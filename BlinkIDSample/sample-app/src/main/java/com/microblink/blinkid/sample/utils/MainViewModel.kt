package com.microblink.blinkid.sample.utils

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microblink.blinkid.core.BlinkIdSdk
import com.microblink.blinkid.core.BlinkIdSdkSettings
import com.microblink.blinkid.core.session.BlinkIdScanningResult
import com.microblink.blinkid.core.session.BlinkIdSessionSettings
import com.microblink.blinkid.core.session.InputImageSource
import com.microblink.blinkid.core.session.ScanningMode
import com.microblink.blinkid.core.settings.ScanningSettings
import com.microblink.blinkid.sample.config.BlinkIdConfig.licenseKey
import com.microblink.blinkid.sample.result.BlinkIdResultHolder
import com.microblink.blinkid.ux.settings.BlinkIdUxSettings
import com.microblink.blinkid.ux.UiSettings
import com.microblink.blinkid.ux.camera.CameraSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
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

    val blinkIdUxSettings = BlinkIdUxSettings()

    val cameraSettings = CameraSettings()

    var stepTimeoutDuration: MutableState<Duration> = mutableStateOf(10000.milliseconds)
        private set

    var localSdk: BlinkIdSdk? = null
        private set

    val scanningSessionSettings = BlinkIdSessionSettings(
        inputImageSource = InputImageSource.Video,
        scanningMode = ScanningMode.Automatic,
        scanningSettings = ScanningSettings()
    )

    suspend fun initializeLocalSdk(context: Context) {
        _mainState.update {
            it.copy(displayLoading = true)
        }
        val maybeInstance = BlinkIdSdk.initializeSdk(
            context = context,
            BlinkIdSdkSettings(
                licenseKey = licenseKey
            )
        )
        when {
            maybeInstance.isSuccess -> {
                localSdk = maybeInstance.getOrNull()
            }

            maybeInstance.isFailure -> {
                val exception = maybeInstance.exceptionOrNull()
                Log.e(TAG, "Initialization failed", exception)
                _mainState.update {
                    it.copy(error = "Initialization failed: ${exception?.message}")
                }
            }
        }
        _mainState.update {
            it.copy(displayLoading = false)
        }
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