/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.scanning

import androidx.camera.core.ImageProxy
import com.microblink.blinkid.core.BlinkIdSdk
import com.microblink.blinkid.core.RemoteLicenseCheckException
import com.microblink.blinkid.core.image.ImageRotation
import com.microblink.blinkid.core.image.InputImage
import com.microblink.blinkid.core.result.ScanningStatus
import com.microblink.blinkid.core.session.BlinkIdProcessResult
import com.microblink.blinkid.core.session.BlinkIdScanningSession
import com.microblink.blinkid.core.session.BlinkIdSessionSettings
import com.microblink.blinkid.core.utils.MbLog
import com.microblink.blinkid.ux.ScanningUxEvent
import com.microblink.blinkid.ux.ScanningUxEventHandler
import com.microblink.blinkid.ux.camera.ImageAnalyzer
import com.microblink.blinkid.ux.settings.BlinkIdUxSettings
import com.microblink.blinkid.ux.utils.ErrorReason
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicBoolean

private const val TAG = "BlinkIdAnalyzer"

/**
 * Analyzes images from the camera and processes them using the BlinkID SDK.
 *
 * This class implements the [ImageAnalyzer] interface and is responsible for
 * receiving image frames from the camera, sending them to the BlinkID
 * SDK for processing and results handling. It also manages the scanning
 * session, timeouts, and dispatches UI events.
 *
 * @param blinkIdSdk An instance of the [BlinkIdSdk] used for processing images.
 * @param sessionSettings The requested [BlinkIdSessionSettings] used to configure the scanning session. Note that some settings may be resolved or overridden by the SDK during session initialization.
 * @property uxSettings The [BlinkIdUxSettings] used to customize the UX.
 * @property scanningDoneHandler A [BlinkIdScanningDoneHandler] to handle the completion
 * of the scanning process.
 * @property uxEventHandler An optional [ScanningUxEventHandler] to handle UI events.
 * @property onFrameProcessResult An optional callback that is invoked with the [FrameProcessResultHandle] after each frame is processed,
 * allowing for custom handling of the processing results and control over the scanning flow.
 */
class BlinkIdAnalyzer(
    blinkIdSdk: BlinkIdSdk,
    sessionSettings: BlinkIdSessionSettings,
    private val uxSettings: BlinkIdUxSettings,
    private val scanningDoneHandler: BlinkIdScanningDoneHandler,
    private val uxEventHandler: ScanningUxEventHandler? = null,
    private val onFrameProcessResult: ((FrameProcessResultHandle) -> Unit)? = null
) : ImageAnalyzer {

    private var session: Result<BlinkIdScanningSession>? =
        runBlocking { blinkIdSdk.createScanningSession(sessionSettings) }

    @Volatile
    private var analysisPaused = false


    /**
     * Flag that prevents finishing and delivering the result more than once.
     */
    private val scanningDone = AtomicBoolean(false)

    /**
     * Flag that prevents overlapping calls to resolveCurrentStep()
     */
    private val resolvingCurrentStep = AtomicBoolean(false)

    private val scanningUxTranslator = BlinkIdScanningUxTranslator()

    /**
     * Analyzes a single camera frame.
     *
     * Called by CameraX for each frame delivered to the analyzer. It sends the
     * image to the BlinkID SDK for processing and handles the results,
     * timeouts and cancellations.
     *
     * This implementation closes the provided [ImageProxy] before returning. Custom
     * analyzer implementations must also close the image after processing it.
     *
     * Timeout handling is driven by [BlinkIdUxSettings.stepTimeoutDuration] and
     * [BlinkIdUxSettings.inactivityTimeoutDuration].
     *
     * @param image The camera frame to analyze.
     */
    override fun analyze(image: ImageProxy) {
        image.use {
            if (analysisPaused || scanningDone.get()) {
                return
            }

            runBlocking {
                val inputImage = InputImage.createFromCameraXImageProxy(image)
                inputImage.use {
                    session?.onSuccess { session ->
                        if (session.isCanceled) {
                            MbLog.w(TAG) { "skipping analysis, session is canceled" }
                            return@runBlocking
                        }

                        try {
                            val sessionProcessResult = session.process(inputImage)
                            if (session.isCanceled) {
                                MbLog.w(TAG) { "processing has been canceled" }
                            } else {
                                sessionProcessResult.getOrNull()?.let { processResult ->
                                    uxSettings.classFilter?.let {
                                        if (!processResult.inputImageAnalysisResult.documentClassInfo.isEmpty()
                                            && !it.classAllowed(
                                                processResult.inputImageAnalysisResult.documentClassInfo
                                            )
                                        ) {
                                            onErrorAnalysis(ErrorReason.ErrorDocumentClassFiltered)
                                            return@runBlocking
                                        }
                                    }

                                    val events = scanningUxTranslator.translate(
                                        processResult,
                                        inputImage,
                                        session.sessionSettings.scanningSettings,
                                        session.getScanningStatus()
                                    )

                                    if (events.any { it is ScanningUxEvent.UnparsableBarcode }) {
                                        // UnparsableBarcode is an internal analyzer signal. It should advance the
                                        // native session step, not be forwarded to UI as a user-facing event.
                                        resolveCurrentStepAndHandleStatus(
                                            session = session,
                                            processResult = processResult,
                                            inputImage = inputImage
                                        )
                                        return@runBlocking
                                    }

                                    uxEventHandler?.onUxEvents(events)

                                    if (session.getScanningStatus() == ScanningStatus.DocumentScanned) {
                                        finishScanning(
                                            session = session,
                                            processResult = processResult
                                        )
                                    } else {
                                        handleFrameProcessResult(
                                            inputImage = inputImage,
                                            imageProxy = image,
                                            session = session,
                                            processResult = processResult
                                        )
                                    }
                                }
                            }
                        } catch (e: CancellationException) {
                            throw e
                        } catch (_: RemoteLicenseCheckException) {
                            onErrorAnalysis(ErrorReason.ErrorInvalidLicense)
                        } catch (_: Exception) {
                            onErrorAnalysis(ErrorReason.ErrorGetResultFailed)
                        }
                    }?.onFailure { e ->
                        if (e is CancellationException) throw e
                        onErrorAnalysis(ErrorReason.ErrorSettingsValidationFailed)
                    }
                }
            }
        }
    }

    override fun pauseAnalysis() {
        analysisPaused = true
    }

    override fun resumeAnalysis() {
        analysisPaused = false
    }

    override fun timeoutAnalysis() {
        MbLog.e(TAG) { "processing timeout occurred" }
        onErrorAnalysis(ErrorReason.ErrorTimeoutExpired)
    }

    override fun restartAnalysis() {
        CoroutineScope(Default).launch {
            session?.getOrNull()?.restartSession()
        }
        scanningUxTranslator.resetSession()
        scanningDone.set(false)
        resolvingCurrentStep.set(false)
        analysisPaused = false
    }

    override fun cancel() {
        session?.getOrNull()?.cancelActiveProcess()
        scanningDoneHandler.onScanningCanceled()
    }

    override fun close() {
        session?.getOrNull()?.also { s ->
            session = null
            CoroutineScope(IO).launch {
                s.close()
            }
        }
    }

    fun getSessionNumber(): Int? {
        return session?.getOrNull()?.sessionNumber
    }

    private fun onErrorAnalysis(errorReason: ErrorReason) {
        pauseAnalysis()
        scanningDoneHandler.onError(errorReason)
    }

    private suspend fun resolveCurrentStepAndHandleStatus(
        session: BlinkIdScanningSession,
        processResult: BlinkIdProcessResult,
        inputImage: InputImage
    ) {
        if (analysisPaused || scanningDone.get()) return
        if (!resolvingCurrentStep.compareAndSet(false, true)) return

        try {
            // Covers the case where native scanning is already complete,
            // but the analyzer has not yet marked scanningDone
            if (session.getScanningStatus() == ScanningStatus.DocumentScanned) {
                finishScanning(session, processResult)
                return
            }

            session.resolveCurrentStep()

            // We need to manually update UX state after we call resolveCurrentStep()
            // It either finishes, or updates the UI for the next step
            // Here we ignore UnparsableBarcode, to avoid looping
            when (val scanningStatus = session.getScanningStatus()) {
                ScanningStatus.DocumentScanned -> finishScanning(session, processResult)
                else -> {
                    val events = scanningUxTranslator.translate(
                        processResult = processResult,
                        inputImage = inputImage,
                        scanningSettings = session.sessionSettings.scanningSettings,
                        scanningStatus = scanningStatus
                    ).filterNot { it is ScanningUxEvent.UnparsableBarcode }

                    if (events.isNotEmpty()) {
                        uxEventHandler?.onUxEvents(events)
                    }
                }
            }
        } finally {
            resolvingCurrentStep.set(false)
        }
    }

    private suspend fun finishScanning(
        session: BlinkIdScanningSession,
        processResult: BlinkIdProcessResult
    ) {
        if (!scanningDone.compareAndSet(false, true)) return

        pauseAnalysis()

        val sessionResult = session.getResult(
            uxSettings.redactionSettingsResolver?.resolveRedactionSettings(
                processResult.inputImageAnalysisResult.documentClassInfo
            )
        ).getOrThrow()

        scanningDoneHandler.onScanningFinished(sessionResult)
    }

    private fun handleFrameProcessResult(
        inputImage: InputImage,
        imageProxy: ImageProxy,
        session: BlinkIdScanningSession,
        processResult: BlinkIdProcessResult,
    ) {
        MbLog.v(TAG) {
            "Attempt to call onFrameProcessResult"
        }

        onFrameProcessResult?.let { callback ->
            val handle = FrameProcessResultHandle(
                processResult = processResult,
                advanceToNextStep = {
                    runBlocking {
                        resolveCurrentStepAndHandleStatus(
                            session = session,
                            processResult = processResult,
                            inputImage = inputImage
                        )
                    }
                },
                getLastFrame = {
                    runCatching {
                        FrameProcessResultHandle.LastFrameResult(
                            image = imageProxy.toBitmap(),
                            imageRotation = ImageRotation.fromDegreesInt(
                                imageProxy.imageInfo.rotationDegrees
                            )
                        )
                    }.onFailure { e ->
                        MbLog.w(TAG) { "Failed to get last frame: $e" }
                    }.getOrNull()
                },
                triggerStepTimeout = { timeoutAnalysis() }
            )
            try {
                callback(handle)
            } catch (e: CancellationException) {
                throw e
            } catch (_: Throwable) {
                MbLog.w(TAG) { "onFrameProcessResult callback failed" }
            }
        }
    }
}
