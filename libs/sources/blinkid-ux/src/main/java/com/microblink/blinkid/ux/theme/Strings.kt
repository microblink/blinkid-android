/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.theme

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.theme.SdkStrings.Companion.Default
import kotlinx.parcelize.Parcelize


/**
 * Contains the common strings used by the shared scanning UX layer.
 *
 * Each SDK can extend the scanning strings to add SDK-specific instruction messages.
 * Help dialog and onboarding strings are not included here as they are SDK-specific
 * and are provided directly via [com.microblink.blinkid.ux.components.HelpScreens] when
 * constructing the scanning screen.
 *
 * [Default] can be used to keep the original strings if only some of the elements are to be changed.
 *
 * This class shouldn't be modified, but rather a new instance should be
 * created and used in [com.microblink.blinkid.ux.UiSettings.sdkStrings].
 *
 * @property scanningStrings Strings that appear as instruction messages during the scanning session.
 * These instructions are triggered by specific UX events and will appear on screen accordingly.
 * @property accessibilityStrings Strings that are used by accessibility TalkBack service for specific
 * buttons, labels, and actions.
 */
@Immutable
@Parcelize
open class SdkStrings(
    val scanningStrings: ScanningStrings,
    val accessibilityStrings: AccessibilityStrings
) : Parcelable {
    companion object {
        val Default: SdkStrings =
            SdkStrings(
                scanningStrings = ScanningStrings.Empty,
                accessibilityStrings = AccessibilityStrings.Default
            )
    }
}

/**
 * @see com.microblink.blinkid.ux.theme.SdkStrings
 */
@Immutable
@Parcelize
open class ScanningStrings(
    @param:StringRes @get:StringRes open val instructionsFirstSide: Int,
    @param:StringRes @get:StringRes open val instructionsSecondSide: Int,
    @param:StringRes @get:StringRes open val instructionsFlip: Int,
    @param:StringRes @get:StringRes open val instructionsNotFullyVisible: Int,
    @param:StringRes @get:StringRes open val instructionsTilted: Int,
    @param:StringRes @get:StringRes open val instructionsScanningWrongSide: Int,
    @param:StringRes @get:StringRes open val instructionsBlurDetected: Int,
    @param:StringRes @get:StringRes open val instructionsMoveFarther: Int,
    @param:StringRes @get:StringRes open val instructionsMoveCloser: Int,
    @param:StringRes @get:StringRes open val snackbarFlashlightWarning: Int
) : Parcelable {
    companion object {
        @JvmStatic val Empty = ScanningStrings(
            instructionsFirstSide = 0,
            instructionsSecondSide = 0,
            instructionsFlip = 0,
            instructionsNotFullyVisible = 0,
            instructionsTilted = 0,
            instructionsScanningWrongSide = 0,
            instructionsBlurDetected = 0,
            instructionsMoveFarther = 0,
            instructionsMoveCloser = 0,
            snackbarFlashlightWarning = 0
        )
    }
}

/**
 * @see com.microblink.blinkid.ux.theme.SdkStrings
 */
@Immutable
@Parcelize
data class AccessibilityStrings(
    @param:StringRes @get:StringRes val scanCompleted: Int,
    @param:StringRes @get:StringRes val firstSideScanned: Int,
    @param:StringRes @get:StringRes val previousPage: Int,
    @param:StringRes @get:StringRes val nextPage: Int,
    @param:StringRes @get:StringRes val showHelpScreens: Int,
    @param:StringRes @get:StringRes val turnFlashlightOff: Int,
    @param:StringRes @get:StringRes val turnFlashlightOn: Int,
    @param:StringRes @get:StringRes val exitScanning: Int,
    @param:StringRes @get:StringRes val flashlightOff: Int,
    @param:StringRes @get:StringRes val flashlightOn: Int
) : Parcelable {
    companion object {
        @JvmStatic val Default: AccessibilityStrings = AccessibilityStrings(
            scanCompleted = R.string.mb_blinkid_accessibility_success_document_scanned,
            firstSideScanned = R.string.mb_blinkid_accessibility_success_first_side_scanned,
            previousPage = R.string.mb_blinkid_accessibility_previous_page,
            nextPage = R.string.mb_blinkid_accessibility_next_page,
            showHelpScreens = R.string.mb_blinkid_accessibility_show_help_screens,
            turnFlashlightOff = R.string.mb_blinkid_accessibility_turn_flashlight_off,
            turnFlashlightOn = R.string.mb_blinkid_accessibility_turn_flashlight_on,
            exitScanning = R.string.mb_blinkid_accessibility_exit_scanning,
            flashlightOff = R.string.mb_blinkid_accessibility_flashlight_off,
            flashlightOn = R.string.mb_blinkid_accessibility_flashlight_on
        )
    }
}

/**
 * SDK-specific onboarding and help dialog strings.
 *
 * The number of [helpTitles] and [helpMessages] entries must match the number
 * of help dialog pages for the SDK flow using these strings.
 */
@Immutable
@Parcelize
data class HelpDialogsStrings(
    @param:StringRes @get:StringRes val onboardingTitle: Int,
    @param:StringRes @get:StringRes val onboardingMessage: Int,
    @param:StringRes @get:StringRes val helpTitles: List<Int>,
    @param:StringRes @get:StringRes val helpMessages: List<Int>
) : Parcelable

var LocalBaseSdkStrings = staticCompositionLocalOf { Default }