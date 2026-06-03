package com.microblink.blinkid.ux.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.os.BundleCompat
import com.microblink.blinkid.core.BlinkIdSdkSettings
import com.microblink.blinkid.core.session.BlinkIdScanningResult
import com.microblink.blinkid.core.session.BlinkIdSessionSettings
import com.microblink.blinkid.ux.DefaultShowHelpButton
import com.microblink.blinkid.ux.DefaultShowOnboardingDialog
import com.microblink.blinkid.ux.activity.BlinkIdScanActivity
import com.microblink.blinkid.ux.camera.CameraSettings
import com.microblink.blinkid.ux.settings.BlinkIdUxSettings
import com.microblink.blinkid.ux.theme.SdkStrings
import com.microblink.blinkid.ux.utils.ParcelableUiTypography
import kotlinx.parcelize.Parcelize

class MbBlinkIdScan :
    ActivityResultContract<BlinkIdScanActivitySettings, BlinkIdScanActivityResult>() {
    override fun createIntent(context: Context, input: BlinkIdScanActivitySettings): Intent {
        return Intent(context, BlinkIdScanActivity::class.java).also {
            input.saveToIntent(it)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): BlinkIdScanActivityResult {
        if (resultCode == Activity.RESULT_OK) {
            BlinkIdScanningResultHolder.blinkIdScanningResult?.let { result ->
                return BlinkIdScanActivityResult(
                    status = ScanActivityResultStatus.Scanned,
                    result = result
                )
            }
            throw IllegalStateException("Activity was completed successfully, but the result is empty!")
        } else {
            val cancelReason = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getSerializableExtra(EXTRA_CANCEL_REASON, CancelReason::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent?.getSerializableExtra(EXTRA_CANCEL_REASON) as? CancelReason
            }
            return when (cancelReason) {
                CancelReason.ErrorSdkInit ->
                    BlinkIdScanActivityResult(
                        status = ScanActivityResultStatus.ErrorSdkInit,
                        result = null
                    )

                CancelReason.UserRequested -> BlinkIdScanActivityResult(
                    status = ScanActivityResultStatus.Canceled,
                    result = null
                )

                else -> BlinkIdScanActivityResult(
                    status = ScanActivityResultStatus.Canceled,
                    result = null
                )
            }
        }
    }

    companion object {
        const val EXTRA_CANCEL_REASON = "ExtraCancelReason"
    }

}

/**
 * Configuration settings for the [BlinkIdScanActivity].
 *
 * This data class encapsulates the various settings that control the behavior and appearance of
 * the [BlinkIdScanActivity], including the SDK settings, scanning session configurations,
 * UI customization options, and other miscellaneous preferences.
 *
 * @property sdkSettings The core SDK settings required for initializing and
 *           running the BlinkID SDK. This is a mandatory parameter.
 * @property cameraSettings The [CameraSettings] used for document scanning. Defaults to [CameraSettings] with default values.
 * @property scanningSessionSettings Configuration options for the document scanning session. This
 *           allows you to customize aspects of the scanning process, such as certain visual check strictness
 *           and timeout duration. Defaults to `BlinkIdSessionSettings()`.
 * @property uxSettings The [BlinkIdUxSettings] used to customize the UX.
 * @property scanActivityUiColors Custom colors for the `BlinkIdScanActivity` user interface.
 *           If set to `null`, the default colors will be used. Defaults to `null`.
 * @property scanActivityUiStrings Custom strings for the `BlinkIdScanActivity` user
 *           interface. Defaults to [SdkStrings.Default].
 * @property scanActivityTypography Custom typography for the `BlinkIdScanActivity` user
 *           interface. Due to a limitation of [Typography] class, [ParcelableUiTypography] mimics
 *           [com.microblink.blinkid.ux.theme.UiTypography] by allowing the customization of all the elements to a lesser extent.
 *           The most important [TextStyle] and [Font] customizations are still available through this class.
 *           Defaults to [ParcelableUiTypography.Default].
 * @property showOnboardingDialog Determines whether an onboarding dialog should be displayed to
 *           the user when the activity is first launched. Defaults to [DefaultShowOnboardingDialog].
 * @property showHelpButton Determines whether a help button should be displayed in the activity.
 *           The button allows the user to open help screens during scanning. Defaults to [DefaultShowHelpButton].
 * @property enableEdgeToEdge Enables edge-to-edge display for the activity. This is the default behavior for
 *           all Android 15 devices (and above) and cannot be changed by this setting. In order to have this behavior
 *           on older OS versions, this setting should be set to `true`. Padding and window insets are
 *           handled automatically. Defaults to `true`.
 * @property deleteCachedAssetsAfterUse Indicates whether cached SDK assets should be deleted after
 *           they are used. If the scanning session is used only once (e.g. for user onboarding), the setting
 *           should be set to `true`. Otherwise, the setting should be set to `true` to avoid assets being re-downloaded.
 *           This only applies if `DOWNLOAD_RESOURCES` variant of the SDK is used. Defaults to `false`.
 *
 */
@Parcelize
data class BlinkIdScanActivitySettings @JvmOverloads constructor(
    val sdkSettings: BlinkIdSdkSettings,
    val scanningSessionSettings: BlinkIdSessionSettings = BlinkIdSessionSettings(),
    val uxSettings: BlinkIdUxSettings = BlinkIdUxSettings(),
    override val cameraSettings: CameraSettings = CameraSettings(),
    override val scanActivityUiColors: ScanActivityColors? = null,
    override val scanActivityUiStrings: SdkStrings = SdkStrings.Default,
    override val scanActivityTypography: ParcelableUiTypography = ParcelableUiTypography.Default(
        null
    ),
    override val showOnboardingDialog: Boolean = DefaultShowOnboardingDialog,
    override val showHelpButton: Boolean = DefaultShowHelpButton,
    override val enableEdgeToEdge: Boolean = true,
    override val deleteCachedAssetsAfterUse: Boolean = false
) : ScanActivitySettings {
    internal fun saveToIntent(intent: Intent) {
        intent.putExtra(INTENT_EXTRAS_BLINKID_SETTINGS, this)
    }

    override fun describeContents(): Int {
        return 0
    }

    internal companion object {
        internal fun loadFromIntent(intent: Intent): BlinkIdScanActivitySettings {
            return intent.extras?.let {
                BundleCompat.getParcelable(
                    it, INTENT_EXTRAS_BLINKID_SETTINGS,
                    BlinkIdScanActivitySettings::class.java
                )
            }
                ?: throw java.lang.IllegalStateException("Intent does not contain expected BlinkIdScanActivitySettings!")
        }

        private const val INTENT_EXTRAS_BLINKID_SETTINGS = "MbBlinkIdSettings"
    }
}

/**
 * Class containing results of the BlinkID Scanning activity.
 *
 * @property status Represents the status of the activity result and shows whether the activity completed
 *                  successfully or what caused it not to complete.
 * @property result Result of the document scanning session activity.
 *                  Result is present only if [status] is [ActivityResultStatus.DocumentScanned], otherwise it is null.
 *
 */
public data class BlinkIdScanActivityResult(
    val status: ScanActivityResultStatus,
    val result: BlinkIdScanningResult?
)