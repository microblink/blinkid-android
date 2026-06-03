/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.theme.SdkTheme

@Composable
fun ErrorDialog(
    @StringRes title: Int,
    @StringRes description: Int?,
    @StringRes buttonText: Int,
    onDismissErrorDialog: () -> Unit = {},
    onButtonClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismissErrorDialog) {
        Card(
            modifier = Modifier
                .semantics {
                    liveRegion = LiveRegionMode.Assertive
                }
                .padding(0.dp)
                .verticalScroll(
                    state = rememberScrollState()
                ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                Modifier.padding(vertical = 24.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .semantics {
                            heading()
                        },
                    text = stringResource(title),
                    style = SdkTheme.sdkTypography.errorDialogTitle,
                    color = MaterialTheme.colorScheme.onBackground
                )
                description?.let {
                    Spacer(Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(end = 12.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        text = stringResource(it),
                        textAlign = TextAlign.Start,
                        style = SdkTheme.sdkTypography.errorDialogText
                    )
                }
                Spacer(Modifier.height(20.dp))
                // TODO: add no ripple clickable to the entire material theme
                Button(
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = onButtonClick
                ) {
                    Text(
                        stringResource(buttonText),
                        style = SdkTheme.sdkTypography.errorDialogButton
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun TimeoutErrorDialogPreview() {
    ErrorDialog(
        title = R.string.mb_blinkid_recognition_timeout_dialog_title,
        description = R.string.mb_blinkid_recognition_timeout_dialog_message,
        buttonText = R.string.mb_blinkid_retry,
        onDismissErrorDialog = {},
        onButtonClick = {}
    )
}

@Preview
@Composable
private fun LicenseLockedErrorDialogPreview() {
    ErrorDialog(
        R.string.mb_blinkid_license_locked,
        null,
        R.string.mb_blinkid_close,
        onDismissErrorDialog = {},
        onButtonClick = { }
    )
}