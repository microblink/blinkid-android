/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.state


interface BaseUiState {
    val reticleState: ReticleState
    val processingState: ProcessingState
    val cardAnimationState: CardAnimationState
    val statusMessage: StatusMessage
    val currentSide: UiScanningSide
    val torchState: MbTorchState
    val cancelRequestState: CancelRequestState
    val helpButtonDisplayed: Boolean
    val helpDisplayed: Boolean
    val helpTooltipDisplayed: Boolean
    val onboardingDialogDisplayed: Boolean
    val errorState: ErrorState
    val hapticFeedbackState: HapticFeedbackState
    val scanSoundState: ScanSoundState
}
