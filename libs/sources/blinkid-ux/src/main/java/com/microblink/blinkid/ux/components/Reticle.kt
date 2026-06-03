/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.state.ReticleState
import com.microblink.blinkid.ux.theme.ErrorRed
import com.microblink.blinkid.ux.theme.Gray
import com.microblink.blinkid.ux.theme.SdkTheme
import com.microblink.blinkid.ux.theme.White

@Composable
fun Reticle(
    modifier: Modifier,
    reticleState: ReticleState,
    screenDimensionMin: Dp,
    onReticleSuccessAnimationCompleted: () -> Unit
) {

    val reticleRadius = screenDimensionMin / 4

    when (reticleState) {
        ReticleState.Hidden -> {

        }

        ReticleState.Sensing -> {
            ReticleBase(
                reticleRadius,
                Gray.copy(0.5f)
            )
            ReticleElementDashedCircle(reticleRadius, White.copy(0.75f))
        }

        ReticleState.IndefiniteProgress -> {
            ReticleBase(
                reticleRadius,
                Gray.copy(0.5f)
            )
            ReticleElementFullCircleAnimation(
                reticleRadius, White.copy(0.3f),
                White
            )
        }

        ReticleState.SuccessFirstSide -> {
            ReticleSuccess(
                modifier = Modifier.semantics {
                    liveRegion = LiveRegionMode.Assertive
                },
                reticleRadius = reticleRadius,
                onReticleSuccessAnimationCompleted = onReticleSuccessAnimationCompleted,
                isAfterFirstSide = true
            )
        }

        ReticleState.Success -> {
            ReticleSuccess(
                modifier = Modifier.semantics {
                    liveRegion = LiveRegionMode.Assertive
                },
                reticleRadius = reticleRadius,
                onReticleSuccessAnimationCompleted = onReticleSuccessAnimationCompleted,
                isAfterFirstSide = false
            )
        }

        ReticleState.Error -> {
            ReticleBase(
                reticleRadius,
                ErrorRed
            )
            ReticleElementFullCircle(
                reticleRadius,
                White.copy(0.75f)
            )
        }
    }
}

@Composable
internal fun ReticleBase(
    reticleRadius: Dp,
    color: Color
) {
    val centerColor = White
    Canvas(
        modifier = Modifier
            .height(reticleRadius)
            .width(reticleRadius)
    ) {
        drawCircle(
            color = color,
        )
        drawCircle(
            color = centerColor,
            radius = size.minDimension / 48
        )
    }
}

@Composable
internal fun ReticleElementFullCircle(
    reticleRadius: Dp,
    fullCircleColor: Color
) {
    Canvas(modifier = Modifier.size(reticleRadius)) {
        drawCircle(
            color = fullCircleColor,
            radius = size.minDimension / 4,
            style = Stroke(8f)
        )
    }
}

@Composable
internal fun ReticleElementFullCircleAnimation(
    reticleRadius: Dp,
    baseCircleColor: Color,
    animatedCircleColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween<Float>(
                durationMillis = 1000,
                easing = LinearEasing
            ),
        )
    )
    Canvas(modifier = Modifier.size(reticleRadius)) {
        drawCircle(
            color = baseCircleColor,
            radius = size.minDimension / 4,
            style = Stroke(8f)
        )
        rotate(rotation.value) {
            drawCircle(
                color = animatedCircleColor,
                radius = size.minDimension / 4,
                style = Stroke(
                    width = 8f,
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(
                            ((Math.PI * size.minDimension) / 12).toFloat(),
                            (((Math.PI * size.minDimension) / 12) * 5).toFloat()
                        )
                    )
                )
            )
        }
    }
}

@Composable
internal fun ReticleElementDashedCircle(
    reticleRadius: Dp,
    dashedCircleColor: Color
) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val infiniteTransition = rememberInfiniteTransition()
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween<Float>(
                durationMillis = reticleDashRotationAnimationDurationMs,
                delayMillis = reticleDashAppearDelayDurationMs,
                easing = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)
            )
        )
    )
    AnimatedVisibility(
        visible,
        enter = fadeIn(
            animationSpec = tween(
                reticleDashAppearAnimationDurationMs,
                reticleDashAppearDelayDurationMs
            )
        )
    ) {
        Canvas(
            modifier = Modifier.size(reticleRadius)
        ) {

            rotate(rotation.value) {
                drawCircle(
                    color = dashedCircleColor,
                    radius = size.minDimension / 4,
                    style = Stroke(
                        width = 8f,
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(
                                ((size.minDimension * Math.PI) / 16f).toFloat(),
                                ((size.minDimension * Math.PI) / 16f).toFloat()
                            ), 25f
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun ReticleSuccess(
    modifier: Modifier,
    reticleRadius: Dp,
    @DrawableRes successDrawable: Int = R.drawable.mb_blinkid_reticle_success,
    onReticleSuccessAnimationCompleted: () -> Unit,
    isAfterFirstSide: Boolean
) {
    // TODO: customizable drawable through DocVerTheme
    val successPainter = painterResource(successDrawable)
    val animationDone = remember { MutableTransitionState(false) }

    LaunchedEffect(animationDone.currentState) {
        if (animationDone.currentState) {
            onReticleSuccessAnimationCompleted()
        }
    }

    val stringFirstSideScanned =
        stringResource(SdkTheme.sdkStrings.accessibilityStrings.firstSideScanned)
    val stringDocumentScanned =
        stringResource(SdkTheme.sdkStrings.accessibilityStrings.scanCompleted)

    AnimatedVisibility(
        modifier = modifier,
        enter = scaleIn(tween(successAnimationDurationMs)),
        visibleState = animationDone.apply { targetState = true }
    ) {
        Image(
            contentDescription = null,
            painter = successPainter,
            modifier = modifier
                .size(reticleRadius)
                .clearAndSetSemantics {
                    this.contentDescription =
                        if (isAfterFirstSide) stringFirstSideScanned else stringDocumentScanned
                }
        )
    }
}