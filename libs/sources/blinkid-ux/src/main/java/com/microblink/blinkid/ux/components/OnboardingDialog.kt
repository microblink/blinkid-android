/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.theme.SdkTheme

@Composable
fun OnboardingDialog(
    onboardingDialogPage: HelpScreenPage,
    onDismissOnboardingDialog: () -> Unit
) {
    var orientation by remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }

    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    Dialog(
        onDismissRequest = onDismissOnboardingDialog,
        properties = DialogProperties(usePlatformDefaultWidth = orientation == Configuration.ORIENTATION_PORTRAIT)
    ) {
        val density = LocalResources.current.displayMetrics.density
        val maxHeight = (LocalWindowInfo.current.containerSize.height / density * 0.8).dp
        Card(
            modifier = Modifier
                .width(600.dp)
                .heightIn(max = maxHeight),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(28.dp)
        ) {
            when (orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Row(
                        Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        OnboardingDialogContent(
                            onDismissOnboardingDialog,
                            onboardingDialogPage
                        )
                    }
                }

                else -> {
                    Column(
                        Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OnboardingDialogContent(
                            onDismissOnboardingDialog,
                            onboardingDialogPage
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingDialogContent(
    onDismissOnboardingDialog: () -> Unit,
    onboardingScreenResources: HelpScreenPage
) {
    Column {
        Image(
            ContextCompat.getDrawable(
                LocalContext.current,
                onboardingScreenResources.pageImage
            )?.toBitmap()?.asImageBitmap()!!,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clearAndSetSemantics {},
        )
    }
    Spacer(Modifier.height(16.dp))
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .drawScrollbar(rememberScrollState())
                .weight(0.85f, fill = false)

        ) {
            Text(
                modifier = Modifier.semantics {
                    heading()
                },
                text = stringResource(onboardingScreenResources.pageTitle),
                style = SdkTheme.sdkTypography.onboardingTitle,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.height(16.dp))
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = stringResource(onboardingScreenResources.pageMessage),
                textAlign = TextAlign.Start,
                style = SdkTheme.sdkTypography.onboardingText
            )
        }
        Button(
            modifier = Modifier
                .weight(0.15f)
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                onDismissOnboardingDialog()
            }) {
            Text(
                text = stringResource(R.string.mb_blinkid_dialog_done_button),
                style = SdkTheme.sdkTypography.onboardingButton
            )
        }
    }
}