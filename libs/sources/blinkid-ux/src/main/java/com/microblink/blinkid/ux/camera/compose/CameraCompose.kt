/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.camera.compose

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.ViewPort
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.microblink.blinkid.ux.camera.CameraInputDetails
import com.microblink.blinkid.ux.camera.CameraLensFacing
import com.microblink.blinkid.ux.camera.CameraSettings
import com.microblink.blinkid.ux.camera.CameraViewModel
import com.microblink.blinkid.ux.camera.DesiredAspectRatio
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Composable function that displays the camera preview and handles camera-related
 * permissions and image analysis.
 *
 * This composable provides a screen for capturing images using the device's
 * camera. It handles camera permission requests and displays the camera
 * preview if permission is granted. It also provides a content slot for
 * overlaying additional UI elements on top of the camera preview.
 *
 * @param cameraViewModel The [CameraViewModel] used to interact with the camera
 *                        and perform image analysis.
 * @param cameraSettings The [CameraSettings] used to configure the camera.
 *                       Defaults to [CameraSettings] with default values.
 * @param onCameraScreenLongPress Callback invoked when the camera screen is long-pressed
 * @param cameraPermissionCallbacks Optional callbacks for camera permission events
 * @param cameraPreviewCallbacks Optional callbacks for camera preview lifecycle events
 * @param cameraInputDetailsCallback Optional callback for receiving camera input configuration details
 * @param content A composable lambda for adding UI content on top of the camera
 *                preview.
 *
 */
@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel,
    cameraSettings: CameraSettings = CameraSettings(),
    onCameraScreenLongPress: () -> Unit = { },
    cameraPermissionCallbacks: CameraPermissionCallbacks? = null,
    cameraPreviewCallbacks: CameraPreviewCallbacks? = null,
    cameraInputDetailsCallback: CameraInputDetailsCallback? = null,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val cameraPermissionGranted = remember { mutableStateOf(isCameraPermissionGranted(context)) }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        cameraPermissionCallbacks?.onCameraPermissionUserResponse(isGranted)
        cameraPermissionGranted.value = isGranted
    }
    val torchOn = cameraViewModel.torchOn.collectAsStateWithLifecycle()

    LifecycleStartEffect(Unit) {
        cameraPermissionCallbacks?.onCameraPermissionCheck()
        cameraPermissionGranted.value = isCameraPermissionGranted(context)
        onStopOrDispose { }
    }
    if (cameraPermissionGranted.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onCameraScreenLongPress()
                        }
                    )
                }
        ) {
            CameraPreview(
                cameraSettings = cameraSettings,
                torchOn = torchOn.value,
                onTorchSupportStateAvailable = cameraViewModel::updateTorchSupportState,
                imageAnalyzer = cameraViewModel::analyzeImage,
                cameraPreviewCallbacks = cameraPreviewCallbacks,
                cameraInputDetailsCallback = cameraInputDetailsCallback
            )
            content()
        }

    } else {
        LaunchedEffect(Unit) {
            cameraPermissionCallbacks?.onCameraPermissionRequested()
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        CameraPermissionDeniedScreen {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}

@Composable
private fun CameraPreview(
    cameraSettings: CameraSettings = CameraSettings(),
    torchOn: Boolean,
    onTorchSupportStateAvailable: (Boolean) -> Unit,
    imageAnalyzer: (ImageProxy) -> Unit,
    cameraPreviewCallbacks: CameraPreviewCallbacks? = null,
    cameraInputDetailsCallback: CameraInputDetailsCallback? = null
) {
    val lensFacing = when (cameraSettings.lensFacing) {
        CameraLensFacing.LensFacingBack -> CameraSelector.LENS_FACING_BACK
        CameraLensFacing.LensFacingFront -> CameraSelector.LENS_FACING_FRONT
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val previewView = remember {
        PreviewView(context)
    }
    val cameraExecutor = remember {
        Executors.newSingleThreadExecutor()
    }
    val camera = remember { mutableStateOf<Camera?>(null) }
    val viewPort = remember { mutableStateOf<ViewPort?>(null) }

    // Track if we've already reported the CameraInputInfo for this session
    val cameraInputInfoReported = remember { mutableStateOf(false) }

    // Persist the last known state across recreation
    var lastKnownState by rememberSaveable {
        mutableStateOf<PreviewView.StreamState?>(null)
    }
    DisposableEffect(lifecycleOwner) {
        val observer = if (cameraPreviewCallbacks != null) {
            Observer<PreviewView.StreamState> { streamState ->
                when (streamState) {
                    PreviewView.StreamState.STREAMING -> {
                        cameraPreviewCallbacks.onCameraPreviewStarted()
                    }
                    PreviewView.StreamState.IDLE -> {
                        // Only call stopped if we were previously streaming
                        if (lastKnownState == PreviewView.StreamState.STREAMING) {
                            cameraPreviewCallbacks.onCameraPreviewStopped()
                        } else {
                            // idle (not from streaming, ignoring)
                        }
                    }
                }
                lastKnownState = streamState
            }.also { observer ->
                previewView.previewStreamState.observeForever(observer)
            }
        } else null

        onDispose {
            if (lastKnownState == PreviewView.StreamState.STREAMING) {
                // if we are disposing, we need to report preview stop as
                // new observer will be registered and set last know state to IDLE
                lastKnownState = PreviewView.StreamState.IDLE
                cameraPreviewCallbacks?.onCameraPreviewStopped()
            }
            observer?.let { previewView.previewStreamState.removeObserver(it) }
        }
    }

    LaunchedEffect(lensFacing) {
        val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        val resolutionSelector = ResolutionSelector.Builder()
            .setResolutionStrategy(
                ResolutionStrategy(
                    Size(
                        cameraSettings.desiredResolution.width,
                        cameraSettings.desiredResolution.height
                    ),
                    ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER
                )
            )
            .setAspectRatioStrategy(
                when (cameraSettings.desiredAspectRatio) {
                    DesiredAspectRatio.RATIO_16_9 -> AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY
                    DesiredAspectRatio.RATIO_4_3 -> AspectRatioStrategy.RATIO_4_3_FALLBACK_AUTO_STRATEGY
                }
            )
            .build()

        val cameraProvider = context.getCameraProvider()

        val previewUseCase = Preview.Builder()
            .setResolutionSelector(resolutionSelector)
            .build()
        val imageAnalysisUseCase = ImageAnalysis.Builder().apply {
            setResolutionSelector(resolutionSelector)
        }.build()

        imageAnalysisUseCase.setAnalyzer(
            cameraExecutor
        ) { imageProxy ->
            // Report CameraInputInfo only once per camera session
            if (cameraInputDetailsCallback != null) {
                if (!cameraInputInfoReported.value) {
                    // Calculate viewport aspect ratio
                    var viewPortAspectRatio = 0.0
                    viewPort.value?.let { vp ->
                        if (vp.aspectRatio.denominator != 0) {
                            viewPortAspectRatio = vp.aspectRatio.numerator.toDouble() / vp.aspectRatio.denominator
                        }
                    }
                    if (viewPortAspectRatio == 0.0) {
                        val previewWidth = previewView.width.toDouble()
                        val previewHeight = previewView.height.toDouble()
                        if (previewHeight > 0) {
                            viewPortAspectRatio = previewWidth / previewHeight
                        }
                    }
                    cameraInputDetailsCallback.onCameraInputDetailsAvailable(
                        CameraInputDetails(
                            cameraId = getCameraIdOrEmpty(camera.value),
                            cameraFacing = when (camera.value?.cameraInfo?.lensFacing) {
                                CameraSelector.LENS_FACING_FRONT -> CameraLensFacing.LensFacingFront
                                CameraSelector.LENS_FACING_BACK -> CameraLensFacing.LensFacingBack
                                else -> cameraSettings.lensFacing
                            },
                            cameraFrameWidth = imageProxy.width,
                            cameraFrameHeight = imageProxy.height,
                            viewPortAspectRatio = viewPortAspectRatio,
                            roiWidth = imageProxy.cropRect.width(),
                            roiHeight = imageProxy.cropRect.height()
                        )
                    )
                    cameraInputInfoReported.value = true
                }
            }
            imageAnalyzer(imageProxy)
        }

        val useCaseGroup = UseCaseGroup.Builder().apply {
            previewView.viewPort?.let {
                viewPort.value = it
                setViewPort(it)
            }
            addUseCase(previewUseCase)
            addUseCase(imageAnalysisUseCase)
        }.build()

        cameraProvider.unbindAll()
        val boundCamera = cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, useCaseGroup)
        previewUseCase.surfaceProvider = previewView.surfaceProvider
        enableTapToFocus(previewView, boundCamera.cameraControl)
        boundCamera.cameraControl.enableTorch(torchOn)
        camera.value = boundCamera

        // Reset tracking flag for new camera session
        cameraInputInfoReported.value = false

        onTorchSupportStateAvailable(boundCamera.cameraInfo.hasFlashUnit())
    }

    LaunchedEffect(torchOn) {
        camera.value?.cameraControl?.enableTorch(torchOn)
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier
            .fillMaxSize()
    )
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                @Suppress("BlockingMethodInNonBlockingContext")
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

@OptIn(ExperimentalCamera2Interop::class)
private fun getCameraIdOrEmpty(camera: Camera?): String {
    if (camera == null) return ""
    return try {
        Camera2CameraInfo.from(camera.cameraInfo).cameraId
    } catch (_: Throwable) {
        ""
    }
}

@SuppressLint("ClickableViewAccessibility")
private fun enableTapToFocus(previewView: PreviewView, cameraControl: CameraControl) {
    previewView.setOnTouchListener { view, motionEvent ->
        val meteringPoint = previewView.meteringPointFactory
            .createPoint(motionEvent.x, motionEvent.y)
        val action = FocusMeteringAction.Builder(meteringPoint) // default AF|AE|AWB
            // The action is canceled in 3 seconds (if not set, default is 5s).
            .setAutoCancelDuration(3, TimeUnit.SECONDS)
            .build()
        cameraControl.startFocusAndMetering(action)
        view.performClick()
        false
    }
}

private fun isCameraPermissionGranted(context: Context) =
    ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

/**
 * Interface for receiving camera permission-related events during the camera lifecycle.
 *
 * This interface provides callbacks for different stages of the camera permission flow,
 * enabling analytics tracking and UI updates based on permission state changes.
 * The callbacks follow the typical Android permission request flow.
 */
public interface CameraPermissionCallbacks {
    /**
     * Called when the camera permission status is being checked.
     */
    public fun onCameraPermissionCheck()
    /**
     * Called when a camera permission request is initiated.
     * This function is invoked just before showing the system permission dialog to the user.
     */
    public fun onCameraPermissionRequested()
    /**
     * Called when the user responds to the camera permission request.
     * This function is invoked after the user grants or denies the camera permission
     * request.
     *
     * @param cameraPermissionGranted true if the user granted camera permission, false if denied
     */
    public fun onCameraPermissionUserResponse(cameraPermissionGranted: Boolean)
}

/**
 * Interface for receiving camera preview lifecycle events.
 *
 * This interface provides callbacks for camera preview state changes, enabling
 * tracking of when the camera preview becomes active or inactive.
 */
public interface CameraPreviewCallbacks {
    /**
     * Called when the camera preview begins streaming.
     * This function is invoked when the camera preview successfully starts displaying
     * live camera frames. It indicates that the camera is active and ready for
     * user interaction.
     */
    public fun onCameraPreviewStarted()
    /**
     * Called when the camera preview stops streaming.
     * This function is invoked when the camera preview stops displaying frames,
     * typically when the camera is released, the app goes to background, or
     * during camera configuration changes.
     */
    public fun onCameraPreviewStopped()
}

/**
 * Interface for receiving detailed camera input configuration information.
 *
 * This interface provides a callback for obtaining comprehensive details about
 * the camera input setup once it's fully configured and operational.
 */
public interface CameraInputDetailsCallback {
    /**
     * Called when camera input configuration details become available.
     *
     * This function is invoked once per camera session when complete camera input
     * information is available.
     *
     * @param cameraInputDetails camera input configuration details.
     */
    public fun onCameraInputDetailsAvailable(cameraInputDetails: CameraInputDetails)
}