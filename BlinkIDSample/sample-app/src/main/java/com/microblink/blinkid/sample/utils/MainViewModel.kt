package com.microblink.blinkid.sample.utils

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microblink.blinkid.core.BlinkIdSdk
import com.microblink.blinkid.core.BlinkIdSdkSettings
import com.microblink.blinkid.core.session.BlinkIdScanningResult
import com.microblink.blinkid.core.session.BlinkIdSessionSettings
import com.microblink.blinkid.core.session.InputImageSource
import com.microblink.blinkid.core.session.ScanningMode
import com.microblink.blinkid.core.settings.OtaResourcesConfig
import com.microblink.blinkid.core.settings.ResourcesConfig
import com.microblink.blinkid.core.settings.ScanningSettings
import com.microblink.blinkid.core.settings.scanning.BarcodeModuleSettings
import com.microblink.blinkid.core.settings.scanning.DocumentCaptureModuleSettings
import com.microblink.blinkid.core.settings.scanning.VizModuleSettings
import com.microblink.blinkid.sample.config.BlinkIdConfig.licenseKey
import com.microblink.blinkid.sample.result.BlinkIdResultHolder
import com.microblink.blinkid.ux.UiSettings
import com.microblink.blinkid.ux.camera.CameraSettings
import com.microblink.blinkid.ux.scanning.FrameProcessResultHandle
import com.microblink.blinkid.ux.scanning.FrameProcessResultHandle.LastFrameResult
import com.microblink.blinkid.ux.settings.BlinkIdUxSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "MainViewModel"

data class MainState(
    val error: String? = null,
    val displayLoading: Boolean = false
)

class MainViewModel : ViewModel() {
    private val _mainState = MutableStateFlow(MainState())
    var mainState = _mainState.asStateFlow()

    val blinkIdUiSettings = UiSettings()

    val stepTimeoutDuration = 60000.milliseconds

    val inactivityTimeoutDuration = 10000.milliseconds

    val blinkIdUxSettings = BlinkIdUxSettings(
        // Customize step timeout duration, which is used to set the duration of the scanning step
        // during the scanning session before a timeout is triggered. This timer will reset whenever
        // one side of the document is successfully scanned or when the barcode step is triggered.
        stepTimeoutDuration = stepTimeoutDuration,
        // Customize inactivity timeout duration, which is used to set the duration of inactivity
        // during the scanning session (time without UI state changes) before a timeout is triggered.
        inactivityTimeoutDuration = inactivityTimeoutDuration
    )

    val cameraSettings = CameraSettings()

    // Resources / OTA settings applied on the next SDK initialization.
    // Defaults match ResourcesConfig() / OtaResourcesConfig().
    var downloadResources by mutableStateOf(true)
        private set

    var updateOtaResources by mutableStateOf(true)
        private set

    var failIfOtaFails by mutableStateOf(false)
        private set

    var otaServiceUrl by mutableStateOf(OtaResourcesConfig.defaultOtaDownloadUrl)
        private set

    fun updateDownloadResources(enabled: Boolean) {
        downloadResources = enabled
    }

    fun updateOtaResourcesEnabled(enabled: Boolean) {
        updateOtaResources = enabled
    }

    fun updateFailIfOtaFails(enabled: Boolean) {
        failIfOtaFails = enabled
    }

    fun updateOtaServiceUrl(url: String) {
        otaServiceUrl = url.trim()
    }

    var localSdk: BlinkIdSdk? = null
        private set

    var bitmapSaved: LastFrameResult? by mutableStateOf(null)
        private set

    val scanningSessionSettings = BlinkIdSessionSettings(
        inputImageSource = InputImageSource.Video,
        scanningMode = ScanningMode.Automatic,
        scanningSettings = ScanningSettings(
            // All the individual modules are turned on by default.
            // Use modular ScanningSettings to customize the behavior of the scanning session.
            // Disable any individual module by setting it to null.
            // Document capture module is used for capturing the image of the document.
            documentCaptureModule = DocumentCaptureModuleSettings(
                // Ensure that the extracted images are returned in the BlinkIdScanningResult object.
                inputImageReturnEnabled = true,
                faceImageExtractionEnabled = true
            ),
            // Barcode module is used for capturing and extracting the data from the barcode.
            barcodeModule = BarcodeModuleSettings(
                barcodeImageReturnEnabled = true
            ),
            // Viz module is used for extracting all the individual data fields from the document
            // and capturing signature images.
            vizModule = VizModuleSettings(
                signatureImageExtractionEnabled = true,
                // By setting the presenceMandatory to true, it is ensured
                // that the scan will not complete before all elements of specific modules
                // are successfully extracted from the document.
                presenceMandatory = true
            ),
            // Mrz module is used for extracting the data from MRZ (Machine readable zone).
            // By setting the mrzModule to null, the MRZ will be
            // completely ignored during the scanning session.
            mrzModule = null
        )
    )

    val frameProcessResultCallback: ((FrameProcessResultHandle) -> Unit) =
        { handle: FrameProcessResultHandle ->
            val frame = handle.getLastFrame()
            viewModelScope.launch(Dispatchers.Main) {
                // The last processed frame is saved to the variable and can be used
                // for further debugging or analysis after the scanning is finished.
                bitmapSaved = frame
            }

            // By uncommenting the following lines, current scanning step will be completed
            // when the first name and document number are successfully extracted from the document.
            // This would force the session to either progress to the second side of the document or
            // to complete the scan, depending on the scanning settings.
            /*
            if (handle.processResult.inputImageAnalysisResult.extractedFields.contains(
                    FieldType.FirstName
                ) && handle.processResult.inputImageAnalysisResult.extractedFields.contains(
                    FieldType.DocumentNumber
                )
            ) {
                handle.advanceToNextStep()
            }
            */
        }

    /**
     * Initializes the SDK with the current resources / OTA settings.
     *
     * @return `true` when initialization succeeds and [localSdk] is ready to use.
     */
    suspend fun initializeLocalSdk(context: Context): Boolean {
        _mainState.update {
            it.copy(displayLoading = true)
        }
        val maybeInstance = BlinkIdSdk.initializeSdk(
            context = context,
            BlinkIdSdkSettings(
                licenseKey = licenseKey,
                resourcesConfig = ResourcesConfig(
                    download = downloadResources
                ),
                otaResourcesConfig = OtaResourcesConfig(
                    checkForUpdates = updateOtaResources,
                    strict = failIfOtaFails,
                    serviceUrl = otaServiceUrl
                )
            )
        )
        val success = when {
            maybeInstance.isSuccess -> {
                localSdk = maybeInstance.getOrNull()
                true
            }

            else -> {
                val exception = maybeInstance.exceptionOrNull()
                Log.e(TAG, "Initialization failed", exception)
                _mainState.update {
                    it.copy(error = "Initialization failed: ${exception?.message}")
                }
                false
            }
        }
        _mainState.update {
            it.copy(displayLoading = false)
        }
        return success
    }

    fun onScanningResultAvailable(result: BlinkIdScanningResult) {
        BlinkIdResultHolder.blinkIdResult = result
        // unload the SDK when not needed anymore to free up resources
        unloadSdk()
    }

    fun onScanningCanceled() {
        unloadSdk()
    }

    fun resetState() {
        BlinkIdResultHolder.blinkIdResult = null
        _mainState.update { MainState() }
    }

    private fun unloadSdk() {
        val sdkToClose = localSdk
        localSdk = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // don't delete cached resources
                sdkToClose?.close()
            } catch (_: Exception) {
                Log.w(TAG, "SDK is already closed")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        unloadSdk()
    }
}