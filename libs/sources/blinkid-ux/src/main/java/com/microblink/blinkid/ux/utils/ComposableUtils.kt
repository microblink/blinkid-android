package com.microblink.blinkid.ux.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.components.ErrorDialog
import com.microblink.blinkid.ux.components.HelpScreenPage
import com.microblink.blinkid.ux.components.HelpScreens
import com.microblink.blinkid.ux.state.ErrorState
import com.microblink.blinkid.ux.theme.BlinkIdTheme

/**
 * Holds the drawable resources for a specific help screen variant.
 *
 * Each variant corresponds to an [BlinkIdExtractionMode] and provides the resources displayed in the help/onboarding dialogs.
 *
 * @property onboardingImage Drawable shown on the onboarding dialog.
 * @property helpPageImages Drawables shown on each help screen page.
 */
sealed class HelpScreenResources(
    @param:DrawableRes @get:DrawableRes val onboardingImage: Int,
    @param:DrawableRes @get:DrawableRes val helpPageImages: List<Int>,
) {
    data object Document : HelpScreenResources(
        onboardingImage = R.drawable.mb_blinkid_onboarding_id,
        helpPageImages = listOf(
            R.drawable.mb_blinkid_help_id_page_one,
            R.drawable.mb_blinkid_help_id_page_two,
            R.drawable.mb_blinkid_help_id_page_three,
        ),
    )

    data object BarcodeOnly : HelpScreenResources(
        onboardingImage = R.drawable.mb_blinkid_onboarding_barcode,
        helpPageImages = listOf(
            R.drawable.mb_blinkid_help_barcode_page_one,
            R.drawable.mb_blinkid_help_barcode_page_two,
            R.drawable.mb_blinkid_help_barcode_page_three,
        ),
    )

    data object BarcodeId : HelpScreenResources(
        onboardingImage = R.drawable.mb_blinkid_onboarding_barcode_id,
        helpPageImages = listOf(
            R.drawable.mb_blinkid_help_barcode_id_page_one,
            R.drawable.mb_blinkid_help_barcode_id_page_two,
            R.drawable.mb_blinkid_help_barcode_id_page_three,
        ),
    )

    data object MrzDocument : HelpScreenResources(
        onboardingImage = R.drawable.mb_blinkid_onboarding_mrz,
        helpPageImages = listOf(
            R.drawable.mb_blinkid_help_mrz_page_one,
            R.drawable.mb_blinkid_help_mrz_page_two,
            R.drawable.mb_blinkid_help_mrz_page_three,
        ),
    )
}

@Composable
fun fillHelpScreens(extractionMode: BlinkIdExtractionMode): HelpScreens {
    val (resources, strings) = when (extractionMode) {
        BlinkIdExtractionMode.FullDocument ->
            Pair(
                HelpScreenResources.Document,
                BlinkIdTheme.sdkStrings.blinkIdDocumentHelpDialogsStrings
            )

        BlinkIdExtractionMode.BarcodeOnly ->
            Pair(
                HelpScreenResources.BarcodeOnly,
                BlinkIdTheme.sdkStrings.blinkIdBarcodeHelpDialogsStrings
            )

        BlinkIdExtractionMode.DocumentWithBarcode ->
            Pair(
                HelpScreenResources.BarcodeId,
                BlinkIdTheme.sdkStrings.blinkIdBarcodeIdHelpDialogsStrings
            )

        BlinkIdExtractionMode.DocumentWithMrz ->
            Pair(
                HelpScreenResources.MrzDocument,
                BlinkIdTheme.sdkStrings.blinkIdMrzHelpDialogsStrings
            )
    }

    return HelpScreens(
        onboardingDialogPage = HelpScreenPage(
            pageImage = resources.onboardingImage,
            pageTitle = strings.onboardingTitle,
            pageMessage = strings.onboardingMessage,
        ),
        helpDialogPages = resources.helpPageImages.mapIndexed { index, image ->
            HelpScreenPage(
                pageImage = image,
                pageTitle = strings.helpTitles[index],
                pageMessage = strings.helpMessages[index],
            )
        }
    )
}

@Composable
fun fillErrorDialogs(
    onRetry: () -> Unit,
    onDoneError: () -> Unit
): Map<ErrorState, @Composable () -> Unit> {
    return mapOf(
        ErrorState.NoError to {},
        ErrorState.ErrorInvalidLicense to
                {
                    ErrorDialog(
                        R.string.mb_blinkid_license_locked,
                        null,
                        R.string.mb_blinkid_close,
                        onButtonClick = onDoneError
                    )
                },
        ErrorState.ErrorInvalidSettings to
                {
                    ErrorDialog(
                        R.string.mb_blinkid_license_locked,
                        null,
                        R.string.mb_blinkid_close,
                        onButtonClick = onDoneError
                    )
                },
        ErrorState.ErrorGetResult to
                {
                    ErrorDialog(
                        R.string.mb_blinkid_license_locked,
                        null,
                        R.string.mb_blinkid_close,
                        onButtonClick = onDoneError
                    )
                },
        ErrorState.ErrorNetworkError to
                {
                    ErrorDialog(
                        R.string.mb_blinkid_license_locked,
                        null,
                        R.string.mb_blinkid_close,
                        onButtonClick = onDoneError
                    )
                },

        ErrorState.ErrorTimeoutExpired to
                {
                    ErrorDialog(
                        R.string.mb_blinkid_recognition_timeout_dialog_title,
                        R.string.mb_blinkid_recognition_timeout_dialog_message,
                        R.string.mb_blinkid_recognition_timeout_dialog_retry_button,
                        onButtonClick = onRetry
                    )
                },

        ErrorState.ErrorDocumentClassFiltered to
                {
                    ErrorDialog(
                        R.string.mb_blinkid_document_class_filtered_dialog_title,
                        R.string.mb_blinkid_document_class_filtered_dialog_message,
                        R.string.mb_blinkid_recognition_timeout_dialog_retry_button,
                        onButtonClick = onRetry
                    )
                },
        ErrorState.ErrorUnsupportedDocument to
                {
                    ErrorDialog(
                        R.string.mb_blinkid_unsupported_document_title,
                        R.string.mb_blinkid_unsupported_document_message,
                        R.string.mb_blinkid_recognition_timeout_dialog_retry_button,
                        onButtonClick = onRetry
                    )
                }
    )
}