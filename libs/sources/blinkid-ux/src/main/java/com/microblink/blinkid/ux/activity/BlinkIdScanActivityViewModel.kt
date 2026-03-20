package com.microblink.blinkid.ux.activity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microblink.blinkid.core.BlinkIdSdk
import com.microblink.blinkid.core.BlinkIdSdkSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BlinkIdScanActivityViewModel : ViewModel() {

    private val _displayLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var displayLoading = _displayLoading.asStateFlow()

    var localSdk: BlinkIdSdk? = null
        private set

    suspend fun initializeLocalSdk(
        context: Context,
        blinkIdSdkSettings: BlinkIdSdkSettings,
        onInitFailed: (exception: Throwable?) -> Unit
    ) {
        _displayLoading.update {
            true
        }

        val maybeInstance = BlinkIdSdk.initializeSdk(
            context,
            blinkIdSdkSettings
        )
        when {
            maybeInstance.isSuccess -> {
                localSdk = maybeInstance.getOrNull()
                _displayLoading.update {
                    false
                }
            }

            maybeInstance.isFailure -> {
                onInitFailed(maybeInstance.exceptionOrNull())
            }
        }
    }

    fun unloadSdk() {
        val sdkToClose = localSdk
        localSdk = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                sdkToClose?.close()
            } catch (_: Exception) {
            }
        }
    }

    fun unloadSdkAndDeleteCachedAssets() {
        val sdkToClose = localSdk
        localSdk = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                sdkToClose?.closeAndDeleteCachedAssets()
            } catch (_: Exception) {
            }
        }
    }

}