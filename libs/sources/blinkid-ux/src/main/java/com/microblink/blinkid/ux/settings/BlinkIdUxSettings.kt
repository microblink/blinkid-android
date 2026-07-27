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
 */
@Parcelize
data class BlinkIdUxSettings(
    val stepTimeoutDuration: Duration = 60000.milliseconds,
    val inactivityTimeoutDuration: Duration = 10000.milliseconds,
    val allowHapticFeedback: Boolean = true,
    val allowScanSound: Boolean = true,
    val classFilter: ClassFilter? = null,
    val redactionSettingsResolver: @RawValue RedactionSettingsResolver? = null
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
     */
    @JvmOverloads constructor(
        stepTimeoutDurationMs: Int,
        inactivityTimeoutDuration: Int,
        allowHapticFeedback: Boolean = true,
        allowScanSound: Boolean = true,
        classFilter: ClassFilter? = null,
        redactionSettingsResolver: RedactionSettingsResolver? = null
    ) : this(
        stepTimeoutDuration = stepTimeoutDurationMs.milliseconds,
        inactivityTimeoutDuration = inactivityTimeoutDuration.milliseconds,
        allowHapticFeedback = allowHapticFeedback,
        allowScanSound = allowScanSound,
        classFilter = classFilter,
        redactionSettingsResolver = redactionSettingsResolver
    )
}