/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.scanning

import com.microblink.blinkid.core.image.InputImage
import com.microblink.blinkid.core.result.ImageAnalysisDetectionStatus
import com.microblink.blinkid.core.result.ImageAnalysisLightingStatus
import com.microblink.blinkid.core.result.ImageExtractionType
import com.microblink.blinkid.core.result.ProcessingStatus
import com.microblink.blinkid.core.result.ScanningSide
import com.microblink.blinkid.core.result.ScanningStatus
import com.microblink.blinkid.core.result.classinfo.CountryId
import com.microblink.blinkid.core.result.classinfo.DocumentTypeId
import com.microblink.blinkid.core.session.BlinkIdProcessResult
import com.microblink.blinkid.core.session.DetectionStatus
import com.microblink.blinkid.core.settings.ScanningSettings
import com.microblink.blinkid.ux.ScanningUxEvent
import com.microblink.blinkid.ux.state.PassportPage
import com.microblink.blinkid.ux.state.PassportType
import com.microblink.blinkid.ux.state.UiScanningSide
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

/**
 * Translates [BlinkIdProcessResult] and other scanning session information into a
 * list of [ScanningUxEvent] objects.
 *
 * This class is responsible for interpreting the results of the document
 * scanning process and generating user experience-related events that can be
 * used to update the UI or provide feedback to the user. It handles logic
 * related to document sides, timeouts, and various detection statuses.
 *
 */
class BlinkIdScanningUxTranslator : BlinkIdUxTranslator {

    private val barcodeTimeout = 3.seconds
    private var barcodeDispatched = false
    private var barcodeFailedTimestamp: Long? = null

    // Flag to avoid multiple consecutive emission of UnparsableBarcode that triggers resolveCurrentStep()
    private var unparsableBarcodeResolved = false

    private var passportType: PassportType? = null

    private var currentSide = UiScanningSide.First

    private val unsupportedDocumentTimeout = 1.5.seconds
    private var firstUnsupportedDocumentTimestamp: Long? = null

    /**
     * Translates the given [BlinkIdProcessResult] and [InputImage]
     * into a list of [ScanningUxEvent] objects.
     *
     * This function analyzes the current state of the scanning session and the
     * results of the last image processing step to determine which UX events
     * should be generated.
     *
     * @param processResult The [BlinkIdProcessResult] from the scanning session.
     * @param inputImage The [InputImage] used for the process. Can be `null`.
     * @param scanningSettings The [ScanningSettings] used to configure scanning behavior.
     * @param scanningStatus The current native scanning status for the session.
     * @return A list of [ScanningUxEvent] objects representing the user
     *         experience events that should be dispatched.
     */
    override suspend fun translate(
        processResult: BlinkIdProcessResult,
        inputImage: InputImage?,
        scanningSettings: ScanningSettings,
        scanningStatus: ScanningStatus
    ): List<ScanningUxEvent> {
        val events = mutableListOf<ScanningUxEvent>()

        val imageAnalysisResult = processResult.inputImageAnalysisResult

        if (scanningStatus == ScanningStatus.DocumentScanned) {
            events.add(ScanningUxEvent.ScanningDone)
            return events
        }

        imageAnalysisResult.documentClassInfo?.documentType?.id
            ?.takeIf { it == DocumentTypeId.Passport }
            ?.let {
                passportType = if (
                    imageAnalysisResult.documentClassInfo?.country?.id == CountryId.Usa ||
                    imageAnalysisResult.documentClassInfo?.country?.id == CountryId.India
                ) {
                    PassportType.BackSideBarcode
                } else {
                    PassportType.Regular
                }
            }

        if (currentSide == UiScanningSide.First) {
            // resolveCurrentStep() can advance native state to SideScanned before
            // a new frame reports AwaitingOtherSide.
            if (scanningStatus == ScanningStatus.SideScanned ||
                processResult.inputImageAnalysisResult.processingStatus == ProcessingStatus.AwaitingOtherSide
            ) {
                currentSide = UiScanningSide.Second
                if (passportType != null) {
                    events.add(
                        RequestPassportPage(
                            documentRotation = imageAnalysisResult.documentRotation,
                            isBarcodePageRequested = passportType == PassportType.BackSideBarcode
                        )
                    )
                } else {
                    events.add(ScanningUxEvent.RequestSide(UiScanningSide.Second))
                }
            }
        } else if (currentSide == UiScanningSide.Second) {
            if (processResult.inputImageAnalysisResult.scanningSide != ScanningSide.Second) {
                currentSide = UiScanningSide.First
            }
        }

        if (events.isNotEmpty()) return events

        if (imageAnalysisResult.processingStatus == ProcessingStatus.BarcodeRecognitionFailed && !barcodeDispatched) {
            barcodeDispatched = true
            events.add(ScanningUxEvent.RequestSide(UiScanningSide.Barcode))
            if (processResult.resultCompleteness.barcode?.parsingSupported == false && canResolveBarcode(
                    scanningSettings
                )
            ) {
                if (barcodeFailedTimestamp == null) {
                    barcodeFailedTimestamp = System.nanoTime()
                }
            }
        }
        if (shouldResolveUnparsableBarcode() && !unparsableBarcodeResolved) {
            unparsableBarcodeResolved = true
            events.add(ScanningUxEvent.UnparsableBarcode)
        }

        if (events.isNotEmpty()) return events

        if (imageAnalysisResult.documentLocation != null) {
            events.add(
                if (inputImage != null) {
                    BlinkIdDocumentLocatedLocation(
                        location = imageAnalysisResult.documentLocation!!,
                        inputImage = inputImage
                    )
                } else {
                    ScanningUxEvent.DocumentLocated
                }
            )
        } else {
            events.add(ScanningUxEvent.DocumentNotFound)
        }

        // below just one event can be generated, by following priorities
        var hasEvents = false

        val previousUnsupportedTimestamp = firstUnsupportedDocumentTimestamp
        firstUnsupportedDocumentTimestamp = null

        when (imageAnalysisResult.processingStatus) {
            ProcessingStatus.UnsupportedDocument -> {
                firstUnsupportedDocumentTimestamp =
                    previousUnsupportedTimestamp ?: System.nanoTime()
                if (shouldShowUnsupportedDocument()) {
                    events.add(ScanningUxEvent.UnsupportedDocument)
                }
                hasEvents = true
            }

            ProcessingStatus.AwaitingOtherSide -> {
                when (passportType) {
                    PassportType.Regular -> events.add(
                        RequestPassportPage(
                            documentRotation = imageAnalysisResult.documentRotation,
                            isBarcodePageRequested = false
                        )
                    )

                    PassportType.BackSideBarcode -> events.add(
                        RequestPassportPage(
                            documentRotation = imageAnalysisResult.documentRotation,
                            isBarcodePageRequested = true
                        )
                    )

                    null -> events.add(ScanningUxEvent.RequestSide(side = currentSide))
                }
                hasEvents = true
            }

            ProcessingStatus.ScanningWrongSide -> {
                val isScanningDataPage = currentSide == UiScanningSide.First
                events.add(
                    when (passportType) {
                        PassportType.Regular -> ScanningWrongPassportPage(
                            activePassportPage = if (isScanningDataPage) PassportPage.Data else null,
                            documentRotation = imageAnalysisResult.documentRotation
                        )

                        PassportType.BackSideBarcode -> ScanningWrongPassportPage(
                            activePassportPage = if (isScanningDataPage) PassportPage.Data else PassportPage.Barcode,
                            documentRotation = imageAnalysisResult.documentRotation
                        )

                        else -> ScanningUxEvent.ScanningWrongSide
                    }
                )
                hasEvents = true
            }

            ProcessingStatus.BarcodeDetectionFailed -> {
                events.add(ScanningUxEvent.BarcodeNotDetected)
                hasEvents = true
            }

            ProcessingStatus.ImageReturnFailed -> {
                if (processResult.inputImageAnalysisResult.imageExtractionFailures.contains(
                        ImageExtractionType.Face
                    )
                ) {
                    events.add(ScanningUxEvent.FaceImageNotFound)
                    hasEvents = true
                }

            }

            ProcessingStatus.MandatoryFieldMissing, ProcessingStatus.MrzParsingFailed, ProcessingStatus.InvalidCharactersFound -> {
                events.add(
                    ScanningUxEvent.DocumentNotFullyVisible
                )
                hasEvents = true
            }

            else -> {}
        }

        if (hasEvents) {
            events.add(DocumentImageAnalysisResult(imageAnalysisResult = imageAnalysisResult))
            return events
        }

        hasEvents = true

        when (imageAnalysisResult.documentDetectionStatus) {
            DetectionStatus.CameraTooFar -> events.add(ScanningUxEvent.DocumentTooFar)
            DetectionStatus.CameraTooClose,
            DetectionStatus.DocumentTooCloseToCameraEdge -> events.add(ScanningUxEvent.DocumentTooClose)

            DetectionStatus.DocumentPartiallyVisible -> events.add(ScanningUxEvent.DocumentNotFullyVisible)
            DetectionStatus.CameraAngleTooSteep -> events.add(ScanningUxEvent.DocumentTooTilted)
            else -> {
                hasEvents = false
            }
        }

        if (hasEvents) {
            events.add(DocumentImageAnalysisResult(imageAnalysisResult = imageAnalysisResult))
            return events
        }

        hasEvents = true

        if (scanningSettings.documentCaptureModule?.imageWithGlareRejected == true &&
            imageAnalysisResult.glareDetectionStatus == ImageAnalysisDetectionStatus.Detected
        ) {
            events.add(ScanningUxEvent.GlareDetected)
        } else if (scanningSettings.documentCaptureModule?.imageWithBlurRejected == true &&
            imageAnalysisResult.blurDetectionStatus == ImageAnalysisDetectionStatus.Detected
        ) {
            events.add(ScanningUxEvent.BlurDetected)
        } else if (scanningSettings.documentCaptureModule?.imageWithHandOcclusionRejected == true &&
            imageAnalysisResult.documentHandOcclusionStatus == ImageAnalysisDetectionStatus.Detected
        ) {
            events.add(ScanningUxEvent.DocumentNotFullyVisible)
        } else if (scanningSettings.documentCaptureModule?.imageWithPoorLightingRejected == true &&
            imageAnalysisResult.documentLightingStatus == ImageAnalysisLightingStatus.TooBright
        ) {
            events.add(ScanningUxEvent.DocumentTooBright)
        } else if (scanningSettings.documentCaptureModule?.imageWithPoorLightingRejected == true &&
            imageAnalysisResult.documentLightingStatus == ImageAnalysisLightingStatus.TooDark
        ) {
            events.add(ScanningUxEvent.DocumentTooDark)
        } else {
            hasEvents = false
        }

        if (hasEvents) {
            events.add(DocumentImageAnalysisResult(imageAnalysisResult = imageAnalysisResult))
            return events
        }

        events.add(ScanningUxEvent.DocumentNotFound)
        events.add(ScanningUxEvent.RequestSide(side = currentSide))
        events.add(DocumentImageAnalysisResult(imageAnalysisResult = imageAnalysisResult))
        return events
    }

    fun resetSession() {
        barcodeDispatched = false
        barcodeFailedTimestamp = null
        unparsableBarcodeResolved = false
        passportType = null
        firstUnsupportedDocumentTimestamp = null
        // TODO: Verify whether currentSide should reset to UiScanningSide.First on session restart.
    }

    private fun shouldShowUnsupportedDocument(): Boolean {
        return (System.nanoTime() - firstUnsupportedDocumentTimestamp!!).nanoseconds > unsupportedDocumentTimeout
    }

    private fun shouldResolveUnparsableBarcode(): Boolean {
        return barcodeFailedTimestamp != null &&
                (System.nanoTime() - barcodeFailedTimestamp!!).nanoseconds > barcodeTimeout
    }

    private fun canResolveBarcode(settings: ScanningSettings): Boolean {
        return if (settings.barcodeModule?.presenceMandatory == true) {
            false
        } else if (settings.documentCaptureModule == null &&
            settings.vizModule == null &&
            settings.mrzModule == null &&
            settings.barcodeModule != null
        ) {
            false
        } else true
    }
}