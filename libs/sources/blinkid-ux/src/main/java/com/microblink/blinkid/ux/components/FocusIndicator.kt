/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microblink.blinkid.ux.theme.Black
import com.microblink.blinkid.ux.theme.White

/**
 * Width of a single ring of the double focus indicator. The complete
 * indicator is twice as thick, because it is built out of two rings, which
 * keeps it exactly as heavy as the single 2dp border it replaces.
 */
internal val focusIndicatorRingWidth = 1.dp

/**
 * Remembers an interaction source and reports whether the control it is
 * attached to currently holds focus.
 *
 * Pass the returned [MutableInteractionSource] to the control (for example to
 * `Modifier.clickable` or to a Material `Button`) so that focus is tracked
 * through the single focus target that the control already owns, instead of
 * adding another one with `Modifier.focusable`.
 */
@Composable
internal fun rememberFocusInteraction(): Pair<MutableInteractionSource, State<Boolean>> {
    val interactionSource = remember { MutableInteractionSource() }
    return interactionSource to interactionSource.collectIsFocusedAsState()
}

/**
 * Draws a double, black-and-white keyboard focus indicator around the
 * content when [focused] is true.
 *
 * The indicator consists of two concentric rings following [shape]: a white
 * [outerColor] ring on the outside and a black [innerColor] ring just inside
 * it. Because the two rings always contrast with each other, at least one of
 * them stays clearly visible no matter what is behind the control, which is
 * what keeps focus readable on top of the live camera preview and on both
 * light and dark surfaces.
 *
 * The rings are painted over the content and take up no layout space, so
 * showing and hiding them never shifts the surrounding UI. Apply this modifier
 * before any `Modifier.clip` in the chain so that the indicator is not clipped
 * away by the control's own shape.
 */
internal fun Modifier.doubleFocusBorder(
    focused: Boolean,
    shape: Shape,
    innerColor: Color = Black,
    outerColor: Color = White,
    ringWidth: Dp = focusIndicatorRingWidth,
): Modifier = drawWithContent {
    drawContent()
    if (!focused) return@drawWithContent

    val ringWidthPx = ringWidth.toPx()
    drawFocusRing(shape, outerColor, inset = ringWidthPx * 0.5f, ringWidth = ringWidthPx)
    drawFocusRing(shape, innerColor, inset = ringWidthPx * 1.5f, ringWidth = ringWidthPx)
}

/**
 * Strokes [shape] along the outline obtained by deflating the drawing bounds
 * by [inset] on every side, so that consecutive calls with a growing [inset]
 * produce concentric rings.
 */
private fun DrawScope.drawFocusRing(
    shape: Shape,
    color: Color,
    inset: Float,
    ringWidth: Float,
) {
    val ringSize = Size(size.width - 2f * inset, size.height - 2f * inset)
    if (ringSize.minDimension <= 0f) return

    val outline = shape.createOutline(ringSize, layoutDirection, this)
    translate(left = inset, top = inset) {
        drawOutline(outline = outline, color = color, style = Stroke(width = ringWidth))
    }
}
