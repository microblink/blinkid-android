package com.microblink.blinkid.ux.scanning

import android.graphics.Bitmap
import com.microblink.blinkid.core.image.ImageRotation
import com.microblink.blinkid.core.session.BlinkIdProcessResult

/**
 * Delivered on every processed camera frame. Provides [com.microblink.blinkid.core.result.completeness.ResultCompleteness] for the current
 * frame and actions to influence the scanning flow.
 *
 * The entire FrameProcessResultHandle should only be used within the callback and must not be retained for usage outside of it.
 *
 * @property processResult Completeness of extraction for the current frame.
 * Use this to inspect mrz, viz, barcode, image extraction status etc. and decide whether to advance to the next step.
 * Do not use [com.microblink.blinkid.core.session.BlinkIdScanningResult] for this decision.
 * @property advanceToNextStep Advances to the next scanning step by calling resolveCurrentStep() internally.
 * Returns after the step resolution has completed. No-op if the session is already complete.
 * @property getLastFrame Returns the last processed camera frame as a [LastFrameResult], or `null` if the frame is no longer available.
 * This is a memory-intensive method, as it converts an image into [Bitmap] and creates additional frame copy.
 * It should only be called when really needed and not for every processed frame.
 * @property triggerStepTimeout Triggers a timeout for the current scanning step, causing the session to advance to the next step.
 * This can be used to implement custom timeout logic based on the [com.microblink.blinkid.core.session.BlinkIdProcessResult] or other factors.
 * Safe to call at any point during scanning. No-op if the session is already complete.
 */
class FrameProcessResultHandle(
    val processResult: BlinkIdProcessResult,
    val advanceToNextStep: () -> Unit,
    val getLastFrame: () -> LastFrameResult?,
    val triggerStepTimeout: () -> Unit
) {
    /**
     * Represents the last captured camera frame at the moment of delivery.
     * Delivered via `getLastFrame()` on [FrameProcessResultHandle].
     * Intended for image collection use cases where recognition yields no usable result.
     */
    data class LastFrameResult(
        val image: Bitmap,
        val imageRotation: ImageRotation
    )
}