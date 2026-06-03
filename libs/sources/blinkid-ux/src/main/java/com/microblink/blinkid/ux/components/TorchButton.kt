/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.state.MbTorchState
import com.microblink.blinkid.ux.theme.Gray
import com.microblink.blinkid.ux.theme.SdkTheme
import com.microblink.blinkid.ux.theme.White

@Composable
fun TorchButton(
    modifier: Modifier,
    torchState: MbTorchState,
    onTorchStateChange: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    if (torchState == MbTorchState.NotSupportedByCamera) {
        return
    }
    val torchButtonBackgroundColor =
        if (torchState == MbTorchState.On) White else Gray.copy(alpha = 0.6f)
    val torchButtonColor =
        if (torchState == MbTorchState.On) Gray.copy(alpha = 0.6f) else White
    val accessibilityFocusColor = SdkTheme.uiColors.helpButton
    val icon =
        if (torchState == MbTorchState.On) painterResource(R.drawable.mb_blinkid_icon_torch_on) else painterResource(
            R.drawable.mb_blinkid_icon_torch_off
        )

    Box(
        modifier = modifier
            .size(uiButtonRadiusDp)
            .clip(CircleShape)
            .then(
                if (isFocused)
                    Modifier.border(
                        width = 2.dp,
                        color = accessibilityFocusColor,
                        shape = CircleShape
                    )
                else Modifier
            )
            .background(torchButtonBackgroundColor)
            .focusable()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .clickable {
                onTorchStateChange()
            }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = icon,
            contentDescription = if (torchState == MbTorchState.On) stringResource(SdkTheme.sdkStrings.accessibilityStrings.turnFlashlightOff) else stringResource(
                SdkTheme.sdkStrings.accessibilityStrings.turnFlashlightOn
            ),
            colorFilter = ColorFilter.tint(torchButtonColor)
        )
    }
}