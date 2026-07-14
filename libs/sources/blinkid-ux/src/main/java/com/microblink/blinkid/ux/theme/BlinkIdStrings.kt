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
import com.microblink.blinkid.ux.theme.BlinkIdSdkStrings.Companion.Default
import kotlinx.parcelize.Parcelize

/**
 * Data class contains all the strings used throughout the SDK.
 * [Default] can be used to keep the original strings if only some of the elements are to be changed.
 *
 * This class shouldn't be modified, but rather a new instance should be
 * created and used in [com.microblink.blinkid.ux.UiSettings.sdkStrings].
 *
 * @property blinkIdScanningStrings Strings that appear as instruction messages during the scanning session.
 *           These instructions are triggered by specific UX events and will appear on screen accordingly.
 *           Includes both BlinkID specific and common SDK strings.
 * @property blinkIdDocumentHelpDialogsStrings Strings used in onboarding and help dialogs for document scanning mode.
 *           These strings shouldn't be customized as they provide adequate instructions tailored specifically to our scanning experience.
 *           However, if the scanning experience is changed in any way, onboarding and help screen instructions may also be adjusted.
 * @property blinkIdBarcodeHelpDialogsStrings Strings used in onboarding and help dialogs for barcode-only scanning mode.
 *           These strings shouldn't be customized as they provide adequate instructions tailored specifically to our scanning experience.
 *           However, if the scanning experience is changed in any way, onboarding and help screen instructions may also be adjusted.
 * @property blinkIdBarcodeIdHelpDialogsStrings Strings used in onboarding and help dialogs for barcode + ID scanning mode.
 *           These strings shouldn't be customized as they provide adequate instructions tailored specifically to our scanning experience.
 *           However, if the scanning experience is changed in any way, onboarding and help screen instructions may also be adjusted.
 * @property blinkIdMrzHelpDialogsStrings Strings used in onboarding and help dialogs for MRZ document scanning mode.
 *           These strings shouldn't be customized as they provide adequate instructions tailored specifically to our scanning experience.
 *           However, if the scanning experience is changed in any way, onboarding and help screen instructions may also be adjusted.
 * @property blinkIdAccessibilityStrings Strings that are used by accessibility TalkBack service for specific
 *           buttons, labels, and actions.
 */
@Immutable
@Parcelize
data class BlinkIdSdkStrings(
    val blinkIdScanningStrings: BlinkIdScanningStrings,
    val blinkIdDocumentHelpDialogsStrings: HelpDialogsStrings,
    val blinkIdBarcodeHelpDialogsStrings: HelpDialogsStrings,
    val blinkIdBarcodeIdHelpDialogsStrings: HelpDialogsStrings,
    val blinkIdMrzHelpDialogsStrings: HelpDialogsStrings,
    val blinkIdAccessibilityStrings: AccessibilityStrings
) : Parcelable, SdkStrings(
    ScanningStrings(
        blinkIdScanningStrings.instructionsFirstSide,
        blinkIdScanningStrings.instructionsSecondSide,
        blinkIdScanningStrings.instructionsFlip,
        blinkIdScanningStrings.instructionsNotFullyVisible,
        blinkIdScanningStrings.instructionsTilted,
        blinkIdScanningStrings.instructionsScanningWrongSide,
        blinkIdScanningStrings.instructionsBlurDetected,
        blinkIdScanningStrings.instructionsMoveFarther,
        blinkIdScanningStrings.instructionsMoveCloser,
        blinkIdScanningStrings.snackbarFlashlightWarning
    ),
    blinkIdAccessibilityStrings
) {
    companion object {
        /**
         * Default onboarding and help dialog strings for document scanning mode.
         *
         * Can be used as a base when customizing [blinkIdDocumentHelpDialogsStrings].
         */
        @JvmStatic
        val DocumentHelpDialogsDefaults = HelpDialogsStrings(
            onboardingTitle = R.string.mb_blinkid_onboarding_dialog_title,
            onboardingMessage = R.string.mb_blinkid_onboarding_dialog_message,
            helpTitles = listOf(
                R.string.mb_blinkid_help_screen_title1,
                R.string.mb_blinkid_help_screen_title2,
                R.string.mb_blinkid_help_screen_title3
            ),
            helpMessages = listOf(
                R.string.mb_blinkid_help_screen_msg1,
                R.string.mb_blinkid_help_screen_msg2,
                R.string.mb_blinkid_help_screen_msg3,
            )
        )

        /**
         * Default onboarding and help dialog strings for barcode-only scanning mode.
         *
         * Can be used as a base when customizing [blinkIdBarcodeHelpDialogsStrings].
         */
        @JvmStatic
        val BarcodeHelpDialogsDefaults = HelpDialogsStrings(
            onboardingTitle = R.string.mb_blinkid_onboarding_dialog_barcode_title,
            onboardingMessage = R.string.mb_blinkid_onboarding_dialog_barcode_message,
            helpTitles = listOf(
                R.string.mb_blinkid_help_screen_barcode_title1,
                R.string.mb_blinkid_help_screen_title2,
                R.string.mb_blinkid_help_screen_title3
            ),
            helpMessages = listOf(
                R.string.mb_blinkid_help_screen_barcode_msg1,
                R.string.mb_blinkid_help_screen_barcode_msg2,
                R.string.mb_blinkid_help_screen_barcode_msg3,
            )
        )

        /**
         * Default onboarding and help dialog strings for barcode + ID scanning mode.
         *
         * Can be used as a base when customizing [blinkIdBarcodeIdHelpDialogsStrings].
         */
        @JvmStatic
        val BarcodeIdHelpDialogsDefaults = HelpDialogsStrings(
            onboardingTitle = R.string.mb_blinkid_onboarding_dialog_barcode_id_title,
            onboardingMessage = R.string.mb_blinkid_onboarding_dialog_barcode_id_message,
            helpTitles = listOf(
                R.string.mb_blinkid_help_screen_barcode_title1,
                R.string.mb_blinkid_help_screen_title2,
                R.string.mb_blinkid_help_screen_title3
            ),
            helpMessages = listOf(
                R.string.mb_blinkid_help_screen_barcode_msg1,
                R.string.mb_blinkid_help_screen_msg2,
                R.string.mb_blinkid_help_screen_msg3,
            )
        )

        /**
         * Default onboarding and help dialog strings for MRZ document scanning mode.
         *
         * Can be used as a base when customizing [blinkIdMrzHelpDialogsStrings].
         */
        @JvmStatic
        val MrzHelpDialogsDefaults = HelpDialogsStrings(
            onboardingTitle = R.string.mb_blinkid_onboarding_dialog_mrz_id_title,
            onboardingMessage = R.string.mb_blinkid_onboarding_dialog_mrz_id_message,
            helpTitles = listOf(
                R.string.mb_blinkid_help_screen_mrz_title1,
                R.string.mb_blinkid_help_screen_title2,
                R.string.mb_blinkid_help_screen_title3
            ),
            helpMessages = listOf(
                R.string.mb_blinkid_help_screen_mrz_msg1,
                R.string.mb_blinkid_help_screen_msg2,
                R.string.mb_blinkid_help_screen_msg3,
            )
        )

        @JvmStatic
        val Default: BlinkIdSdkStrings =
            BlinkIdSdkStrings(
                blinkIdScanningStrings = BlinkIdScanningStrings.Default,
                blinkIdDocumentHelpDialogsStrings = DocumentHelpDialogsDefaults,
                blinkIdBarcodeHelpDialogsStrings = BarcodeHelpDialogsDefaults,
                blinkIdBarcodeIdHelpDialogsStrings = BarcodeIdHelpDialogsDefaults,
                blinkIdMrzHelpDialogsStrings = MrzHelpDialogsDefaults,
                blinkIdAccessibilityStrings = AccessibilityStrings.Default
            )
    }

    init {
        LocalBaseSdkStrings = staticCompositionLocalOf { Default }
    }
}

/**
 * Includes common SDK strings from [ScanningStrings] and BlinkID specific strings.
 */
@Immutable
@Parcelize
data class BlinkIdScanningStrings(
    @param:StringRes @get:StringRes override val instructionsFirstSide: Int,
    @param:StringRes @get:StringRes override val instructionsSecondSide: Int,
    @param:StringRes @get:StringRes val instructionsBarcode: Int,
    @param:StringRes @get:StringRes override val instructionsFlip: Int,
    @param:StringRes @get:StringRes val instructionsBarcodeOnlyModule: Int,
    @param:StringRes @get:StringRes val instructionsBarcodeIdModule: Int,
    @param:StringRes @get:StringRes val instructionsMrzModule: Int,
    @param:StringRes @get:StringRes override val instructionsNotFullyVisible: Int,
    @param:StringRes @get:StringRes override val instructionsTilted: Int,
    @param:StringRes @get:StringRes val instructionsFacePhotoNotFullyVisible: Int,
    @param:StringRes @get:StringRes override val instructionsScanningWrongSide: Int,
    @param:StringRes @get:StringRes override val instructionsBlurDetected: Int,
    @param:StringRes @get:StringRes val instructionsGlareDetected: Int,
    @param:StringRes @get:StringRes override val instructionsMoveFarther: Int,
    @param:StringRes @get:StringRes override val instructionsMoveCloser: Int,
    @param:StringRes @get:StringRes val instructionsIncreaseLight: Int,
    @param:StringRes @get:StringRes val instructionsDecreaseLight: Int,
    @param:StringRes @get:StringRes override val snackbarFlashlightWarning: Int,
    @param:StringRes @get:StringRes val instructionsPassportDataPage: Int,
    @param:StringRes @get:StringRes val instructionsPassportMoveToTopPage: Int,
    @param:StringRes @get:StringRes val instructionsPassportMoveToRightPage: Int,
    @param:StringRes @get:StringRes val instructionsPassportMoveToLeftPage: Int,
    @param:StringRes @get:StringRes val instructionsPassportMoveToBarcodePage: Int,
    @param:StringRes @get:StringRes val instructionsPassportWrongPageTop: Int,
    @param:StringRes @get:StringRes val instructionsPassportWrongPageRight: Int,
    @param:StringRes @get:StringRes val instructionsPassportWrongPageLeft: Int,
    @param:StringRes @get:StringRes val instructionsPassportWrongPageBarcode: Int,
    @param:StringRes @get:StringRes val instructionsPassportScanTopPage: Int,
    @param:StringRes @get:StringRes val instructionsPassportScanRightPage: Int,
    @param:StringRes @get:StringRes val instructionsPassportScanLeftPage: Int,
    @param:StringRes @get:StringRes val instructionsPassportScanBarcodePage: Int,
    @param:StringRes @get:StringRes val instructionsBarcodeWrongSide: Int
) : Parcelable, ScanningStrings(
    instructionsFirstSide,
    instructionsSecondSide,
    instructionsFlip,
    instructionsNotFullyVisible,
    instructionsTilted,
    instructionsScanningWrongSide,
    instructionsBlurDetected,
    instructionsMoveFarther,
    instructionsMoveCloser,
    snackbarFlashlightWarning
) {
    companion object {
        @JvmStatic
        val Default: BlinkIdScanningStrings =
            BlinkIdScanningStrings(
                instructionsFirstSide = R.string.mb_blinkid_front_instructions,
                instructionsSecondSide = R.string.mb_blinkid_back_instructions,
                instructionsBarcode = R.string.mb_blinkid_barcode_instructions,
                instructionsFlip = R.string.mb_blinkid_camera_flip_document,
                instructionsBarcodeOnlyModule = R.string.mb_blinkid_barcode_instructions,
                instructionsBarcodeIdModule = R.string.mb_blinkid_barcode_id_instructions,
                instructionsMrzModule = R.string.mb_blinkid_mrz_id_instructions,
                instructionsNotFullyVisible = R.string.mb_blinkid_document_not_fully_visible,
                instructionsTilted = R.string.mb_blinkid_keep_document_parallel,
                instructionsFacePhotoNotFullyVisible = R.string.mb_blinkid_face_photo_not_fully_visible,
                instructionsScanningWrongSide = R.string.mb_blinkid_scanning_wrong_side,
                instructionsBlurDetected = R.string.mb_blinkid_blur_detected,
                instructionsGlareDetected = R.string.mb_blinkid_glare_detected,
                instructionsMoveFarther = R.string.mb_blinkid_move_farther,
                instructionsMoveCloser = R.string.mb_blinkid_move_closer,
                instructionsIncreaseLight = R.string.mb_blinkid_increase_lighting_intensity,
                instructionsDecreaseLight = R.string.mb_blinkid_decrease_lighting_intensity,
                snackbarFlashlightWarning = R.string.mb_blinkid_flashlight_warning_message,
                instructionsPassportDataPage = R.string.mb_blinkid_passport_scan_data_page_instructions,
                instructionsPassportMoveToTopPage = R.string.mb_blinkid_instructions_turn_page_top,
                instructionsPassportMoveToRightPage = R.string.mb_blinkid_instructions_turn_page_right,
                instructionsPassportMoveToLeftPage = R.string.mb_blinkid_instructions_turn_page_left,
                instructionsPassportMoveToBarcodePage = R.string.mb_blinkid_instructions_scan_barcode_last_page,
                instructionsPassportWrongPageTop = R.string.mb_blinkid_scanning_wrong_page_top,
                instructionsPassportWrongPageRight = R.string.mb_blinkid_scanning_wrong_page_right,
                instructionsPassportWrongPageLeft = R.string.mb_blinkid_scanning_wrong_page_left,
                instructionsPassportWrongPageBarcode = R.string.mb_blinkid_instructions_scan_barcode_last_page,
                instructionsPassportScanTopPage = R.string.mb_blinkid_top_page_instructions,
                instructionsPassportScanRightPage = R.string.mb_blinkid_right_page_instructions,
                instructionsPassportScanLeftPage = R.string.mb_blinkid_left_page_instructions,
                instructionsPassportScanBarcodePage = R.string.mb_blinkid_instructions_scan_barcode_last_page,
                instructionsBarcodeWrongSide = R.string.mb_blinkid_barcode_instructions
            )
    }
}

var LocalBaseBlinkIdSdkStrings = staticCompositionLocalOf {
    BlinkIdSdkStrings.Default
}