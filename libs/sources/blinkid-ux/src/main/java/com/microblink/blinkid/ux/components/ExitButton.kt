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
import com.microblink.blinkid.ux.theme.Gray
import com.microblink.blinkid.ux.theme.SdkTheme
import com.microblink.blinkid.ux.theme.White

@Composable
fun ExitButton(
    modifier: Modifier,
    onExit: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val exitButtonBackgroundColor = Gray.copy(alpha = 0.6f)
    val exitButtonColor = White
    val accessibilityFocusColor = SdkTheme.uiColors.helpButton

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
            .background(exitButtonBackgroundColor)
            .focusable()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .clickable {
                onExit()
            }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.mb_blinkid_icon_exit),
            contentDescription = stringResource(SdkTheme.sdkStrings.accessibilityStrings.exitScanning),
            colorFilter = ColorFilter.tint(exitButtonColor)
        )
    }
}