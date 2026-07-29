package com.microblink.blinkid.sample.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.microblink.blinkid.sample.R
import com.microblink.blinkid.sample.ui.components.BlinkIdTopAppBar
import com.microblink.blinkid.sample.ui.theme.Cobalt
import com.microblink.blinkid.sample.utils.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    launchDocumentScanning: () -> Unit,
    openSettings: () -> Unit
) {
    val mainState by viewModel.mainState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            BlinkIdTopAppBar(
                title = stringResource(R.string.app_name),
                actions = {
                    IconButton(onClick = openSettings) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.cd_settings)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = launchDocumentScanning
            ) {
                Text(text = stringResource(R.string.btn_launch))
            }
        }

        if (mainState.displayLoading) {
            LoadingDialog()
        }
    }
}

@Composable
private fun LoadingDialog() {
    Dialog(
        onDismissRequest = {},
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp),
                color = Cobalt
            )
        }
    }
}
