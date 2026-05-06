package com.microblink.ux

import com.microblink.ux.state.UiScanningSide

interface ScanningUxEvent {
    /**
     * Request to scan a specific side of the document.
     *
     * @property side Specified the [UiScanningSide] that is to be scanned.
     */
    data class RequestSide(val side: UiScanningSide) : ScanningUxEvent

    /**
     * Camera image is too blurry for accurate document capture.
     */
    object BlurDetected : ScanningUxEvent

    /**
     * Light reflection is interfering with document capture.
     */
    object GlareDetected : ScanningUxEvent

    /**
     * No document has been located by the camera.
     */
    object DocumentNotFound : ScanningUxEvent

    /**
     * Indicates the wrong side of the document is being presented.
     */
    object ScanningWrongSide : ScanningUxEvent

    /**
     * The document has been located
     */
    object DocumentLocated: ScanningUxEvent

    /**
     * Document is too far from the camera.
     */
    object DocumentTooFar : ScanningUxEvent

    /**
     * Document is too close to the camera.
     */
    object DocumentTooClose : ScanningUxEvent

    /**
     * Part of document is occluded or partially outside of the camera.
     */
    object DocumentNotFullyVisible : ScanningUxEvent

    /**
     * Document is not parallel to the camera plane.
     */
    object DocumentTooTilted : ScanningUxEvent

    /**
     * Scanning has been successfully completed.
     */
    object ScanningDone: ScanningUxEvent

    /**
     * Face image was not found on the document.
     */
    object FaceImageNotFound: ScanningUxEvent

    /**
     * Document lighting is too bright.
     */
    object DocumentTooBright: ScanningUxEvent

    /**
     * Document lighting is too dark.
     */
    object DocumentTooDark: ScanningUxEvent

    /**
     * Document is not supported.
     */
    object UnsupportedDocument: ScanningUxEvent

}

interface ScanningUxEventHandler {
    fun onUxEvents(events: List<ScanningUxEvent>)
}