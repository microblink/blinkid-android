/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux

import android.os.Build
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.microblink.blinkid.ux.components.DemoOverlay
import com.microblink.blinkid.ux.components.ExitButton
import com.microblink.blinkid.ux.components.HelpBox
import com.microblink.blinkid.ux.components.HelpScreens
import com.microblink.blinkid.ux.components.MessageContainer
import com.microblink.blinkid.ux.components.OnboardingDialog
import com.microblink.blinkid.ux.components.ProductionOverlay
import com.microblink.blinkid.ux.components.Reticle
import com.microblink.blinkid.ux.components.TorchButton
import com.microblink.blinkid.ux.components.longHapticFeedback
import com.microblink.blinkid.ux.components.longHapticFeedbackDurationMs
import com.microblink.blinkid.ux.components.shortHapticFeedback
import com.microblink.blinkid.ux.components.shortHapticFeedbackDurationMs
import com.microblink.blinkid.ux.state.BaseUiState
import com.microblink.blinkid.ux.state.CardAnimationState
import com.microblink.blinkid.ux.state.CommonStatusMessage
import com.microblink.blinkid.ux.state.ErrorState
import com.microblink.blinkid.ux.state.HapticFeedbackState
import com.microblink.blinkid.ux.state.ProcessingState
import com.microblink.blinkid.ux.state.ReticleState
import com.microblink.blinkid.ux.state.StatusMessage

/**
 * Composable function that provides the user interface for the scanning screen,
 * including the reticle, instruction messages, buttons, and dialogs.
 *
 * This function is responsible for rendering all the UI elements visible
 * during the document scanning process, except for the camera preview itself.
 * It handles displaying the reticle, instruction messages, exit and torch
 * buttons, help components, and various dialogs based on the provided UI state
 * and settings.
 *
 * @param modifier The [Modifier] to be applied to the outermost container of the UI.
 * @param uiState The [BaseUiState] containing the current state of the UI.
 * @param onExitScanning A callback function invoked when the user wants to
 *                       exit the scanning process.
 * @param uiSettings The [UiSettings] used to configure the UI.
 * @param showProductionOverlay A [Boolean] defining whether a `Microblink` logo overlay will be shown during scanning.
 *                              The setting value is defined by license and shouldn't be modified
 * @param showDemoOverlay A [Boolean] defining whether a `Powered by Microblink` text overlay will be shown during scanning.
 *  *                     The setting value is defined by license and shouldn't be modified
 * @param onTorchStateChange A callback function invoked when the user wants to
 *                           change the torch state.
 * @param onFlipDocumentAnimationCompleted A callback function invoked when the
 *                                         flip document animation is completed.
 * @param onReticleSuccessAnimationCompleted A callback function invoked when
 *                                           the reticle success animation is
 *                                           completed.
 * @param onHapticFeedbackCompleted A callback function invoked when the haptic feedback is completed.
 * @param onChangeOnboardingDialogVisibility A callback function invoked when
 *                                           the visibility of the onboarding
 *                                           dialog should change.
 * @param onChangeHelpScreensVisibility A callback function invoked when the
 *                                      visibility of the help screens should
 *                                      change.
 * @param onChangeHelpTooltipVisibility A callback function invoked when the
 *                                       visibility of the help tooltip should
 *                                       change.
 * @param onRetry A callback function invoked when retry button is pressed (e.g on timeout or
 * document class filtered dialog)
 * @param onDoneError A callback function invoked when the unrecoverable error occurs and cancel
 * button is pressed on error dialog.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanningUx(
    modifier: Modifier,
    uiState: BaseUiState,
    onExitScanning: () -> Unit,
    uiSettings: UiSettings,
    helpScreens: HelpScreens,
    errorStateDialogs: Map<ErrorState, @Composable () -> Unit>,
    allowHapticFeedback: Boolean,
    showProductionOverlay: Boolean,
    showDemoOverlay: Boolean,
    onTorchStateChange: () -> Unit,
    onFlipDocumentAnimationCompleted: () -> Unit,
    onReticleSuccessAnimationCompleted: () -> Unit,
    onHapticFeedbackCompleted: () -> Unit,
    onChangeOnboardingDialogVisibility: (Boolean) -> Unit,
    onHelpScreensDisplayRequested: () -> Unit,
    onHelpScreensCloseRequested: (allPagesDisplayed: Boolean) -> Unit,
    onChangeHelpTooltipVisibility: (Boolean) -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }) {
        ScanningScreenCentralElements(
            modifier = modifier,
            reticleState = uiState.processingState,
            instructionMessage = uiState.statusMessage,
            cardAnimationState = uiState.cardAnimationState,
            hapticFeedbackState = uiState.hapticFeedbackState,
            allowHapticFeedback = allowHapticFeedback,
            showDemoOverlay = showDemoOverlay,
            onFlipDocumentAnimationCompleted = onFlipDocumentAnimationCompleted,
            onReticleSuccessAnimationCompleted = onReticleSuccessAnimationCompleted,
            onHapticFeedbackCompleted = onHapticFeedbackCompleted
        )
        Box(
            modifier
                .fillMaxSize()
                .padding(
                    16.dp
                )
        ) {
            ExitButton(
                Modifier
                    .align(Alignment.TopStart)
                    .semantics { traversalIndex = 2f },
                onExitScanning
            )
            TorchButton(
                Modifier
                    .align(Alignment.TopEnd)
                    .semantics { traversalIndex = 2f },
                uiState.torchState,
                onTorchStateChange
            )
            if (uiSettings.showHelpButton) {
                HelpBox(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .semantics { traversalIndex = 2f },
                    uiState.helpButtonDisplayed,
                    uiState.helpTooltipDisplayed,
                    onHelpScreensDisplayRequested,
                    onChangeHelpTooltipVisibility
                )
            }
            if (showProductionOverlay) ProductionOverlay(
                Modifier
                    .align(Alignment.BottomCenter)
                    .clearAndSetSemantics {})
        }

        if (uiSettings.showOnboardingDialog && uiState.onboardingDialogDisplayed) {
            OnboardingDialog(helpScreens.onboardingDialogPage) {
                onChangeOnboardingDialogVisibility(
                    false
                )
            }
        }
        if (uiSettings.showHelpButton && uiState.helpDisplayed) {
            HelpScreens(helpScreens.helpDialogPages, onHelpScreensCloseRequested)
        }

        errorStateDialogs[uiState.errorState]?.invoke()
    }
}

@Composable
internal fun ScanningScreenCentralElements(
    modifier: Modifier = Modifier,
    reticleState: ProcessingState,
    instructionMessage: StatusMessage,
    cardAnimationState: CardAnimationState,
    hapticFeedbackState: HapticFeedbackState,
    allowHapticFeedback: Boolean,
    showDemoOverlay: Boolean,
    onFlipDocumentAnimationCompleted: () -> Unit,
    onReticleSuccessAnimationCompleted: () -> Unit,
    onHapticFeedbackCompleted: () -> Unit
) {

    var _reticleState by remember { mutableStateOf(ReticleState.Sensing) }
    var _instructionMessage: StatusMessage by remember { mutableStateOf(CommonStatusMessage.Empty) }
    var _cardAnimationState by remember { mutableStateOf(CardAnimationState.Hidden) }
    var showInstructionDialog by remember { mutableStateOf(true) }
    var showAnimation by remember { mutableStateOf(false) }
    var lastHapticFeedbackTime by remember { mutableLongStateOf(0L) }

    val context = LocalContext.current

    LaunchedEffect(hapticFeedbackState) {
        if (allowHapticFeedback) {
            val now = System.currentTimeMillis()
            if (hapticFeedbackState != HapticFeedbackState.VibrationOneTimeShort || now - lastHapticFeedbackTime > 1000) {
                val vibrator = context.getSystemService(Vibrator::class.java)
                when (hapticFeedbackState) {
                    HapticFeedbackState.VibrationOneTimeShort -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator?.vibrate(shortHapticFeedback())
                        } else {
                            @Suppress("DEPRECATION")
                            vibrator?.vibrate(shortHapticFeedbackDurationMs)
                        }
                        lastHapticFeedbackTime = now
                    }

                    HapticFeedbackState.VibrationOneTimeLong -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator?.vibrate(longHapticFeedback())
                        } else {
                            @Suppress("DEPRECATION")
                            vibrator?.vibrate(longHapticFeedbackDurationMs)
                        }
                        lastHapticFeedbackTime = now
                    }

                    else -> Unit
                }
            }
            onHapticFeedbackCompleted()
        }
    }

    LaunchedEffect(reticleState) {
        _reticleState = reticleState.reticleState
    }

    LaunchedEffect(instructionMessage) {
        _instructionMessage = instructionMessage
        showInstructionDialog = instructionMessage != CommonStatusMessage.Empty
    }

    LaunchedEffect(cardAnimationState) {
        _cardAnimationState = cardAnimationState
        showAnimation = _cardAnimationState != CardAnimationState.Hidden
    }

    Column(modifier.fillMaxSize()) {

        val density = LocalResources.current.displayMetrics.density
        val screenHeight = LocalWindowInfo.current.containerSize.height / density
        val screenWidth = LocalWindowInfo.current.containerSize.width / density
        val screenDimensionMinDp =
            if (screenWidth < screenHeight) screenWidth.dp else screenHeight.dp
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.55f)
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            if (showDemoOverlay) {
                Row(
                    Modifier
                        .weight(0.25f)
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Bottom
                ) {
                    DemoOverlay(
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .clearAndSetSemantics {}
                    )
                }
            }
            Box(
                Modifier
                    .weight(0.30f)
                    .fillMaxWidth(), contentAlignment = Alignment.BottomCenter
            ) {
                if (showAnimation) {
                    _cardAnimationState.Animate(
                        screenDimensionMinDp,
                        onFlipDocumentAnimationCompleted
                    )
                } else {
                    Reticle(
                        modifier = Modifier.semantics {
                            this.traversalIndex = 0f
                        },
                        _reticleState,
                        screenDimensionMinDp,
                        onReticleSuccessAnimationCompleted
                    )
                }
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(0.45f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                if (showInstructionDialog) {
                    _instructionMessage.statusMessageToStringRes()?.let {
                        MessageContainer(
                            modifier = Modifier.semantics {
                                this.traversalIndex = 1f
                                liveRegion = LiveRegionMode.Polite
                            },
                            it, Gray.copy(0.9f)
                        )
                    }
                }
            }
        }
    }
}