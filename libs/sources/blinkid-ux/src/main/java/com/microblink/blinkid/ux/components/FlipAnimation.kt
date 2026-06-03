/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun FlipAnimation(
    // TODO: customizable, based on document orientation change documentDrawable
    @DrawableRes firstSideDrawable:Int,
    @DrawableRes secondSideDrawable:Int,
    screenDimensionMin: Dp,
    onAnimationCompleted: () -> Unit
) {

    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        rotation.animateTo(
            targetValue = -180f,
            animationSpec = tween(durationMillis = flipAnimationDurationMs)
        ) {
            if (this.value == -180f) {
                onAnimationCompleted()
            }
        }
    }


    Box(
        modifier = Modifier
            .width((screenDimensionMin / 2))
            .height((screenDimensionMin / 4))
    ) {
        val documentFlipPainter =
            painterResource(if (rotation.value > -90f) firstSideDrawable else secondSideDrawable)
        Image(
            painter = documentFlipPainter,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .graphicsLayer {
                    rotationY = rotation.value
                    cameraDistance = 3f
                    transformOrigin = TransformOrigin(0.5f, 0.5f)
                    scaleX = if (rotation.value > -90f) 1f else -1f
                }
        )
    }
}
