/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun HelpBox(
    modifier: Modifier,
    helpScreensButtonDisplayed: Boolean,
    helpTooltipDisplayed: Boolean,
    onDisplayHelpRequested: () -> Unit,
    onChangeHelpTooltipState: (Boolean) -> Unit,
    helpTooltipHideDelay: Duration = needHelpTooltipDefaultDurationMs.milliseconds
) {
    var _helpScreensButtonDisplayed by rememberSaveable {
        mutableStateOf(helpScreensButtonDisplayed)
    }

    LaunchedEffect(helpScreensButtonDisplayed) {
        _helpScreensButtonDisplayed = helpScreensButtonDisplayed
    }

    if (helpScreensButtonDisplayed) {
        NeedHelpTooltip(
            modifier,
            onChangeHelpTooltipState,
            onDisplayHelpRequested,
            helpTooltipDisplayed,
            helpTooltipHideDelay
        )
    }
}