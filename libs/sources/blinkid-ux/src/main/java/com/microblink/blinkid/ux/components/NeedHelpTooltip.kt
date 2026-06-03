package com.microblink.blinkid.ux.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.theme.SdkTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NeedHelpTooltip(
    modifier: Modifier,
    onChangeHelpTooltipState: (Boolean) -> Unit,
    onDisplayHelpRequested: () -> Unit,
    activateHelpTooltip: Boolean
) {
    val scope = rememberCoroutineScope()

    val tooltipText = stringResource(R.string.mb_blinkid_need_help_tooltip)
    val helpTooltipBackgroundColor = SdkTheme.uiColors.helpTooltipBackground
    val helpTooltipTextColor = SdkTheme.uiColors.helpTooltipText
    val triangleWidth = 16.dp
    val triangleHeight = 8.dp

    var helpTooltipDisplayed by remember { mutableStateOf(false) }

    LaunchedEffect(activateHelpTooltip) {
        if (activateHelpTooltip) {
            helpTooltipDisplayed = true
            delay(needHelpTooltipDurationMs)
            scope.launch {
                helpTooltipDisplayed = false
            }
            onChangeHelpTooltipState(false)
        }
    }

    Column(modifier, horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = helpTooltipDisplayed,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Box(
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(8.dp), clip = false)
                    .background(helpTooltipBackgroundColor, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = tooltipText, color = helpTooltipTextColor)
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = helpTooltipDisplayed,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Canvas(
                    modifier = Modifier
                        .padding(bottom = 2.dp)
                        .width(triangleWidth)
                        .height(triangleHeight)
                ) {
                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(size.width / 2, size.height)
                        lineTo(size.width, 0f)
                        close()
                    }
                    drawPath(
                        path = path,
                        color = helpTooltipBackgroundColor
                    )
                }
            }
            HelpButton(
                onDisplayHelpRequested = onDisplayHelpRequested
            )
        }
    }
}