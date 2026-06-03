/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.camera

import androidx.camera.core.ImageAnalysis

/**
 * An interface that represents the features of Microblink's analyzer.
 */
interface ImageAnalyzer : ImageAnalysis.Analyzer, AutoCloseable {
    fun cancel()
    fun pauseAnalysis()
    fun resumeAnalysis()
    fun restartAnalysis()
    fun timeoutAnalysis()
}