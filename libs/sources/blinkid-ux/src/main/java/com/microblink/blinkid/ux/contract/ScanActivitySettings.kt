/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.contract

import android.os.Parcelable
import com.microblink.blinkid.ux.camera.CameraSettings
import com.microblink.blinkid.ux.theme.SdkStrings
import com.microblink.blinkid.ux.utils.ParcelableUiTypography

interface ScanActivitySettings : Parcelable {
    val cameraSettings: CameraSettings
    // TODO simplify naming to colors, strings, typography ?
    val scanActivityUiColors: ScanActivityColors?
    val scanActivityUiStrings: SdkStrings
    val scanActivityTypography: ParcelableUiTypography
    val showOnboardingDialog: Boolean
    val showHelpButton: Boolean
    val enableEdgeToEdge: Boolean
    val deleteCachedAssetsAfterUse: Boolean
}