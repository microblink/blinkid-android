/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.camera

import android.content.Context
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.util.Size
import androidx.annotation.OptIn
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Utility object for collecting comprehensive camera hardware information from the device.
 *
 * This object provides functionality to query and analyze all available cameras on the device,
 * extracting detailed information about their capabilities, characteristics, and supported
 * configurations.
 */
object CameraHardwareInfoHelper {

    /**
     * Collects comprehensive camera hardware information for all available cameras.
     *
     * This method queries the device's camera system to gather detailed information
     * about all available cameras.
     *
     * @param context Application context required for camera system access
     * @return [CameraDevicesDetails] containing comprehensive information about all cameras
     */
    @OptIn(ExperimentalCamera2Interop::class)
    public suspend fun getCameraHardwareInfo(context: Context): CameraDevicesDetails {
        val cameraProvider = context.getCameraProvider()
        val cameras = cameraProvider.availableCameraInfos
        val cameraDevicesDetails = mutableListOf<CameraDetails>()

        cameras.forEach { cameraInfo ->
            val camera2Info = Camera2CameraInfo.from(cameraInfo)
            val characteristics = camera2Info.getCameraCharacteristic(
                CameraCharacteristics.LENS_FACING
            )

            cameraDevicesDetails.add(
                CameraDetails(
                    cameraId = camera2Info.cameraId,
                    facing = when (characteristics) {
                        CameraCharacteristics.LENS_FACING_FRONT -> CameraLensFacing.LensFacingFront
                        else -> CameraLensFacing.LensFacingBack
                    },
                    focusType = getFocusType(camera2Info),
                    resolutions = getSupportedResolutions(camera2Info)
                )
            )
        }
        return CameraDevicesDetails(
            numberOfDevices = cameraDevicesDetails.size,
            devicesDetails = cameraDevicesDetails
        )
    }

    /**
     * Retrieves supported resolutions for image analysis from a camera.
     *
     * This method queries the camera's stream configuration to get all supported
     * output sizes for the YUV_420_888 format, which is the standard format used
     * for image analysis in the scanning pipeline.
     *
     * @param camera2Info Camera2 info object for accessing camera characteristics
     * @return List of supported [Size] objects, or empty list if none available
     */
    @ExperimentalCamera2Interop
    private fun getSupportedResolutions(camera2Info: Camera2CameraInfo): List<Size> {
        val streamConfigMap = camera2Info.getCameraCharacteristic(
            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
        )

        // image format for image analysis stream is YUV_420_888
        return streamConfigMap?.getOutputSizes(ImageFormat.YUV_420_888)?.toList()
            ?: emptyList()
    }

    /**
     * Determines the focus type capabilities of a camera.
     *
     * @param camera2Info Camera2 info object for accessing camera characteristics
     * @return [FocusType.Fixed] for fixed focus cameras, [FocusType.AUTO] for autofocus cameras
     */
    @OptIn(ExperimentalCamera2Interop::class)
    private fun getFocusType(camera2Info: Camera2CameraInfo): FocusType {
        // Get minimum focus distance - 0 means fixed focus
        val minFocusDistance = camera2Info.getCameraCharacteristic(
            CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE
        ) ?: 0f

        return if (minFocusDistance == 0f) FocusType.Fixed else FocusType.Auto
    }

    /**
     * Asynchronously obtains the ProcessCameraProvider instance.
     *
     * @return [ProcessCameraProvider] instance for camera operations
     */
    private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(this).also { cameraProvider ->
                cameraProvider.addListener({
                    @Suppress("BlockingMethodInNonBlockingContext")
                    continuation.resume(cameraProvider.get())
                }, ContextCompat.getMainExecutor(this))
            }
        }
}

/**
 * Data class containing comprehensive information about all cameras on the device.
 *
 * @property numberOfDevices Total number of camera devices available on the device
 * @property devicesDetails List of detailed information for each individual camera
 */
data class CameraDevicesDetails (
    val numberOfDevices: Int,
    val devicesDetails: List<CameraDetails>
)

/**
 * Data class containing detailed information about a specific camera device.
 *
 * This class captures the key characteristics of an individual camera that are
 * relevant for scanning performance and analytics tracking.
 *
 * @property cameraId Camera ID of this camera device (e.g. "0", "1", "2").
 * @property facing The direction the camera faces (front or back)
 * @property focusType The focus capability of the camera (auto or fixed)
 * @property resolutions List of supported resolutions for image analysis
 */
data class CameraDetails(
    val cameraId: String,
    val facing: CameraLensFacing,
    val focusType: FocusType,
    val resolutions: List<Size>
)

/**
 * Camera focus type indicating autofocus capability.
 */
enum class FocusType {
    /** Camera supports automatic focus adjustment */
    Auto,
    /** Camera has fixed focus distance */
    Fixed
}