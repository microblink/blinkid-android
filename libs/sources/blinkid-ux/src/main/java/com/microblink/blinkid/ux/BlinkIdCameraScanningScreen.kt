package com.microblink.blinkid.ux

/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.microblink.blinkid.core.BlinkIdSdk
import com.microblink.blinkid.core.session.BlinkIdScanningResult
import com.microblink.blinkid.core.session.BlinkIdSessionSettings
import com.microblink.blinkid.ux.camera.CameraInputDetails
import com.microblink.blinkid.ux.camera.CameraSettings
import com.microblink.blinkid.ux.camera.compose.CameraInputDetailsCallback
import com.microblink.blinkid.ux.camera.compose.CameraPermissionCallbacks
import com.microblink.blinkid.ux.camera.compose.CameraPreviewCallbacks
import com.microblink.blinkid.ux.camera.compose.CameraScreen
import com.microblink.blinkid.ux.scanning.FrameProcessResultHandle
import com.microblink.blinkid.ux.settings.BlinkIdUxSettings
import com.microblink.blinkid.ux.state.MbTorchState
import com.microblink.blinkid.ux.state.ProcessingState
import com.microblink.blinkid.ux.theme.BlinkIdSdkTheme
import com.microblink.blinkid.ux.theme.BlinkIdTheme
import com.microblink.blinkid.ux.utils.DeviceOrientationListener
import com.microblink.blinkid.ux.utils.fillErrorDialogs
import com.microblink.blinkid.ux.utils.fillHelpScreens
import com.microblink.blinkid.ux.utils.onAppMovedToBackground
import com.microblink.blinkid.ux.utils.onCameraPermissionCheck
import com.microblink.blinkid.ux.utils.onCameraPermissionRequest
import com.microblink.blinkid.ux.utils.onCameraPermissionUserResponse
import com.microblink.blinkid.ux.utils.onCameraPreviewStarted
import com.microblink.blinkid.ux.utils.onCameraPreviewStopped
import com.microblink.blinkid.ux.utils.onCloseButtonClicked
import com.microblink.blinkid.ux.utils.toBlinkIdExtractionMode
import kotlinx.coroutines.launch

private const val TAG = "BlinkIdCameraScanningScreen"

/**
 * Composable function that provides a complete camera scanning screen using
 * the BlinkID SDK.
 *
 * This composable function sets up and manages the entire camera scanning
 * process, including UI elements, camera interaction, and result handling. It
 * uses the provided [blinkIdSdk] and [BlinkIdSessionSettings] to
 * configure the scanning session and provides callbacks for handling
 * successful capture and cancellation.
 *
 * @param blinkIdSdk The [BlinkIdSdk] instance used for document scanning.
 * @param uxSettings The [BlinkIdUxSettings] used to customize the UX.
 * @param uiSettings The [UiSettings] used to customize the UI.
 *                         Defaults to [UiSettings] with default values.
 * @param cameraSettings The [CameraSettings] used for document scanning.
 *                       Defaults to [CameraSettings] with default values.
 * @param sessionSettings The [BlinkIdSessionSettings] used to configure
 *                        the capture session. Defaults to [BlinkIdSessionSettings] with default values.
 * @param onScanningSuccess A callback function invoked when a document is
 *                         successfully captured. Receives the
 *                         [BlinkIdScanningResult] as a parameter.
 * @param onScanningCanceled A callback function invoked when the user cancels
 *                          the scanning process.
 * @param onFrameProcessResult An optional callback invoked after each camera frame is
 *                             processed, receiving a [FrameProcessResultHandle]. Use it to
 *                             inspect extraction completeness for the current frame and to
 *                             control the scanning flow (for example, by calling
 *                             [FrameProcessResultHandle.advanceToNextStep] when custom
 *                             conditions are met, or [FrameProcessResultHandle.triggerStepTimeout]
 *                             to implement custom step timeout logic). Defaults to `null`, in which
 *                             case the default scanning flow is used.
 *
 */
@Composable
fun BlinkIdCameraScanningScreen(
    blinkIdSdk: BlinkIdSdk,
    uxSettings: BlinkIdUxSettings = BlinkIdUxSettings(),
    uiSettings: UiSettings = UiSettings(),
    cameraSettings: CameraSettings = CameraSettings(),
    sessionSettings: BlinkIdSessionSettings = BlinkIdSessionSettings(),
    onScanningSuccess: (BlinkIdScanningResult) -> Unit,
    onScanningCanceled: () -> Unit,
    onFrameProcessResult: ((FrameProcessResultHandle) -> Unit)? = null
) {
    val viewModel: BlinkIdUxViewModel = viewModel(
        factory = BlinkIdUxViewModel.Factory,
        extras = MutableCreationExtras().apply {
            set(
                BlinkIdUxViewModel.BLINKID_SDK,
                blinkIdSdk
            )
            set(
                BlinkIdUxViewModel.BLINKID_SESSION_SETTINGS,
                sessionSettings
            )
            set(
                BlinkIdUxViewModel.BLINKID_UX_SETTINGS,
                uxSettings
            )
            set(
                BlinkIdUxViewModel.BLINKID_FRAME_PROCESS_RESULT_HANDLE,
                onFrameProcessResult
            )
        }
    )

    val applicationContext = LocalContext.current.applicationContext

    DeviceOrientationListener(applicationContext) {
        viewModel.setScreenOrientation(it)
    }

    var initialUiStateSet by rememberSaveable { mutableStateOf(false) }
    if (!initialUiStateSet) {
        viewModel.setInitialUiStateFromUiSettings(uiSettings)
        initialUiStateSet = true
    }

    val snackbarHostState = remember { SnackbarHostState() }

    DisposableEffect(Unit) {
        val observer = getDefaultLifecycleObserver(viewModel)
        val processLifecycle = ProcessLifecycleOwner.get().lifecycle
        processLifecycle.addObserver(observer)

        onDispose {
            processLifecycle.removeObserver(observer)
        }
    }

    val extractionMode = sessionSettings.toBlinkIdExtractionMode()

    BlinkIdSdkTheme(uiSettings) {
        val snackbarWarningMessage =
            stringResource(BlinkIdTheme.sdkStrings.scanningStrings.snackbarFlashlightWarning)
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->
            CameraScreen(
                cameraViewModel = viewModel,
                cameraSettings = cameraSettings,
                onCameraScreenLongPress = { viewModel.changeHelpTooltipVisibility(true) },
                cameraPermissionCallbacks = rememberCameraPermissionCallbacks(viewModel),
                cameraPreviewCallbacks = rememberCameraPreviewCallbacks(viewModel),
                cameraInputDetailsCallback = rememberCameraInputDetailsCallback(
                    viewModel,
                    applicationContext
                )
            ) {
                val overlayUiState = viewModel.uiState.collectAsStateWithLifecycle()

                if (overlayUiState.value.processingState == ProcessingState.Success) {
                    overlayUiState.value.blinkIdScanningResult?.let {
                        onScanningSuccess(it)
                    }
                }
                BackHandler {
                    onScanningCanceled()
                }
                ScanningUx(
                    Modifier.padding(paddingValues),
                    overlayUiState.value,
                    {
                        onCloseButtonClicked(viewModel.getSessionNumber())
                        onScanningCanceled()
                    },
                    uiSettings,
                    fillHelpScreens(extractionMode),
                    fillErrorDialogs(viewModel::onRetryTimeout, onScanningCanceled),
                    uxSettings.allowHapticFeedback,
                    allowScanSound = uxSettings.allowScanSound,
                    showProductionOverlay = !blinkIdSdk.getLicenseToken().licenseRights.allowRemoveProductionOverlay,
                    showDemoOverlay = !blinkIdSdk.getLicenseToken().licenseRights.allowRemoveDemoOverlay,
                    {
                        viewModel.changeTorchState()
                        viewModel.viewModelScope.launch {
                            if (viewModel.uiState.value.torchState == MbTorchState.On) {
                                snackbarHostState.showSnackbar(
                                    snackbarWarningMessage,
                                    null,
                                    false,
                                    SnackbarDuration.Short
                                )
                            }
                        }
                    },
                    viewModel::onFlipAnimationCompleted,
                    viewModel::onReticleSuccessAnimationCompleted,
                    viewModel::onHapticFeedbackCompleted,
                    viewModel::onScanSoundCompleted,
                    viewModel::changeOnboardingDialogVisibility,
                    viewModel::onHelpScreensDisplayRequested,
                    viewModel::onHelpScreensCloseRequested,
                    viewModel::changeHelpTooltipVisibility
                )
            }
        }
    }
}

@Composable
private fun rememberCameraPreviewCallbacks(viewModel: BlinkIdUxViewModel) = remember(viewModel) {
    object : CameraPreviewCallbacks {
        override fun onCameraPreviewStarted() {
            onCameraPreviewStarted(viewModel.getSessionNumber())
        }

        override fun onCameraPreviewStopped() {
            onCameraPreviewStopped(viewModel.getSessionNumber())
        }
    }
}

@Composable
private fun rememberCameraPermissionCallbacks(viewModel: BlinkIdUxViewModel) =
    remember(viewModel) {
        object : CameraPermissionCallbacks {
            override fun onCameraPermissionCheck() {
                onCameraPermissionCheck(viewModel.getSessionNumber())
            }

            override fun onCameraPermissionRequested() {
                onCameraPermissionRequest(viewModel.getSessionNumber())
            }

            override fun onCameraPermissionUserResponse(cameraPermissionGranted: Boolean) {
                onCameraPermissionUserResponse(
                    viewModel.getSessionNumber(),
                    cameraPermissionGranted
                )
            }

        }
    }

@Composable
private fun rememberCameraInputDetailsCallback(
    viewModel: BlinkIdUxViewModel,
    applicationContext: Context
) = remember(viewModel) {
    object : CameraInputDetailsCallback {
        override fun onCameraInputDetailsAvailable(cameraInputDetails: CameraInputDetails) {
            viewModel.onCameraInputInfoAvailable(
                applicationContext.applicationContext,
                cameraInputDetails
            )
        }
    }
}

private fun getDefaultLifecycleObserver(viewModel: BlinkIdUxViewModel): LifecycleObserver {
    val observer = object : DefaultLifecycleObserver {
        override fun onStop(owner: LifecycleOwner) {
            onAppMovedToBackground(viewModel.getSessionNumber())
        }

        override fun onPause(owner: LifecycleOwner) {
            viewModel.lifecyclePauseAnalysis()
        }

        override fun onResume(owner: LifecycleOwner) {
            viewModel.lifecycleResumeAnalysis()
        }
    }
    return observer
}