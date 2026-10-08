package com.microblink.blinkid.ux.settings

import com.microblink.blinkid.core.result.classinfo.DocumentClassInfo
import com.microblink.blinkid.core.result.classinfo.DocumentTypeId
import kotlinx.parcelize.Parcelize

/**
 * [ClassFilter] used when [BlinkIdUxSettings.passportOnly] is enabled.
 *
 * Allows only passport document types. If [additionalFilter] is set, the document must also
 * be allowed by it.
 *
 * Documents without a known [DocumentTypeId] are not rejected, as their type can't be determined.
 */
@Parcelize
internal class PassportOnlyClassFilter(
    private val additionalFilter: ClassFilter? = null
) : ClassFilter {

    override fun classAllowed(documentClass: DocumentClassInfo): Boolean {
        val documentTypeId = documentClass.documentType?.id
        val isPassport = documentTypeId == null || documentTypeId in PassportDocumentTypes
        return isPassport && additionalFilter?.classAllowed(documentClass) != false
    }

    companion object {
        // Passport booklets only, PassportCard has no data page to open.
        val PassportDocumentTypes: Set<DocumentTypeId> = setOf(
            DocumentTypeId.Passport,
            DocumentTypeId.AlienPassport,
            DocumentTypeId.ConsularPassport,
            DocumentTypeId.MinorsPassport,
            DocumentTypeId.RefugeePassport,
            DocumentTypeId.EmergencyPassport,
            DocumentTypeId.TemporaryPassport,
        )
    }
}

/**
 * Returns the [ClassFilter] that should be applied during scanning, taking
 * [BlinkIdUxSettings.passportOnly] into account.
 */
internal fun BlinkIdUxSettings.resolveClassFilter(): ClassFilter? =
    if (passportOnly) PassportOnlyClassFilter(classFilter) else classFilter
