/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */

package com.microblink.blinkid.ux.camera

/**
 * Identifies which UX-layer timeout timer expired and triggered [ImageAnalyzer.timeoutAnalysis].
 */
enum class TimeoutCause {
    /**
     * The current scanning step timed out (no progress within the configured step timeout).
     */
    Step,

    /**
     * The scanning session timed out due to user inactivity (no UI state change within the
     * configured inactivity timeout).
     */
    Inactivity,
}
