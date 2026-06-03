/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.camera.compose

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.net.toUri
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.theme.Black
import com.microblink.blinkid.ux.theme.Cobalt
import com.microblink.blinkid.ux.theme.White

/**
 * Composable function that displays a screen to inform the user that camera
 * permission is denied and provides options to request or enable it.
 *
 * This composable displays an informative screen with an image and text
 * indicating that camera permission is required. It provides a button to
 * request camera permission again or, if the user has permanently denied the
 * permission, shows an alert dialog with instructions to enable the
 * permission manually in the device settings.
 *
 * This screen is used as a part of the SDK and may be modified if necessary.
 *
 * @param requestCameraPermission A lambda function to be invoked when the user
 *                                clicks the button to request camera
 *                                permission.
 *
 */
@Composable
fun CameraPermissionDeniedScreen(requestCameraPermission: () -> Unit) {
    val shouldShowDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    var isFocusedSettingsButton by remember { mutableStateOf(false) }
    var isFocusedOkButton by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.mb_blinkid_camera_denied),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                modifier = Modifier
                    .padding(20.dp)
                    .semantics {
                        heading()
                    },
                text = stringResource(id = R.string.mb_blinkid_camera_permission_required),
                color = Black,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .then(
                        if (isFocusedSettingsButton)
                            Modifier.border(
                                width = 2.dp,
                                color = Cobalt,
                                shape = ButtonDefaults.shape
                            )
                        else Modifier
                    )
                    .padding(horizontal = 6.dp, vertical = 1.dp)
                    .focusable()
                    .onFocusChanged { focusState ->
                        isFocusedSettingsButton = focusState.isFocused
                    },
                colors = ButtonDefaults.buttonColors().copy(containerColor = Cobalt),
                onClick = {
                    val shouldShowRequestPermissionRationale = (context as? Activity)?.let {
                        shouldShowRequestPermissionRationale(it, Manifest.permission.CAMERA)
                    } ?: false
                    if (shouldShowRequestPermissionRationale) {
                        requestCameraPermission()
                    } else {
                        shouldShowDialog.value = true
                    }
                }) {
                Text(text = stringResource(id = R.string.mb_blinkid_enable_camera), color = White)
            }
        }

        if (shouldShowDialog.value) {
            AlertDialog(
                onDismissRequest = { shouldShowDialog.value = false },
                title = {
                    Text(
                        text = stringResource(R.string.mb_blinkid_warning),
                        color = Black
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.mb_blinkid_enable_permission_help),
                        color = Black
                    )
                },
                containerColor = White,
                confirmButton = {
                    Button(
                        modifier = Modifier
                            .then(
                                if (isFocusedOkButton)
                                    Modifier.border(
                                        width = 2.dp,
                                        color = Cobalt,
                                        shape = ButtonDefaults.shape
                                    )
                                else Modifier
                            )
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                            .focusable()
                            .onFocusChanged { focusState ->
                                isFocusedOkButton = focusState.isFocused
                            },
                        colors = ButtonDefaults.buttonColors().copy(containerColor = Cobalt),
                        onClick = {
                            shouldShowDialog.value = false
                            context.startActivity(
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    ("package:" + context.packageName).toUri()
                                )
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.mb_blinkid_ok),
                            color = White
                        )
                    }
                }
            )
        }

    }
}