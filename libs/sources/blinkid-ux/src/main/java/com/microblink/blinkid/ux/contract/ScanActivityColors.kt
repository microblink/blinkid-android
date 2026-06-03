/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.contract

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScanActivityColors(
    @param:ColorInt val primary: Int?,
    @param:ColorInt val background: Int?,
    @param:ColorInt val onBackground: Int?,
    @param:ColorInt val helpButtonBackground: Int?,
    @param:ColorInt val helpButton: Int?,
    @param:ColorInt val helpTooltipBackground: Int?,
    @param:ColorInt val helpTooltipText: Int?,
) : Parcelable
