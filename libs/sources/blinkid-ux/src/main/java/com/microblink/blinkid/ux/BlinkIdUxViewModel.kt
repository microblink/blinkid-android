/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux

import android.content.Context
import android.os.CountDownTimer
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.microblink.blinkid.core.BlinkIdSdk
import com.microblink.blinkid.core.ping.config.PingSendTriggerPoint
import com.microblink.blinkid.core.ping.pinglets.UxEvent
import com.microblink.blinkid.core.session.BlinkIdScanningResult
import com.microblink.blinkid.core.session.BlinkIdSessionSettings
import com.microblink.blinkid.core.utils.MbLog
import com.microblink.blinkid.core.utils.ping.sendPingletsIfAllowed
import com.microblink.blinkid.ux.camera.CameraHardwareInfoHelper
import com.microblink.blinkid.ux.camera.CameraInputDetails
import com.microblink.blinkid.ux.camera.CameraViewModel
import com.microblink.blinkid.ux.camera.TimeoutCause
import com.microblink.blinkid.ux.components.needHelpTooltipDefaultTimeToAppearMs
import com.microblink.blinkid.ux.components.uiCountingWindowDurationMs
import com.microblink.blinkid.ux.scanning.BlinkIdAnalyzer
import com.microblink.blinkid.ux.scanning.BlinkIdDocumentLocatedLocation
import com.microblink.blinkid.ux.scanning.BlinkIdScanningDoneHandler
import com.microblink.blinkid.ux.scanning.DocumentImageAnalysisResult
import com.microblink.blinkid.ux.scanning.FrameProcessResultHandle
import com.microblink.blinkid.ux.scanning.RequestPassportPage
import com.microblink.blinkid.ux.scanning.ScanningWrongPassportPage
import com.microblink.blinkid.ux.settings.BlinkIdUxSettings
import com.microblink.blinkid.ux.state.BlinkIdStatusMessage
import com.microblink.blinkid.ux.state.BlinkIdUiState
import com.microblink.blinkid.ux.state.CardAnimationState
import com.microblink.blinkid.ux.state.CardAnimationState.ShowFlipLandscape
import com.microblink.blinkid.ux.state.CommonStatusMessage
import com.microblink.blinkid.ux.state.ErrorState
import com.microblink.blinkid.ux.state.HapticFeedbackState
import com.microblink.blinkid.ux.state.ScanSoundState
import com.microblink.blinkid.ux.state.MbTorchState
import com.microblink.blinkid.ux.state.PassportPage
import com.microblink.blinkid.ux.state.ProcessingState
import com.microblink.blinkid.ux.state.ReticleState
import com.microblink.blinkid.ux.state.ShowPassportMoveToBarcode
import com.microblink.blinkid.ux.state.ShowPassportMoveToLeft
import com.microblink.blinkid.ux.state.ShowPassportMoveToRight
import com.microblink.blinkid.ux.state.ShowPassportMoveToTop
import com.microblink.blinkid.ux.state.StatusMessage
import com.microblink.blinkid.ux.state.StatusMessageCounter
import com.microblink.blinkid.ux.state.UiScanningSide
import com.microblink.blinkid.ux.utils.BlinkIdExtractionMode
import com.microblink.blinkid.ux.utils.ErrorReason
import com.microblink.blinkid.ux.utils.ScreenOrientation
import com.microblink.blinkid.ux.utils.UxPingletTracker
import com.microblink.blinkid.ux.utils.getCorrectedDocumentRotation
import com.microblink.blinkid.ux.utils.getPassportPageFromRotation
import com.microblink.blinkid.ux.utils.pingletOrientationDelayMs
import com.microblink.blinkid.ux.utils.toBlinkIdExtractionMode
import com.microblink.blinkid.ux.utils.toErrorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal class BlinkIdUxViewModel(
    blinkIdSdkInstance: BlinkIdSdk,
    sessionSettings: BlinkIdSessionSettings,
    uxSettings: BlinkIdUxSettings,
    onFrameProcessResult: ((FrameProcessResultHandle) -> Unit)? = null
) : CameraViewModel() {
    private var imageAnalyzer: BlinkIdAnalyzer? = null

    private var firstImageTimestamp: Long? = null
    private var newStateTimestamp: Long? = null

    private var stateTimeoutDurationBeforePause: Long? = null

    private val stepTimeoutDuration: Duration? =
        if (uxSettings.stepTimeoutDuration == Duration.ZERO) null else uxSettings.stepTimeoutDuration
    private val inactivityTimeoutDuration: Duration? =
        if (uxSettings.inactivityTimeoutDuration == Duration.ZERO) null else uxSettings.inactivityTimeoutDuration

    private var isStateTimeoutActive: Boolean = true

    private val initialStatusMessage: StatusMessage =
        when (sessionSettings.toBlinkIdExtractionMode()) {
            BlinkIdExtractionMode.FullDocument -> CommonStatusMessage.ScanFirstSide
            BlinkIdExtractionMode.BarcodeOnly -> BlinkIdStatusMessage.ScanBarcodeOnlyModule
            BlinkIdExtractionMode.DocumentWithBarcode -> BlinkIdStatusMessage.ScanBarcodeIdModule
            BlinkIdExtractionMode.DocumentWithMrz -> BlinkIdStatusMessage.ScanMrzModule
        }

    private val _uiState = MutableStateFlow(BlinkIdUiState(statusMessage = initialStatusMessage))
    val uiState: StateFlow<BlinkIdUiState> = _uiState.asStateFlow()

    var uiStateStartTime: Duration = Duration.ZERO
    val countingWindowDuration: Duration = uiCountingWindowDurationMs.milliseconds
    private var lastScreenOrientationPingTime: Long = 0L

    private val statusCounter: StatusMessageCounter = StatusMessageCounter()
    private val appearanceCounter: StatusMessageCounter = StatusMessageCounter()

    private var currentScreenOrientation: ScreenOrientation? = null

    private var lastTrackedErrorType: UxEvent.ErrorMessageType? = null

    private var cameraHardwareInfoReported = false

    private val helpTooltipTimer =
        object : CountDownTimer(
            needHelpTooltipDefaultTimeToAppearMs,
            needHelpTooltipDefaultTimeToAppearMs
        ) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                UxPingletTracker.UxEvent.trackSimpleEvent(
                    UxPingletTracker.UxEvent.SimpleUxEventType.HelpTooltipDisplayed,
                    getSessionNumber()
                )
                changeHelpTooltipVisibility(true)
            }

        }

    init {
        imageAnalyzer = BlinkIdAnalyzer(
            blinkIdSdk = blinkIdSdkInstance,
            sessionSettings = sessionSettings,
            uxSettings = uxSettings,
            scanningDoneHandler = object : BlinkIdScanningDoneHandler {
                override fun onScanningFinished(result: BlinkIdScanningResult) {
                    MbLog.d(TAG) { "Scanning finished successfully." }
                    _uiState.update {
                        it.copy(
                            blinkIdScanningResult = result,
                            statusMessage = CommonStatusMessage.Empty,
                            processingState = ProcessingState.SuccessAnimation(false)
                        )
                    }
                }

                override fun onError(error: ErrorReason) {
                    MbLog.d(TAG) { "Scanning finished with an error: $error" }
                    showErrorDialog(
                        alertType = when (error) {
                            ErrorReason.ErrorInvalidLicense -> UxEvent.AlertType.INVALIDLICENSEKEY
                            ErrorReason.ErrorStepTimeoutExpired -> UxEvent.AlertType.STEPTIMEOUT
                            ErrorReason.ErrorInactivityTimeoutExpired -> UxEvent.AlertType.INACTIVITYTIMEOUT
                            ErrorReason.ErrorNetworkError -> UxEvent.AlertType.NETWORKERROR
                            ErrorReason.ErrorDocumentClassFiltered -> UxEvent.AlertType.DOCUMENTCLASSNOTALLOWED
                            ErrorReason.ErrorSettingsValidationFailed -> UxEvent.AlertType.NETWORKERROR
                            ErrorReason.ErrorGetResultFailed -> UxEvent.AlertType.NETWORKERROR
                        },
                        errorState = error.toErrorState()
                    )
                }

                override fun onScanningCanceled() {}
            },
            uxEventHandler = object : ScanningUxEventHandler {
                override fun onUxEvents(events: List<ScanningUxEvent>) {
                    var newStatusMessage: StatusMessage? = null
                    var newProcessingState: ProcessingState? = null
                    var newActivePassportPage: PassportPage? = null
                    var newCurrentSide: UiScanningSide? = null
                    stepTimeoutDuration?.let {
                        if (firstImageTimestamp == null) {
                            firstImageTimestamp = System.nanoTime()
                        }
                        firstImageTimestamp?.let { timestamp ->
                            val currentDuration =
                                (System.nanoTime() - timestamp).toDuration(DurationUnit.NANOSECONDS)
                            if (currentDuration > stepTimeoutDuration) {
                                imageAnalyzer?.timeoutAnalysis(TimeoutCause.Step)
                                firstImageTimestamp = null
                                newStateTimestamp = null
                            }
                        }
                    }
                    inactivityTimeoutDuration?.let {
                        if (isStateTimeoutActive) {
                            if (newStateTimestamp == null) {
                                newStateTimestamp = System.nanoTime()
                            }
                            newStateTimestamp?.let { timestamp ->
                                val currentDuration =
                                    (System.nanoTime() - timestamp).toDuration(DurationUnit.NANOSECONDS)
                                if (currentDuration > inactivityTimeoutDuration) {
                                    imageAnalyzer?.timeoutAnalysis(TimeoutCause.Inactivity)
                                    firstImageTimestamp = null
                                    newStateTimestamp = null
                                }
                            }
                        }
                    }
                    for (event in events) {
                        MbLog.d(TAG) { "Received UX event: $event" }
                        when (event) {
                            is ScanningUxEvent.ScanningDone -> {
                                firstImageTimestamp = null
                                lifecyclePauseAnalysis()
                                newStatusMessage = CommonStatusMessage.Empty
                                newProcessingState = ProcessingState.SuccessAnimation(false)
                            }

                            is ScanningUxEvent.DocumentNotFound -> {
                                newProcessingState = ProcessingState.Sensing

                                newStatusMessage =
                                    if (uiState.value.activePassportPage != null) {
                                        when (uiState.value.activePassportPage) {
                                            PassportPage.Top -> BlinkIdStatusMessage.PassportScanTopPage
                                            PassportPage.Right -> BlinkIdStatusMessage.PassportScanRightPage
                                            PassportPage.Left -> BlinkIdStatusMessage.PassportScanLeftPage
                                            PassportPage.Barcode -> BlinkIdStatusMessage.PassportScanBarcodePage
                                            else -> BlinkIdStatusMessage.PassportScanTopPage
                                        }
                                    } else {
                                        when (uiState.value.currentSide) {
                                            UiScanningSide.First -> initialStatusMessage
                                            UiScanningSide.Second -> CommonStatusMessage.ScanSecondSide
                                            UiScanningSide.Barcode -> BlinkIdStatusMessage.ScanBarcode
                                        }
                                    }

                            }

                            is ScanningUxEvent.DocumentLocated, is BlinkIdDocumentLocatedLocation -> {
                                // newProcessingState = ProcessingState.Processing
                                // Not used in this UI implementation.
                                // Can be used for processing state.
                            }

                            is ScanningUxEvent.BlurDetected -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = CommonStatusMessage.EliminateBlur
                            }

                            is ScanningUxEvent.DocumentNotFullyVisible -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = CommonStatusMessage.KeepVisible
                            }

                            is ScanningUxEvent.FaceImageNotFound -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = BlinkIdStatusMessage.KeepFacePhotoVisible
                            }

                            is ScanningUxEvent.DocumentTooBright -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = BlinkIdStatusMessage.DecreaseLightingIntensity
                            }

                            is ScanningUxEvent.DocumentTooDark -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = BlinkIdStatusMessage.IncreaseLightingIntensity
                            }

                            is ScanningUxEvent.DocumentTooClose -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = CommonStatusMessage.MoveFarther
                            }

                            is ScanningUxEvent.DocumentTooFar -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = CommonStatusMessage.MoveCloser
                            }

                            is ScanningUxEvent.DocumentTooTilted -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = CommonStatusMessage.Align
                            }

                            is ScanningUxEvent.GlareDetected -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = BlinkIdStatusMessage.EliminateGlare
                            }

                            is ScanningUxEvent.ScanningWrongSide -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = CommonStatusMessage.ScanningWrongSide
                            }

                            is ScanningWrongPassportPage -> {
                                val page =
                                    if (event.activePassportPage == PassportPage.Data) {
                                        PassportPage.Data
                                    } else if (event.activePassportPage == PassportPage.Barcode) {
                                        PassportPage.Barcode
                                    } else {
                                        getPassportPageFromRotation(
                                            getCorrectedDocumentRotation(
                                                event.documentRotation,
                                                uiState.value.screenOrientation
                                            )
                                        )
                                    }
                                newStatusMessage =
                                    when (page) {
                                        PassportPage.Top -> BlinkIdStatusMessage.PassportWrongPageTop
                                        PassportPage.Right -> BlinkIdStatusMessage.PassportWrongPageRight
                                        PassportPage.Left -> BlinkIdStatusMessage.PassportWrongPageLeft
                                        PassportPage.Data -> BlinkIdStatusMessage.ScanPassportDataPage
                                        PassportPage.Barcode -> BlinkIdStatusMessage.PassportWrongPageBarcode
                                    }
                                newProcessingState = ProcessingState.Error
                                newActivePassportPage = page
                            }

                            is RequestPassportPage -> {
                                firstImageTimestamp = null
                                lifecyclePauseAnalysis()
                                newActivePassportPage = if (event.isBarcodePageRequested) {
                                    PassportPage.Barcode
                                } else {
                                    getPassportPageFromRotation(
                                        getCorrectedDocumentRotation(
                                            event.documentRotation,
                                            uiState.value.screenOrientation
                                        )
                                    )
                                }
                                newProcessingState =
                                    ProcessingState.SuccessAnimation(true)
                                newStatusMessage = CommonStatusMessage.Empty
                            }

                            is ScanningUxEvent.RequestSide -> {
                                var currentSide: UiScanningSide = uiState.value.currentSide
                                when (uiState.value.currentSide) {
                                    UiScanningSide.First -> {
                                        when (event.side) {
                                            UiScanningSide.First -> {
                                            }

                                            UiScanningSide.Second -> {
                                                // TODO: support for portrait animations
                                                newProcessingState =
                                                    ProcessingState.SuccessAnimation(true)
                                                newStatusMessage = CommonStatusMessage.Empty
                                                firstImageTimestamp = null
                                                lifecyclePauseAnalysis()
                                            }

                                            UiScanningSide.Barcode -> {
                                                newProcessingState = ProcessingState.Sensing
                                                newStatusMessage =
                                                    BlinkIdStatusMessage.ScanBarcode
                                                newCurrentSide = UiScanningSide.Barcode
                                                firstImageTimestamp = null
                                                isStateTimeoutActive = false
                                                imageAnalyzer?.pauseAnalysis()
                                                imageAnalyzer?.resumeAnalysis()
                                            }
                                        }
                                    }

                                    UiScanningSide.Second -> {
                                        when (event.side) {
                                            UiScanningSide.First -> {}

                                            UiScanningSide.Second -> {}

                                            UiScanningSide.Barcode -> {
                                                newProcessingState = ProcessingState.Sensing
                                                newStatusMessage =
                                                    BlinkIdStatusMessage.ScanBarcode
                                                newCurrentSide = UiScanningSide.Barcode
                                                firstImageTimestamp = null
                                                isStateTimeoutActive = false
                                                imageAnalyzer?.pauseAnalysis()
                                                imageAnalyzer?.resumeAnalysis()
                                            }
                                        }
                                    }

                                    UiScanningSide.Barcode -> {
                                        // Impossible to reach anything else other than barcode.
                                        currentSide = UiScanningSide.Barcode
                                        newStatusMessage = BlinkIdStatusMessage.ScanBarcode
                                    }
                                }

                            }

                            is ScanningUxEvent.BarcodeNotDetected -> {
                                newProcessingState = ProcessingState.Error
                                newStatusMessage = BlinkIdStatusMessage.BarcodeWrongSide
                            }

                            is DocumentImageAnalysisResult -> {
                                // Not used in this UI implementation.
                                // Can be used for additional frame debugging.
                            }

                            is ScanningUxEvent.UnsupportedDocument -> {
                                showErrorDialog(
                                    alertType = UxEvent.AlertType.DOCUMENTNOTSUPPORTED,
                                    errorState = ErrorState.ErrorUnsupportedDocument
                                )
                            }

                        }
                    }

                    if (newProcessingState != _uiState.value.processingState || newStatusMessage != _uiState.value.statusMessage) {
                        newStateTimestamp = null
                    }
                    updateUiState(
                        newProcessingState,
                        newStatusMessage,
                        newActivePassportPage,
                        newCurrentSide
                    )
                }
            },
            onFrameProcessResult = onFrameProcessResult
        )

        viewModelScope.launch {
            isTorchSupported.collect { isTorchSupported ->
                _uiState.update {
                    it.copy(
                        torchState = if (isTorchSupported) MbTorchState.Off else MbTorchState.NotSupportedByCamera
                    )
                }
            }
        }
    }

    private fun updateUiState(
        newProcessingState: ProcessingState?,
        newStatusMessage: StatusMessage?,
        newActivePassportPage: PassportPage?,
        newCurrentSide: UiScanningSide?
    ) {
        newProcessingState?.let {
            if (newProcessingState is ProcessingState.SuccessAnimation || newStatusMessage == BlinkIdStatusMessage.ScanBarcode) {
                isStateTimeoutActive = false
                runBlocking {
                    waitForMinimumStateDuration(newProcessingState)
                }
            } else if (isStateTimeoutActive || shouldStartCounting(uiState.value.processingState)) {
                newStatusMessage?.let {
                    statusCounter.increment(it)
                }
            }

            newStatusMessage?.let {
                val stateRemDur = remainingStateDuration(
                    uiState.value.processingState,
                    newProcessingState,
                    newStatusMessage
                )
                if (stateRemDur <= Duration.ZERO || newProcessingState is ProcessingState.SuccessAnimation || newStatusMessage == BlinkIdStatusMessage.ScanBarcode) {
                    val (selectedProcessingState, selectedStatusMessage) =
                        if (newProcessingState.reticleState == ReticleState.Success || newProcessingState.reticleState == ReticleState.SuccessFirstSide || newStatusMessage == BlinkIdStatusMessage.ScanBarcode) {
                            Pair(
                                newProcessingState,
                                newStatusMessage
                            )
                        } else if (uiState.value.statusMessage == BlinkIdStatusMessage.ScanBarcode) {
                            Pair(
                                null,
                                null
                            )
                        } else pickNewState()
                    selectedProcessingState?.let {
                        val newHapticFeedbackState = when (selectedProcessingState) {
                            ProcessingState.Error -> HapticFeedbackState.VibrationOneTimeShort
                            is ProcessingState.SuccessAnimation -> {
                                if (selectedProcessingState.isFirstSide) {
                                    HapticFeedbackState.VibrationOneTimeShort
                                } else {
                                    HapticFeedbackState.VibrationOneTimeLong
                                }
                            }

                            else -> null
                        }
                        val newScanSoundState = when (selectedProcessingState) {
                            is ProcessingState.SuccessAnimation -> ScanSoundState.PlayScanBeep
                            else -> null
                        }
                        selectedStatusMessage?.let {
                            if (selectedProcessingState == ProcessingState.Error) {
                                val errorType: UxEvent.ErrorMessageType? =
                                    when (selectedStatusMessage) {
                                        is CommonStatusMessage -> when (selectedStatusMessage) {
                                            CommonStatusMessage.MoveCloser -> UxEvent.ErrorMessageType.MOVECLOSER
                                            CommonStatusMessage.MoveFarther -> UxEvent.ErrorMessageType.MOVEFARTHER
                                            CommonStatusMessage.KeepVisible,
                                                -> UxEvent.ErrorMessageType.KEEPVISIBLE

                                            CommonStatusMessage.ScanningWrongSide -> UxEvent.ErrorMessageType.FLIPSIDE
                                            CommonStatusMessage.Align -> UxEvent.ErrorMessageType.ALIGNDOCUMENT
                                            CommonStatusMessage.EliminateBlur -> UxEvent.ErrorMessageType.ELIMINATEBLUR
                                            else -> null
                                        }

                                        is BlinkIdStatusMessage -> when (selectedStatusMessage) {
                                            BlinkIdStatusMessage.KeepFacePhotoVisible -> UxEvent.ErrorMessageType.KEEPVISIBLE
                                            BlinkIdStatusMessage.IncreaseLightingIntensity -> UxEvent.ErrorMessageType.INCREASELIGHTING
                                            BlinkIdStatusMessage.DecreaseLightingIntensity -> UxEvent.ErrorMessageType.DECREASELIGHTING
                                            BlinkIdStatusMessage.EliminateGlare -> UxEvent.ErrorMessageType.ELIMINATEGLARE
                                            else -> null
                                        }

                                        else -> null
                                    }
                                errorType?.let {
                                    // We track error message event only when the errorMessageType changes
                                    if (errorType != lastTrackedErrorType) {
                                        lastTrackedErrorType = errorType
                                        UxPingletTracker.UxEvent.trackErrorMessageEvent(
                                            it,
                                            getSessionNumber()
                                        )
                                    }
                                }
                            }
                            _uiState.update {
                                it.copy(
                                    reticleState = selectedProcessingState.reticleState,
                                    processingState = selectedProcessingState,
                                    statusMessage = selectedStatusMessage,
                                    hapticFeedbackState = newHapticFeedbackState
                                        ?: it.hapticFeedbackState,
                                    scanSoundState = newScanSoundState
                                        ?: it.scanSoundState,
                                    activePassportPage = newActivePassportPage
                                        ?: it.activePassportPage,
                                    currentSide = newCurrentSide ?: it.currentSide
                                )
                            }

                            appearanceCounter.incrementIfNotPresent(selectedStatusMessage)
                            updateStateStartTime()
                        }
                    }
                }
            }
        }
    }


    fun setInitialUiStateFromUiSettings(uiSettings: UiSettings) {
        _uiState.update {
            it.copy(
                helpButtonDisplayed = uiSettings.showHelpButton,
                onboardingDialogDisplayed = if (uiState.value.errorState == ErrorState.NoError) uiSettings.showOnboardingDialog else false
            )
        }
        if (_uiState.value.onboardingDialogDisplayed) {
            changeOnboardingDialogVisibility(true)
        } else {
            changeOnboardingDialogVisibility(false)
        }
    }

    fun pickNewState(): Pair<ProcessingState?, StatusMessage?> {
        val max = statusCounter.getAllCounts().values.maxOrNull()
        val mostFrequent = statusCounter.getAllCounts().filterValues { it == max }.keys.toList()
        statusCounter.reset()
        return if (mostFrequent.isNotEmpty()) {
            when (mostFrequent[0]) {
                BlinkIdStatusMessage.RotateDocument, CommonStatusMessage.ScanFirstSide, BlinkIdStatusMessage.ScanBarcodeOnlyModule, BlinkIdStatusMessage.ScanBarcodeIdModule, BlinkIdStatusMessage.ScanMrzModule, CommonStatusMessage.ScanSecondSide, BlinkIdStatusMessage.ScanBarcode, BlinkIdStatusMessage.PassportScanTopPage, BlinkIdStatusMessage.PassportScanLeftPage, BlinkIdStatusMessage.PassportScanRightPage, BlinkIdStatusMessage.PassportScanBarcodePage -> {
                    Pair(ProcessingState.Sensing, mostFrequent[0])
                }

                else -> {
                    Pair(ProcessingState.Error, mostFrequent[0])
                }

            }
        } else Pair(null, null)
    }

    fun setScreenOrientation(screenOrientation: ScreenOrientation) {
        val currentTime = System.currentTimeMillis()
        if (currentScreenOrientation != screenOrientation) {
            currentScreenOrientation = screenOrientation
            // track pinglet only when screen orientation changes (with a delay)
            if (currentTime - lastScreenOrientationPingTime >= pingletOrientationDelayMs) {
                UxPingletTracker.ScanningConditions.trackScreenOrientationChange(
                    screenOrientation = screenOrientation,
                    sessionNumber = getSessionNumber()
                )
                lastScreenOrientationPingTime = currentTime
            }
        }
        _uiState.update {
            it.copy(
                screenOrientation = screenOrientation
            )
        }
    }

    override fun analyzeImage(image: ImageProxy) {
        imageAnalyzer?.analyze(image)
    }

    private fun showErrorDialog(alertType: UxEvent.AlertType, errorState: ErrorState) {
        firstImageTimestamp = null
        lifecyclePauseAnalysis()
        appearanceCounter.reset()
        UxPingletTracker.UxEvent.trackAlertDisplayedEvent(
            alertType = alertType,
            sessionNumber = getSessionNumber()
        )
        _uiState.update {
            it.copy(
                errorState = errorState,
                processingState = ProcessingState.ErrorDialog,
                hapticFeedbackState = HapticFeedbackState.VibrationOneTimeLong,
                activePassportPage = null
            )
        }
    }

    fun lifecyclePauseAnalysis() {
        imageAnalyzer?.pauseAnalysis()
        newStateTimestamp = null
        helpTooltipTimer.cancel()
        statusCounter.reset()
        isStateTimeoutActive = false
        firstImageTimestamp?.let { stateTimeoutDurationBeforePause = System.nanoTime() - it }
    }

    fun lifecycleResumeAnalysis() {
        if (!_uiState.value.onboardingDialogDisplayed && !_uiState.value.helpDisplayed && _uiState.value.errorState == ErrorState.NoError) {
            imageAnalyzer?.resumeAnalysis()
            helpTooltipTimer.start()
            firstImageTimestamp?.let { _ ->
                firstImageTimestamp = System.nanoTime() - (stateTimeoutDurationBeforePause ?: 0L)
            }
        }
    }

    suspend fun waitForMinimumStateDuration(newProcessingState: ProcessingState) {
        var remainingDuration: Duration
        do {
            remainingDuration = remainingStateDuration(
                uiState.value.processingState,
                newProcessingState,
                uiState.value.statusMessage
            )
            if (remainingDuration > Duration.ZERO) {
                delay(remainingDuration.inWholeMilliseconds)
            }
        } while (remainingDuration > Duration.ZERO)
    }

    fun remainingStateDuration(
        currentState: ProcessingState,
        newState: ProcessingState,
        newStatusMessage: StatusMessage
    ): Duration {
        if ((newState.reticleState == ReticleState.Success || newState.reticleState == ReticleState.SuccessFirstSide) && appearanceCounter.getAllCounts()
                .contains(newStatusMessage)
        ) {
            val remainingDuration =
                System.nanoTime().nanoseconds - uiStateStartTime - currentState.minDuration
            return -remainingDuration
        } else {
            val remainingDuration =
                System.nanoTime().nanoseconds - uiStateStartTime - currentState.duration
            return -remainingDuration
        }
    }

    fun shouldStartCounting(currentState: ProcessingState): Boolean {
        if ((System.nanoTime().nanoseconds - uiStateStartTime + countingWindowDuration) >= currentState.duration) {
            isStateTimeoutActive =
                true
            return true
        } else {
            return false
        }
    }

    fun onFlipAnimationCompleted() {
        _uiState.update {
            it.copy(
                processingState = ProcessingState.Sensing,
                cardAnimationState = CardAnimationState.Hidden,
                statusMessage =
                    if (uiState.value.activePassportPage != null) {
                        when (uiState.value.activePassportPage) {
                            PassportPage.Top -> BlinkIdStatusMessage.PassportScanTopPage
                            PassportPage.Right -> BlinkIdStatusMessage.PassportScanRightPage
                            PassportPage.Left -> BlinkIdStatusMessage.PassportScanLeftPage
                            PassportPage.Barcode -> BlinkIdStatusMessage.PassportScanBarcodePage
                            else -> BlinkIdStatusMessage.PassportScanTopPage
                        }
                    } else
                        when (it.currentSide) {
                            UiScanningSide.First -> initialStatusMessage
                            UiScanningSide.Second -> CommonStatusMessage.ScanSecondSide
                            UiScanningSide.Barcode -> BlinkIdStatusMessage.ScanBarcode
                        }
            )
        }
        updateStateStartTime()
        if (!_uiState.value.onboardingDialogDisplayed && !_uiState.value.helpDisplayed) {
            lifecycleResumeAnalysis()
        }
    }

    private fun updateStateStartTime() {
        uiStateStartTime =
            System.nanoTime()
                .toDuration(DurationUnit.NANOSECONDS)
    }

    fun changeTorchState() {
        val (newTorchState, hapticState) = when (_uiState.value.torchState) {
            MbTorchState.On -> MbTorchState.Off to HapticFeedbackState.VibrationOff
            MbTorchState.Off -> MbTorchState.On to HapticFeedbackState.VibrationOneTimeShort
            MbTorchState.NotSupportedByCamera -> return
        }
        _torchOn.value = newTorchState == MbTorchState.On
        _uiState.update {
            it.copy(
                torchState = newTorchState,
                hapticFeedbackState = hapticState
            )
        }
        UxPingletTracker.ScanningConditions.trackTorchStateUpdate(
            newTorchState == MbTorchState.On,
            getSessionNumber()
        )
    }

    fun changeHelpTooltipVisibility(show: Boolean) {
        if (_uiState.value.helpButtonDisplayed) {
            if (show) {
                helpTooltipTimer.cancel()
            } else {
                helpTooltipTimer.start()
            }
            _uiState.update {
                it.copy(helpTooltipDisplayed = show)
            }
        }
    }

    fun changeOnboardingDialogVisibility(show: Boolean) {
        _uiState.update {
            it.copy(onboardingDialogDisplayed = show)
        }
        if (show) {
            UxPingletTracker.UxEvent.trackSimpleEvent(
                UxPingletTracker.UxEvent.SimpleUxEventType.OnboardingInfoDisplayed,
                getSessionNumber()
            )
            lifecyclePauseAnalysis()
        } else {
            lifecycleResumeAnalysis()
        }
    }

    fun onHelpScreensDisplayRequested() {
        UxPingletTracker.UxEvent.trackSimpleEvent(
            UxPingletTracker.UxEvent.SimpleUxEventType.HelpOpened,
            getSessionNumber()
        )
        _uiState.update {
            it.copy(helpDisplayed = true)
        }
        lifecyclePauseAnalysis()
    }

    fun onHelpScreensCloseRequested(allPagesVisited: Boolean) {
        UxPingletTracker.UxEvent.trackHelpCloseEvent(
            helpCloseType = if (allPagesVisited) {
                UxEvent.HelpCloseType.CONTENTFULLYVIEWED
            } else {
                UxEvent.HelpCloseType.CONTENTSKIPPED
            },
            sessionNumber = getSessionNumber()
        )
        _uiState.update {
            it.copy(helpDisplayed = false)
        }
        lifecycleResumeAnalysis()
    }

    fun onRetryTimeout() {
        helpTooltipTimer.cancel()
        _uiState.update {
            it.copy(
                errorState = ErrorState.NoError,
                processingState = ProcessingState.Sensing,
                statusMessage = initialStatusMessage,
                currentSide = UiScanningSide.First,
                activePassportPage = null
            )
        }
        updateStateStartTime()
        viewModelScope.launch {
            imageAnalyzer?.restartAnalysis()
        }
        helpTooltipTimer.start()
    }

    fun onHapticFeedbackCompleted() {
        _uiState.update {
            it.copy(
                hapticFeedbackState = HapticFeedbackState.VibrationOff
            )
        }
    }

    fun onScanSoundCompleted() {
        _uiState.update {
            it.copy(
                scanSoundState = ScanSoundState.SoundOff
            )
        }
    }

    fun onReticleSuccessAnimationCompleted() {
        if (_uiState.value.processingState is ProcessingState.SuccessAnimation && (_uiState.value.processingState as ProcessingState.SuccessAnimation).isFirstSide) {
            if (_uiState.value.activePassportPage != null) {
                _uiState.update {
                    it.copy(
                        processingState = ProcessingState.CardAnimation,
                        statusMessage = when (it.activePassportPage) {
                            PassportPage.Top -> BlinkIdStatusMessage.PassportMoveToTop
                            PassportPage.Right -> BlinkIdStatusMessage.PassportMoveToRight
                            PassportPage.Left -> BlinkIdStatusMessage.PassportMoveToLeft
                            PassportPage.Barcode -> BlinkIdStatusMessage.PassportMoveToBarcode
                            else -> BlinkIdStatusMessage.ScanPassportDataPage
                        },
                        currentSide = UiScanningSide.Second,
                        cardAnimationState =
                            when (it.activePassportPage) {
                                PassportPage.Top -> ShowPassportMoveToTop
                                PassportPage.Right -> ShowPassportMoveToRight
                                PassportPage.Left -> ShowPassportMoveToLeft
                                PassportPage.Barcode -> ShowPassportMoveToBarcode
                                else -> ShowPassportMoveToTop
                            }
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        processingState =
                            ProcessingState.CardAnimation,
                        statusMessage = CommonStatusMessage.Flip,
                        currentSide = UiScanningSide.Second,
                        cardAnimationState = ShowFlipLandscape(
                            firstSideDrawable = R.drawable.mb_blinkid_card_front,
                            secondSideDrawable = R.drawable.mb_blinkid_card_back
                        )
                    )
                }

            }
        } else {
            _uiState.update {
                it.copy(
                    processingState = ProcessingState.Success
                )
            }
        }
    }

    fun onCameraInputInfoAvailable(context: Context, cameraInputDetails: CameraInputDetails) {
        getSessionNumber().takeIf { it > 0 }
            ?.let { sessionNumber ->
                UxPingletTracker.CameraInfo.trackCameraInputInfo(
                    cameraInputDetails,
                    sessionNumber
                )
                if (!cameraHardwareInfoReported) {
                    cameraHardwareInfoReported = true
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            val cameraDetailsList =
                                CameraHardwareInfoHelper.getCameraHardwareInfo(context)
                            UxPingletTracker.CameraInfo.trackCameraHardwareInfo(
                                cameraDetailsList,
                                sessionNumber
                            )
                        }
                    }
                }
            }
    }

    fun getSessionNumber(): Int = imageAnalyzer?.getSessionNumber() ?: 0

    override fun onCleared() {
        super.onCleared()
        BlinkIdSdk.sendPingletsIfAllowed(PingSendTriggerPoint.CameraScreenClosed)
        firstImageTimestamp = null
        lifecyclePauseAnalysis()
        imageAnalyzer?.cancel()
        imageAnalyzer?.close()
        imageAnalyzer = null
    }

    companion object {
        private const val TAG = "BlinkIdUxViewModel"

        // Define a custom key for your dependency
        val BLINKID_SDK =
            object : CreationExtras.Key<BlinkIdSdk> {}
        val BLINKID_SESSION_SETTINGS =
            object : CreationExtras.Key<BlinkIdSessionSettings> {}
        val BLINKID_UX_SETTINGS =
            object : CreationExtras.Key<BlinkIdUxSettings> {}
        val BLINKID_FRAME_PROCESS_RESULT_HANDLE =
            object : CreationExtras.Key<((FrameProcessResultHandle) -> Unit)?> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                BlinkIdUxViewModel(
                    this[BLINKID_SDK] as BlinkIdSdk,
                    this[BLINKID_SESSION_SETTINGS] as BlinkIdSessionSettings,
                    this[BLINKID_UX_SETTINGS] as BlinkIdUxSettings,
                    this[BLINKID_FRAME_PROCESS_RESULT_HANDLE]
                )
            }
        }
    }
}