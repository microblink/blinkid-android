package com.microblink.blinkid.ux.settings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import com.microblink.blinkid.core.result.classinfo.CountryId
import com.microblink.blinkid.core.result.classinfo.RegionId
import com.microblink.blinkid.core.result.classinfo.DocumentTypeId
import com.microblink.blinkid.core.settings.RedactionSettingsResolver
import kotlinx.parcelize.RawValue
import com.microblink.blinkid.ux.components.needHelpTooltipDefaultDurationMs
import com.microblink.blinkid.ux.components.needHelpTooltipDefaultTimeToAppearMs

/**
 * Configuration settings for the scanning UX.
 *
 * @param stepTimeoutDuration Duration of the scanning session step before a timeout is triggered.
 * Resets on side changes, pauses when onboarding and help screen dialogs appear. If set to [Duration.ZERO], the scanning will not time out.
 * @param inactivityTimeoutDuration Duration of the current UI state in a scanning session before a timeout is triggered.
 * Resets every time the UI state changes (reticle type or message). If set to [Duration.ZERO], the scanning will not time out.
 * @param allowHapticFeedback Whether haptic feedback is allowed during the scanning process. Defaults to true.
 * @param allowScanSound Whether scan success sounds are allowed during the scanning process. Defaults to true.
 * @param classFilter Defines which specific document classes are allowed during scanning.
 * Each document class is defined by the trio of [CountryId], [RegionId], and [DocumentTypeId]. Defaults to null, meaning all classes are allowed.
 * @param redactionSettingsResolver Defines how to resolve `RedactionSettings` for a given document class.
 * @param passportOnly Enables the passport-only scanning flow, with passport specific instructions, onboarding and help screens.
 * Only passport documents are allowed. If [classFilter] is also set, documents must pass both filters. Defaults to false.
 * @param helpTooltipShowDelay Duration before the help tooltip is shown.
 * If less than or equal to [Duration.ZERO], the help tooltip won't be shown automatically.
 * @param helpTooltipHideDelay Duration before the help tooltip is hidden.
 * If less than or equal to [Duration.ZERO], the help tooltip won't be hidden automatically. Defaults to 5 seconds.
 */
@Parcelize
data class BlinkIdUxSettings(
    val stepTimeoutDuration: Duration = 60000.milliseconds,
    val inactivityTimeoutDuration: Duration = 10000.milliseconds,
    val allowHapticFeedback: Boolean = true,
    val allowScanSound: Boolean = true,
    val classFilter: ClassFilter? = null,
    val redactionSettingsResolver: @RawValue RedactionSettingsResolver? = null,
    val passportOnly: Boolean = false,
    val helpTooltipShowDelay: Duration = needHelpTooltipDefaultTimeToAppearMs.milliseconds,
    val helpTooltipHideDelay: Duration = needHelpTooltipDefaultDurationMs.milliseconds
) : Parcelable {
    /**
     * Constructor for easier Java implementation.
     *
     * This secondary constructor allows Java developers to create a [BlinkIdUxSettings]
     * instance by providing the `stepTimeoutDuration` as an `Int` in milliseconds.
     *
     * @param stepTimeoutDurationMs Duration of the scanning session step before a timeout is triggered in milliseconds.
     * Resets on side changes, pauses when onboarding and help screen dialogs appear. If set to 0, the scanning will not time out.
     * @param inactivityTimeoutDuration Duration of the current UI state in a scanning session before a timeout is triggered in milliseconds.
     * Resets every time the UI state changes (reticle type or message). If set to 0, the scanning will not time out.
     * @param allowHapticFeedback Whether haptic feedback is allowed during the scanning process. Defaults to true.
     * @param allowScanSound Whether scan success sounds are allowed during the scanning process. Defaults to true.
     * @param classFilter Defines which specific document classes are allowed during scanning.
     * Each document class is defined by the trio of [CountryId], [RegionId], and [DocumentTypeId]. Defaults to null, meaning all classes are allowed.
     * @param redactionSettingsResolver Defines how to resolve `RedactionSettings` for a given document class.
     * @param passportOnly Enables the passport-only scanning flow, with passport specific instructions, onboarding and help screens.
     * Only passport documents are allowed. If [classFilter] is also set, documents must pass both filters. Defaults to false.
     * @param helpTooltipShowDelayMs Duration before the help tooltip is shown in milliseconds.
     * If less than or equal to 0, the help tooltip won't be shown automatically.
     * @param helpTooltipHideDelayMs Duration before the help tooltip is hidden in milliseconds.
     * If less than or equal to 0, the help tooltip won't be hidden automatically. Defaults to 5000.
     */
    @JvmOverloads constructor(
        stepTimeoutDurationMs: Int,
        inactivityTimeoutDuration: Int,
        allowHapticFeedback: Boolean = true,
        allowScanSound: Boolean = true,
        classFilter: ClassFilter? = null,
        redactionSettingsResolver: RedactionSettingsResolver? = null,
        passportOnly: Boolean = false,
        helpTooltipShowDelayMs: Int = needHelpTooltipDefaultTimeToAppearMs.toInt(),
        helpTooltipHideDelayMs: Int = needHelpTooltipDefaultDurationMs.toInt()
    ) : this(
        stepTimeoutDuration = stepTimeoutDurationMs.milliseconds,
        inactivityTimeoutDuration = inactivityTimeoutDuration.milliseconds,
        allowHapticFeedback = allowHapticFeedback,
        allowScanSound = allowScanSound,
        classFilter = classFilter,
        redactionSettingsResolver = redactionSettingsResolver,
        passportOnly = passportOnly,
        helpTooltipShowDelay = helpTooltipShowDelayMs.milliseconds,
        helpTooltipHideDelay = helpTooltipHideDelayMs.milliseconds
    )
}