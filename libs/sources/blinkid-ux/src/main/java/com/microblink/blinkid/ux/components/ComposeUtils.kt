/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import android.os.Build
import android.os.VibrationEffect
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kotlin.comparisons.then

internal val uiButtonRadiusDp = 36.dp

internal const val flipAnimationDurationMs = 2000
internal const val successAnimationDurationMs = 750

internal const val reticleDashRotationAnimationDurationMs = 550
internal const val reticleDashAppearAnimationDurationMs = 1000
internal const val reticleDashAppearDelayDurationMs = 550

internal const val needHelpTooltipDurationMs = 5000L
const val needHelpTooltipDefaultTimeToAppearMs = 10000L
const val uiCountingWindowDurationMs = 1500L

internal const val shortHapticFeedbackDurationMs = 100L
internal const val shortHapticFeedbackAmplitude = 50
internal const val longHapticFeedbackDurationMs = 300L
internal const val longHapticFeedbackAmplitude = 80

@RequiresApi(Build.VERSION_CODES.O)
fun shortHapticFeedback(): VibrationEffect {
    return VibrationEffect.createOneShot(
        shortHapticFeedbackDurationMs,
        shortHapticFeedbackAmplitude
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun longHapticFeedback(): VibrationEffect {
    return VibrationEffect.createOneShot(
        longHapticFeedbackDurationMs,
        longHapticFeedbackAmplitude
    )
}

fun Modifier.drawScrollbar(
    scrollState: ScrollState,
    color: Color = Color.Gray.copy(alpha = 0.5f),
    nestedScrollConnection: NestedScrollConnection? = null
): Modifier = this
    .then(nestedScrollConnection?.let { Modifier.nestedScroll(it) } ?: Modifier)
    .verticalScroll(scrollState)
    .drawWithContent {
        drawContent()

        val canScroll = scrollState.maxValue > 0
        if (canScroll) {
            val scrollbarWidth = 6.dp.toPx()
            val scrollbarHeight =
                (size.height / (scrollState.maxValue + size.height)) * size.height / 2
            val scrollProgress = scrollState.value.toFloat() / scrollState.maxValue
            val scrollbarOffsetY = scrollProgress * (size.height - scrollbarHeight)

            drawRoundRect(
                color = color,
                topLeft = Offset(size.width - scrollbarWidth, scrollbarOffsetY),
                size = Size(scrollbarWidth, scrollbarHeight),
                cornerRadius = CornerRadius(scrollbarWidth / 2, scrollbarWidth / 2)
            )
        }
    }