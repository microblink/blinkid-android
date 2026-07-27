/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.microblink.blinkid.core.session.BlinkIdScanningResult
import com.microblink.blinkid.ux.DefaultShowHelpButton
import com.microblink.blinkid.ux.DefaultShowOnboardingDialog
import com.microblink.blinkid.ux.components.EmptyAnimation
import com.microblink.blinkid.ux.components.PassportPageAnimation
import com.microblink.blinkid.ux.theme.BlinkIdTheme
import com.microblink.blinkid.ux.utils.ScreenOrientation
import kotlin.time.Duration

data class BlinkIdUiState(
    val blinkIdScanningResult: BlinkIdScanningResult? = null,
    override val reticleState: ReticleState = ReticleState.Hidden,
    override val processingState: ProcessingState = ProcessingState.Sensing,
    override val cardAnimationState: CardAnimationState = CardAnimationState.Hidden,
    override val statusMessage: StatusMessage = CommonStatusMessage.ScanFirstSide,
    override val currentSide: UiScanningSide = UiScanningSide.First,
    override val torchState: MbTorchState = MbTorchState.Off,
    override val cancelRequestState: CancelRequestState = CancelRequestState.CancelNotRequested,
    override val helpButtonDisplayed: Boolean = DefaultShowHelpButton,
    override val helpDisplayed: Boolean = false,
    override val helpTooltipDisplayed: Boolean = false,
    override val onboardingDialogDisplayed: Boolean = DefaultShowOnboardingDialog,
    override val errorState: ErrorState = ErrorState.NoError,
    override val hapticFeedbackState: HapticFeedbackState = HapticFeedbackState.VibrationOff,
    override val scanSoundState: ScanSoundState = ScanSoundState.SoundOff,
    val screenOrientation: ScreenOrientation = ScreenOrientation.Unknown,
    val activePassportPage: PassportPage? = null
) : BaseUiState

/**
 * Represents all the instruction messages that may be shown
 * during the BlinkID scanning session.
 *
 * This enum class defines the various status messages that can be displayed to the
 * user during the BlinkID document scanning process. Each enum value corresponds to a
 * specific instruction or feedback message.
 */
enum class BlinkIdStatusMessage : StatusMessage {
    ScanBarcodeOnlyModule,
    ScanBarcodeIdModule,
    ScanMrzModule,
    ScanBarcode,
    RotateDocument,
    RotateDocumentShort,
    KeepFacePhotoVisible,
    IncreaseLightingIntensity,
    DecreaseLightingIntensity,
    EliminateGlare,
    FilterSpecificMessage,
    ScanPassportDataPage,
    PassportMoveToTop,
    PassportMoveToRight,
    PassportMoveToLeft,
    PassportMoveToBarcode,
    PassportWrongPageTop,
    PassportWrongPageRight,
    PassportWrongPageLeft,
    PassportWrongPageBarcode,
    PassportScanTopPage,
    PassportScanRightPage,
    PassportScanLeftPage,
    PassportScanBarcodePage,
    BarcodeWrongSide;

    @Composable
    override fun statusMessageToStringRes(): Int? {
        val strings = BlinkIdTheme.sdkStrings.blinkIdScanningStrings
        return when (this) {
            ScanBarcodeOnlyModule -> strings.instructionsBarcodeOnlyModule
            ScanBarcodeIdModule -> strings.instructionsBarcodeIdModule
            ScanMrzModule -> strings.instructionsMrzModule
            ScanBarcode -> strings.instructionsBarcode
            RotateDocument -> null
            RotateDocumentShort -> null
            KeepFacePhotoVisible -> strings.instructionsFacePhotoNotFullyVisible
            IncreaseLightingIntensity -> strings.instructionsIncreaseLight
            DecreaseLightingIntensity -> strings.instructionsDecreaseLight
            EliminateGlare -> strings.instructionsGlareDetected
            FilterSpecificMessage -> null
            ScanPassportDataPage -> strings.instructionsPassportDataPage
            PassportMoveToTop -> strings.instructionsPassportMoveToTopPage
            PassportMoveToRight -> strings.instructionsPassportMoveToRightPage
            PassportMoveToLeft -> strings.instructionsPassportMoveToLeftPage
            PassportMoveToBarcode -> strings.instructionsPassportMoveToBarcodePage
            PassportWrongPageTop -> strings.instructionsPassportWrongPageTop
            PassportWrongPageRight -> strings.instructionsPassportWrongPageRight
            PassportWrongPageLeft -> strings.instructionsPassportWrongPageLeft
            PassportWrongPageBarcode -> strings.instructionsPassportWrongPageBarcode
            PassportScanTopPage -> strings.instructionsPassportScanTopPage
            PassportScanRightPage -> strings.instructionsPassportScanRightPage
            PassportScanLeftPage -> strings.instructionsPassportScanLeftPage
            PassportScanBarcodePage -> strings.instructionsPassportScanBarcodePage
            BarcodeWrongSide -> strings.instructionsBarcodeWrongSide
        }
    }
}

enum class PassportPage {
    Data,
    Top,
    Left,
    Right,
    Barcode
}

enum class PassportType {
    Regular,
    BackSideBarcode
}

object ShowPassportMoveToTop : CardAnimationState {
    @Composable
    override fun Animate(screenDimensionMin: Dp, onAnimationCompleted: () -> Unit) {
        PassportPageAnimation(PassportPage.Top, screenDimensionMin, onAnimationCompleted)
    }
}

object ShowPassportMoveToRight : CardAnimationState {
    @Composable
    override fun Animate(screenDimensionMin: Dp, onAnimationCompleted: () -> Unit) {
        PassportPageAnimation(PassportPage.Right, screenDimensionMin, onAnimationCompleted)
    }
}

object ShowPassportMoveToLeft : CardAnimationState {
    @Composable
    override fun Animate(screenDimensionMin: Dp, onAnimationCompleted: () -> Unit) {
        PassportPageAnimation(PassportPage.Left, screenDimensionMin, onAnimationCompleted)
    }
}

object ShowPassportMoveToBarcode : CardAnimationState {
    @Composable
    override fun Animate(screenDimensionMin: Dp, onAnimationCompleted: () -> Unit) {
        EmptyAnimation(Duration.ZERO, onAnimationCompleted)
    }
}