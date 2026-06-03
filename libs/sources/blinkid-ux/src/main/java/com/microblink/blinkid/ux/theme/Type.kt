/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Defines the typography used in the verification UI.
 *
 * This `Typography` object specifies the text styles for various UI elements
 * in the verification flow, such as headlines, labels, body text, and titles.
 * It provides a consistent look and feel throughout the UI.
 *
 * @param fontFamily Font family to be used in the SDK. By using this constructor,
 * all of the original sizes, weights, and line heights of the SDK remain, while the
 * font changes. To change other font parameters, create a new Typography object.
 */

@Immutable
data class UiTypography(
    val onboardingTitle: TextStyle,
    val onboardingText: TextStyle,
    val onboardingButton: TextStyle,
    val helpDialogTitle: TextStyle,
    val helpDialogText: TextStyle,
    val helpDialogButton: TextStyle,
    val errorDialogTitle: TextStyle,
    val errorDialogText: TextStyle,
    val errorDialogButton: TextStyle,
    val scanningInstructions: TextStyle,
    val helpTooltip: TextStyle,
    val loadingScreen: TextStyle,
) {
    companion object {
        fun Default(fontFamily: FontFamily?): UiTypography =
            UiTypography(
                onboardingTitle = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                ),
                onboardingText = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                onboardingButton = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                helpDialogTitle = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                ),
                helpDialogText = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                helpDialogButton = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                errorDialogTitle = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                ),
                errorDialogText = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                errorDialogButton = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                scanningInstructions = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                ),
                helpTooltip = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                ),
                loadingScreen = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                )
            )
    }
}

val SdkTypography: (fontFamily: FontFamily?) -> UiTypography = { fontFamily ->
    UiTypography.Default(fontFamily)
}

var LocalTypography = staticCompositionLocalOf {
    UiTypography.Default(null)
}