package com.microblink.blinkid.sample.utils

import com.microblink.blinkid.core.result.classinfo.DocumentClassInfo
import com.microblink.blinkid.core.settings.RedactionSettings
import com.microblink.blinkid.core.settings.RedactionSettingsResolver
import kotlinx.parcelize.Parcelize

/**
 * Applies the same custom [RedactionSettings] to every scanned document.
 *
 * The resolver is called with the [DocumentClassInfo] of the scanned document right before the
 * result is finalized, so a real integration can return different settings per country, region
 * or document type, or `null` to keep the SDK's default redaction for that document.
 *
 * @property redactionSettings Custom redaction settings applied to every document.
 * @property includeDefaultFields Whether the fields the SDK redacts by default for the scanned
 * document are redacted in addition to [RedactionSettings.fields].
 */
@Parcelize
class SampleRedactionSettingsResolver(
    private val redactionSettings: RedactionSettings,
    private val includeDefaultFields: Boolean
) : RedactionSettingsResolver {
    override fun resolveRedactionSettings(classInfo: DocumentClassInfo): RedactionSettings {
        if (!includeDefaultFields) return redactionSettings
        val defaultFields = RedactionSettings.getDefaultRedactionSettings(classInfo).fields
        return redactionSettings.copy(
            fields = defaultFields.union(redactionSettings.fields).toList()
        )
    }
}
