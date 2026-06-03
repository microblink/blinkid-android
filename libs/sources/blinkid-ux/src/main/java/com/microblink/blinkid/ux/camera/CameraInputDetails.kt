/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.camera

/**
 * Class representing camera input information for image analysis.
 *
 * @property cameraId Camera ID of the camera used for obtaining images (e.g. "0", "1", "2").
 * @property cameraFacing Camera lens facing of the camera used for the obtaining images.
 * @property cameraFrameWidth Full width of the input image used for analysis,
 * as provided by the camera - matches the camera resolution selected for image analysis.
 * @property cameraFrameHeight Full height of the input image used for analysis,
 * as provided by the camera - matches the camera resolution selected for image analysis.
 * @property viewPortAspectRatio Aspect ratio of the preview region visible on the screen.
 * @property roiWidth Width of the Region of Interest (ROI), matching the visible area of the input
 * image in the preview that is being analysed.
 * @property roiHeight Height of the Region of Interest (ROI), matching the visible area of the
 * input image in the preview that is being analysed.
 */
data class CameraInputDetails(
    val cameraId: String,
    val cameraFacing: CameraLensFacing,
    val cameraFrameWidth: Int,
    val cameraFrameHeight: Int,
    val viewPortAspectRatio: Double,
    val roiWidth: Int,
    val roiHeight: Int
)