/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.theme.Gray
import com.microblink.blinkid.ux.theme.SdkTheme
import com.microblink.blinkid.ux.theme.White

@Composable
fun ExitButton(
    modifier: Modifier,
    onExit: () -> Unit
) {
    val (interactionSource, isFocused) = rememberFocusInteraction()
    val exitButtonBackgroundColor = Gray.copy(alpha = 0.6f)
    val exitButtonColor = White

    Box(
        modifier = modifier
            .size(uiButtonRadiusDp)
            .doubleFocusBorder(focused = isFocused.value, shape = CircleShape)
            .clip(CircleShape)
            .background(exitButtonBackgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) {
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
