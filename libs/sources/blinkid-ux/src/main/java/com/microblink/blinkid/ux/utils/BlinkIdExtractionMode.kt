package com.microblink.blinkid.ux.utils

import com.microblink.blinkid.core.session.BlinkIdSessionSettings
import com.microblink.blinkid.core.session.ScanningMode

/**
 * Represents the active extraction mode configuration.
 */
enum class BlinkIdExtractionMode {
    FullDocument,

    BarcodeOnly,

    DocumentWithBarcode,

    DocumentWithMrz
}

/**
 * Maps the current [BlinkIdSessionSettings] to the corresponding [BlinkIdExtractionMode]
 * based on which extraction modules are enabled.
 */
fun BlinkIdSessionSettings.toBlinkIdExtractionMode(): BlinkIdExtractionMode {
    val documentCaptureEnabled = scanningSettings.documentCaptureModule != null
    val barcodeEnabled = scanningSettings.barcodeModule != null
    val barcodePresenceMandatory = scanningSettings.barcodeModule?.presenceMandatory == true
    val mrzEnabled = scanningSettings.mrzModule != null
    val mrzPresenceMandatory = scanningSettings.mrzModule?.presenceMandatory == true
    val vizEnabled = scanningSettings.vizModule != null
    val isSingleSideScan = scanningMode == ScanningMode.Single


    return when {
        documentCaptureEnabled && barcodeEnabled && barcodePresenceMandatory && isSingleSideScan && !mrzEnabled && !vizEnabled ->
            BlinkIdExtractionMode.DocumentWithBarcode

        documentCaptureEnabled && mrzEnabled && mrzPresenceMandatory && isSingleSideScan && !vizEnabled && !barcodeEnabled ->
            BlinkIdExtractionMode.DocumentWithMrz

        !documentCaptureEnabled && barcodeEnabled && !mrzEnabled && !vizEnabled ->
            BlinkIdExtractionMode.BarcodeOnly

        else ->
            BlinkIdExtractionMode.FullDocument
    }
}
