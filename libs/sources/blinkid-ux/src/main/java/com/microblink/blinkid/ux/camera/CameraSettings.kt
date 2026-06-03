/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.camera

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Enum representing the camera resolution that is being used.
 */
enum class Resolution(val width: Int, val height: Int) {
    Resolution720p(1280, 720),
    Resolution1080p(1920, 1080),
    Resolution2160p(3840, 2160),
    Resolution4320p(7680, 4320);
}

/**
 * Enum representing whether the front or the back camera is being used.
 */
enum class CameraLensFacing {
    LensFacingBack,
    LensFacingFront
}

/**
 * Enum representing the desired aspect ratio for the camera preview.
 */
enum class DesiredAspectRatio {
    RATIO_16_9,
    RATIO_4_3
}

/**
 * Configuration settings for the camera used in the scanning process.
 *
 * This data class allows you to specify the preferred camera lens facing, the desired resolution for the camera,
 * and the desired aspect ratio for the camera preview.
 *
 * @property lensFacing The preferred [CameraLensFacing] for the camera.
 *                      Defaults to [CameraLensFacing.LensFacingBack].
 * @property desiredResolution The desired [Resolution] for the camera.
 *                            Defaults to [Resolution.Resolution2160p].
 * @property desiredAspectRatio The desired aspect ratio for the camera preview.
 *                           Defaults to [DesiredAspectRatio.RATIO_16_9].
 *
 */
@Parcelize
data class CameraSettings(
    val lensFacing: CameraLensFacing = CameraLensFacing.LensFacingBack,
    val desiredResolution: Resolution = Resolution.Resolution2160p,
    val desiredAspectRatio: DesiredAspectRatio = DesiredAspectRatio.RATIO_16_9
) : Parcelable