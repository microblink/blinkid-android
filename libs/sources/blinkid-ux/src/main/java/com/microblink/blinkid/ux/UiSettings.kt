/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microblink.blinkid.ux.contract.ScanActivitySettings
import com.microblink.blinkid.ux.theme.DarkColorScheme
import com.microblink.blinkid.ux.theme.LightColorScheme
import com.microblink.blinkid.ux.theme.LocalBaseUiColors
import com.microblink.blinkid.ux.theme.LocalTheme
import com.microblink.blinkid.ux.theme.SdkStrings
import com.microblink.blinkid.ux.theme.UiColors
import com.microblink.blinkid.ux.theme.UiTypography
import com.microblink.blinkid.ux.utils.toUiTypography

const val DefaultShowOnboardingDialog = true
const val DefaultShowHelpButton = true

/**
 * Configuration settings for the scanning UI.
 *
 * Allows customization of various aspects of the UI used
 * during the scanning process, such as typography, color schemes,
 * reticle colors, string resources, and dialogs.
 *
 * @property typography             The [Typography] to be used in the scanning UI.
 *                                  Defaults to [com.microblink.blinkid.ux.theme.UiTypography] which uses default OS font.
 *                                  Defaults to `null`.
 * @property colorScheme            The [ColorScheme] to be used in the scanning UI.
 *                                  If left `null`, the theme will be applied based on the device settings (dark or light mode).
 *                                  Setting this value will overwrite the default behavior and the user
 *                                  will be responsible for handling theming based on device settings. Defaults to `null`.
 * @property uiColors               The [UiColors] to be used for graphical elements of scanning the UI.
 *                                  If left `null`, the theme will be applied based on the
 *                                  device settings (dark or light mode). Setting this value will overwrite the
 *                                  default behavior and the user will be responsible for handling theming based
 *                                  on device settings. Defaults to `null`.
 * @property sdkStrings             The [SdkStrings] containing text strings for the scanning UI, such as scanning
 *                                  instruction strings and accessibility strings. Defaults to [com.microblink.blinkid.ux.theme.SdkStrings.Default].
 * @property showOnboardingDialog   A boolean indicating whether to show an onboarding dialog at the beginning
 *                                  of the scanning session. Defaults to [DefaultShowOnboardingDialog].
 * @property showHelpButton         A boolean indicating whether to show a help button and enable help screens
 *                                  during the scanning session. Defaults to [DefaultShowHelpButton].
 *
 */
public data class UiSettings(
    val typography: UiTypography? = null,
    val colorScheme: ColorScheme? = null,
    val uiColors: UiColors? = null,
    val sdkStrings: SdkStrings? = null,
    val showOnboardingDialog: Boolean = DefaultShowOnboardingDialog,
    val showHelpButton: Boolean = DefaultShowHelpButton
)

@Composable
fun createUiSettings(scanActivitySettings: ScanActivitySettings): UiSettings {
    val primaryColor =
        if (scanActivitySettings.scanActivityUiColors?.primary != null) Color(
            scanActivitySettings.scanActivityUiColors!!.primary!!
        ) else if (isSystemInDarkTheme()) DarkColorScheme.primary else LightColorScheme.primary
    val backgroundColor =
        if (scanActivitySettings.scanActivityUiColors?.background != null) Color(
            scanActivitySettings.scanActivityUiColors!!.background!!
        ) else if (isSystemInDarkTheme()) DarkColorScheme.background else LightColorScheme.background
    val onBackgroundColor =
        if (scanActivitySettings.scanActivityUiColors?.onBackground != null) Color(
            scanActivitySettings.scanActivityUiColors!!.onBackground!!
        ) else if (isSystemInDarkTheme()) DarkColorScheme.onBackground else LightColorScheme.onBackground

    val colorScheme = LocalTheme.current.copy(
        primary = primaryColor,
        background = backgroundColor,
        onBackground = onBackgroundColor
    )

    val helpButtonColor =
        if (scanActivitySettings.scanActivityUiColors?.helpButton != null) Color(
            scanActivitySettings.scanActivityUiColors!!.helpButton!!
        ) else if (isSystemInDarkTheme()) UiColors.DefaultDark.helpButton else UiColors.Default.helpButton
    val helpButtonBackgroundColor =
        if (scanActivitySettings.scanActivityUiColors?.helpButtonBackground != null) Color(
            scanActivitySettings.scanActivityUiColors!!.helpButtonBackground!!
        ) else if (isSystemInDarkTheme()) UiColors.DefaultDark.helpButtonBackground else UiColors.Default.helpButtonBackground
    val helpTooltipTextColor =
        if (scanActivitySettings.scanActivityUiColors?.helpTooltipText != null) Color(
            scanActivitySettings.scanActivityUiColors!!.helpTooltipText!!
        ) else if (isSystemInDarkTheme()) UiColors.DefaultDark.helpTooltipText else UiColors.Default.helpTooltipText
    val helpTooltipBackgroundColor =
        if (scanActivitySettings.scanActivityUiColors?.helpTooltipBackground != null) Color(
            scanActivitySettings.scanActivityUiColors!!.helpTooltipBackground!!
        ) else if (isSystemInDarkTheme()) UiColors.DefaultDark.helpTooltipBackground else UiColors.Default.helpTooltipBackground

    val uiColors = LocalBaseUiColors.current.copy(
        helpButton = helpButtonColor,
        helpButtonBackground = helpButtonBackgroundColor,
        helpTooltipText = helpTooltipTextColor,
        helpTooltipBackground = helpTooltipBackgroundColor
    )

    val uiTypography = scanActivitySettings.scanActivityTypography.toUiTypography()

    return UiSettings(
        typography = uiTypography,
        colorScheme = colorScheme,
        uiColors = uiColors,
        sdkStrings = scanActivitySettings.scanActivityUiStrings,
        showOnboardingDialog = scanActivitySettings.showOnboardingDialog,
        showHelpButton = scanActivitySettings.showHelpButton
    )
}

