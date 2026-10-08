package com.microblink.blinkid.sample.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microblink.blinkid.core.settings.usecase.DocumentScenario
import com.microblink.blinkid.core.settings.usecase.VideoCaptureEnvironment
import com.microblink.blinkid.core.settings.usecase.VideoQualityProfile
import com.microblink.blinkid.sample.R
import com.microblink.blinkid.sample.ui.components.BlinkIdTopAppBar
import com.microblink.blinkid.sample.ui.theme.Cobalt800
import com.microblink.blinkid.sample.utils.MainViewModel
import com.microblink.blinkid.sample.utils.SessionPreset

@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    onNavigateUp: () -> Unit
) {
    var showOtaUrlDialog by remember { mutableStateOf(false) }
    var scanningDialog by remember { mutableStateOf<ScanningDialog?>(null) }

    Scaffold(
        topBar = {
            BlinkIdTopAppBar(
                title = stringResource(R.string.settings),
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_navigate_up)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.settings_resources_ota),
                style = MaterialTheme.typography.titleSmall,
                color = Cobalt800,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            SettingsSwitchItem(
                title = stringResource(R.string.settings_download_resources),
                description = stringResource(R.string.settings_download_resources_desc),
                checked = viewModel.downloadResources,
                onCheckedChange = viewModel::updateDownloadResources
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsSwitchItem(
                title = stringResource(R.string.settings_update_ota),
                description = stringResource(R.string.settings_update_ota_desc),
                checked = viewModel.updateOtaResources,
                onCheckedChange = viewModel::updateOtaResourcesEnabled
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsSwitchItem(
                title = stringResource(R.string.settings_fail_if_ota_fails),
                description = stringResource(R.string.settings_fail_if_ota_fails_desc),
                checked = viewModel.failIfOtaFails,
                onCheckedChange = viewModel::updateFailIfOtaFails
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsTextItem(
                title = stringResource(R.string.settings_ota_service_url),
                description = stringResource(R.string.settings_ota_service_url_desc),
                value = viewModel.otaServiceUrl,
                onClick = { showOtaUrlDialog = true }
            )

            Text(
                text = stringResource(R.string.settings_scanning),
                style = MaterialTheme.typography.titleSmall,
                color = Cobalt800,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            SettingsTextItem(
                title = stringResource(R.string.settings_session_preset),
                description = stringResource(R.string.settings_session_preset_desc),
                value = viewModel.sessionPreset.displayName(),
                onClick = { scanningDialog = ScanningDialog.SessionPreset }
            )
            if (viewModel.sessionPreset == SessionPreset.DocumentVideo) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingsTextItem(
                    title = stringResource(R.string.settings_document_scenario),
                    description = stringResource(R.string.settings_document_scenario_desc),
                    value = viewModel.documentScenario.displayName(),
                    onClick = { scanningDialog = ScanningDialog.DocumentScenario }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingsTextItem(
                    title = stringResource(R.string.settings_video_quality),
                    description = stringResource(R.string.settings_video_quality_desc),
                    value = viewModel.videoQualityProfile.displayName(),
                    onClick = { scanningDialog = ScanningDialog.VideoQuality }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingsTextItem(
                    title = stringResource(R.string.settings_capture_environment),
                    description = stringResource(R.string.settings_capture_environment_desc),
                    value = viewModel.videoCaptureEnvironment.displayName(),
                    onClick = { scanningDialog = ScanningDialog.CaptureEnvironment }
                )
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            SettingsSwitchItem(
                title = stringResource(R.string.settings_passport_only),
                description = stringResource(R.string.settings_passport_only_desc),
                checked = viewModel.passportOnly,
                onCheckedChange = viewModel::updatePassportOnly
            )
        }
    }

    if (showOtaUrlDialog) {
        OtaServiceUrlDialog(
            initialValue = viewModel.otaServiceUrl,
            onDismiss = { showOtaUrlDialog = false },
            onConfirm = { url ->
                viewModel.updateOtaServiceUrl(url)
                showOtaUrlDialog = false
            }
        )
    }

    when (scanningDialog) {
        ScanningDialog.SessionPreset -> SingleChoiceDialog(
            title = stringResource(R.string.settings_session_preset),
            options = SessionPreset.entries,
            initialSelection = viewModel.sessionPreset,
            optionLabel = { it.displayName() },
            onDismiss = { scanningDialog = null },
            onConfirm = { preset ->
                viewModel.updateSessionPreset(preset)
                scanningDialog = null
            }
        )

        ScanningDialog.DocumentScenario -> SingleChoiceDialog(
            title = stringResource(R.string.settings_document_scenario),
            options = DocumentScenario.entries,
            initialSelection = viewModel.documentScenario,
            optionLabel = { it.displayName() },
            onDismiss = { scanningDialog = null },
            onConfirm = { scenario ->
                viewModel.updateDocumentScenario(scenario)
                scanningDialog = null
            }
        )

        ScanningDialog.VideoQuality -> SingleChoiceDialog(
            title = stringResource(R.string.settings_video_quality),
            options = VideoQualityProfile.entries,
            initialSelection = viewModel.videoQualityProfile,
            optionLabel = { it.displayName() },
            onDismiss = { scanningDialog = null },
            onConfirm = { profile ->
                viewModel.updateVideoQualityProfile(profile)
                scanningDialog = null
            }
        )

        ScanningDialog.CaptureEnvironment -> SingleChoiceDialog(
            title = stringResource(R.string.settings_capture_environment),
            options = VideoCaptureEnvironment.entries,
            initialSelection = viewModel.videoCaptureEnvironment,
            optionLabel = { it.displayName() },
            onDismiss = { scanningDialog = null },
            onConfirm = { environment ->
                viewModel.updateVideoCaptureEnvironment(environment)
                scanningDialog = null
            }
        )

        null -> Unit
    }
}

private enum class ScanningDialog {
    SessionPreset,
    DocumentScenario,
    VideoQuality,
    CaptureEnvironment
}

/**
 * Converts an enum entry name such as `ResultFieldsOnly` into a readable label (`Result fields only`).
 */
private fun Enum<*>.displayName(): String =
    name.replace(Regex("(?<=[a-z0-9])(?=[A-Z])"), " ")
        .lowercase()
        .replaceFirstChar { it.uppercase() }

@Composable
private fun SettingsSwitchItem(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Cobalt800
            )
        },
        supportingContent = {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

@Composable
private fun SettingsTextItem(
    title: String,
    description: String,
    value: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Cobalt800
            )
        },
        supportingContent = {
            Column {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}

@Composable
private fun OtaServiceUrlDialog(
    initialValue: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var textFieldValue by remember(initialValue) {
        mutableStateOf(
            TextFieldValue(
                text = initialValue,
                selection = TextRange(0, initialValue.length)
            )
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.settings_ota_service_url)) },
        text = {
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(textFieldValue.text) }) {
                Text(text = stringResource(R.string.settings_done))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.settings_cancel))
            }
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun <T> SingleChoiceDialog(
    title: String,
    options: List<T>,
    initialSelection: T,
    optionLabel: (T) -> String,
    onDismiss: () -> Unit,
    onConfirm: (T) -> Unit
) {
    var selected by remember(initialSelection) { mutableStateOf(initialSelection) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            Column(modifier = Modifier.selectableGroup()) {
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = option == selected,
                                onClick = { selected = option },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = option == selected, onClick = null)
                        Text(
                            text = optionLabel(option),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(selected) }) {
                Text(text = stringResource(R.string.settings_done))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.settings_cancel))
            }
        }
    )
}
