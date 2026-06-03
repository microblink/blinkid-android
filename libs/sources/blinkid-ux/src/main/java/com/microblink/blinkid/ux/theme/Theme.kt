package com.microblink.blinkid.ux.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

public object SdkTheme {
    val uiColors: UiColors
        @Composable
        get() = LocalBaseUiColors.current

    val sdkStrings: SdkStrings
        @Composable
        get() = LocalBaseSdkStrings.current

    val sdkTheme: ColorScheme
        @Composable
        get() = LocalTheme.current

    val sdkTypography: UiTypography
        @Composable
        get() = LocalTypography.current
}