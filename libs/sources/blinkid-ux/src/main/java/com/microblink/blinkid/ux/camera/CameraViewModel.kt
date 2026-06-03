/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.camera

import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class CameraViewModel: ViewModel() {

    /**
     * Updating this value will turn the torch on or off.
     */
    val _torchOn: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val torchOn: StateFlow<Boolean> = _torchOn.asStateFlow()

    private val _isTorchSupported: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val isTorchSupported: StateFlow<Boolean> = _isTorchSupported.asStateFlow()

    fun updateTorchSupportState(isSupported: Boolean) {
        _isTorchSupported.value = isSupported
    }

    /**
     * Analyzes an image to produce a result.
     *
     * This method is called once for each image from the camera, and called at the
     * frame rate of the camera. Each analyze call is executed sequentially.
     *
     * @see [androidx.camera.core.ImageAnalysis.Analyzer.analyze]
     *
     * @param image input image to process.
     */
    abstract fun analyzeImage(image: ImageProxy)

}