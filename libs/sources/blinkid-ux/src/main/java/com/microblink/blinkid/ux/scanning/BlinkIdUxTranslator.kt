/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.scanning

import com.microblink.blinkid.core.image.InputImage
import com.microblink.blinkid.core.result.ScanningStatus
import com.microblink.blinkid.core.session.BlinkIdProcessResult
import com.microblink.blinkid.core.settings.ScanningSettings
import com.microblink.blinkid.ux.ScanningUxEvent

/**
 * An interface that represents the translation process from [BlinkIdProcessResult] to [ScanningUxEvent].
 *
 * The translator converts scanning results into UX events based on the provided settings and the current native scanning status.
 */
interface BlinkIdUxTranslator {
    /**
     * Translates the latest process result into UX events.
     *
     * @param processResult The result from the last processed frame.
     * @param inputImage The input image used for processing. Can be `null`.
     * @param scanningSettings The scanning settings used by the session.
     * @param scanningStatus Current native session status. Use this for session-level transitions.
     */
    suspend fun translate(
        processResult: BlinkIdProcessResult,
        inputImage: InputImage?,
        scanningSettings: ScanningSettings,
        scanningStatus: ScanningStatus
    ): List<ScanningUxEvent>
}
