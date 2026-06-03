/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.microblink.blinkid.ux.UiSettings

/**
 * Theme used in the entire SDK.
 * Defines colors and strings that are used for all the elements.
 * The theme can be updated by defining [UiSettings] which customizes
 * all the visual elements.
 *
 * @property uiSettings Class that defines all of the visual elements of the SDK.
 * @property darkTheme Defines whether dark theme should be used or not. By default, the
 *                     current OS theme setting will be used.
 *
 */
@Composable
fun BlinkIdSdkTheme(
    uiSettings: UiSettings,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalBaseUiColors provides (uiSettings.uiColors
            ?: if (darkTheme) UiColors.DefaultDark else UiColors.Default),
        LocalBaseBlinkIdSdkStrings provides (uiSettings.sdkStrings as? BlinkIdSdkStrings
            ?: BlinkIdTheme.sdkStrings),
        LocalTheme provides if (darkTheme) DarkColorScheme else LightColorScheme,
        LocalTypography provides (uiSettings.typography ?: SdkTypography(null))
    ) {
        MaterialTheme(
            colorScheme = uiSettings.colorScheme ?: BlinkIdTheme.sdkTheme,
            content = content
        )
    }
}

internal object BlinkIdTheme {
    val uiColors: UiColors
        @Composable
        get() = LocalBaseUiColors.current

    val sdkStrings: BlinkIdSdkStrings
        @Composable
        get() = LocalBaseBlinkIdSdkStrings.current

    val sdkTheme: ColorScheme
        @Composable
        get() = LocalTheme.current

    val sdkTypography: UiTypography
        @Composable
        get() = LocalTypography.current
}