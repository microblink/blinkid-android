package com.microblink.blinkid.ux.settings

import android.os.Parcelable
import com.microblink.blinkid.core.result.classinfo.DocumentClassInfo

/**
 * Document filter that is used to determine which documents will be processed.
 * If the document is rejected by this filter, its data cannot be returned as scanning result.
 */
interface ClassFilter : Parcelable {
    /**
     * Determines whether document should be processed or it is filtered out.
     *
     * @param documentClass [DocumentClassInfo] for document currently being evaluated. This object cannot
     * be used outside of this method, it will be invalidated as soon as this filter method
     * finishes. Please don't keep reference to classInfo parameter outside of this method.
     *
     * @return true if the document with the given [DocumentClassInfo] should be processed,
     * false if the document should be filtered out.
     */
    fun classAllowed(documentClass: DocumentClassInfo): Boolean
}