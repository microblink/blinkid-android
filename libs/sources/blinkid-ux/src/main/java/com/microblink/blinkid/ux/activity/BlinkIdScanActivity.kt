package com.microblink.blinkid.ux.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.microblink.blinkid.core.ping.util.PingletTracker
import com.microblink.blinkid.core.utils.MbLog
import com.microblink.blinkid.ux.BlinkIdCameraScanningScreen
import com.microblink.blinkid.ux.components.LoadingScreen
import com.microblink.blinkid.ux.contract.BlinkIdScanActivitySettings
import com.microblink.blinkid.ux.contract.BlinkIdScanningResultHolder
import com.microblink.blinkid.ux.contract.CancelReason
import com.microblink.blinkid.ux.contract.MbBlinkIdScan
import com.microblink.blinkid.ux.createUiSettings
import com.microblink.blinkid.ux.theme.BlinkIdSdkTheme
import kotlinx.coroutines.launch

private const val TAG = "BlinkIdScanActivity"

class BlinkIdScanActivity : AppCompatActivity() {

    private val activityViewModel: BlinkIdScanActivityViewModel by viewModels()
    private lateinit var blinkIdScanActivitySettings: BlinkIdScanActivitySettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blinkIdScanActivitySettings = BlinkIdScanActivitySettings.loadFromIntent(intent)
        if (blinkIdScanActivitySettings.enableEdgeToEdge) enableEdgeToEdge()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onCancel(CancelReason.UserRequested)
            }
        })

        activityViewModel.viewModelScope.launch {
            activityViewModel.initializeLocalSdk(
                context = this@BlinkIdScanActivity,
                blinkIdSdkSettings = blinkIdScanActivitySettings.sdkSettings,
                onInitFailed = { exception ->
                    MbLog.e(TAG, exception) { "SDK initialization failed" }
                    onCancel(CancelReason.ErrorSdkInit)
                }
            )
        }

        PingletTracker.Log.trackInfo(
            context = this,
            logMessage = "Using BlinkIdScanActivity"
        )

        setContent {

            val uiSettings = createUiSettings(blinkIdScanActivitySettings)

            BlinkIdSdkTheme(uiSettings) {
                val displayLoading = activityViewModel.displayLoading.collectAsStateWithLifecycle()
                if (displayLoading.value) {
                    LoadingScreen()
                } else {
                    activityViewModel.localSdk?.let {
                        BlinkIdCameraScanningScreen(
                            blinkIdSdk = it,
                            uxSettings = blinkIdScanActivitySettings.uxSettings,
                            uiSettings = uiSettings,
                            cameraSettings = blinkIdScanActivitySettings.cameraSettings,
                            sessionSettings = blinkIdScanActivitySettings.scanningSessionSettings,
                            onScanningSuccess = { result ->
                                BlinkIdScanningResultHolder.blinkIdScanningResult = result
                                this.setResult(RESULT_OK)
                                this.finish()
                            },
                            onScanningCanceled = {
                                onCancel(CancelReason.UserRequested)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        if (blinkIdScanActivitySettings.deleteCachedAssetsAfterUse) {
            activityViewModel.unloadSdkAndDeleteCachedAssets()
        } else {
            activityViewModel.unloadSdk()
        }
        super.onDestroy()
    }

    fun onCancel(cancelReason: CancelReason) {
        val extras = Intent()
        extras.putExtra(MbBlinkIdScan.EXTRA_CANCEL_REASON, cancelReason)
        this.setResult(RESULT_CANCELED, extras)
        this.finish()

    }

}