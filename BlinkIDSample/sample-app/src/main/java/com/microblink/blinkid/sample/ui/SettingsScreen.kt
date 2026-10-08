package com.microblink.blinkid.sample.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
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
import com.microblink.blinkid.core.result.FieldType
import com.microblink.blinkid.core.settings.RedactionMode
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
    var redactionDialog by remember { mutableStateOf<RedactionDialog?>(null) }

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

            Text(
                text = stringResource(R.string.settings_redaction),
                style = MaterialTheme.typography.titleSmall,
                color = Cobalt800,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            SettingsSwitchItem(
                title = stringResource(R.string.settings_custom_redaction),
                description = stringResource(R.string.settings_custom_redaction_desc),
                checked = viewModel.customRedactionEnabled,
                onCheckedChange = viewModel::updateCustomRedactionEnabled
            )
            if (viewModel.customRedactionEnabled) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingsTextItem(
                    title = stringResource(R.string.settings_redaction_mode),
                    description = stringResource(R.string.settings_redaction_mode_desc),
                    value = viewModel.redactionMode.displayName(),
                    onClick = { redactionDialog = RedactionDialog.Mode }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingsTextItem(
                    title = stringResource(R.string.settings_redacted_fields),
                    description = stringResource(R.string.settings_redacted_fields_desc),
                    value = if (viewModel.redactedFields.isEmpty()) {
                        stringResource(R.string.settings_redacted_fields_none)
                    } else {
                        stringResource(
                            R.string.settings_redacted_fields_selected,
                            viewModel.redactedFields.size
                        )
                    },
                    onClick = { redactionDialog = RedactionDialog.Fields }
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingsSwitchItem(
                    title = stringResource(R.string.settings_include_default_fields),
                    description = stringResource(R.string.settings_include_default_fields_desc),
                    checked = viewModel.includeDefaultRedactedFields,
                    onCheckedChange = viewModel::updateIncludeDefaultRedactedFields
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingsSwitchItem(
                    title = stringResource(R.string.settings_document_number_redaction),
                    description = stringResource(R.string.settings_document_number_redaction_desc),
                    checked = viewModel.documentNumberRedactionEnabled,
                    onCheckedChange = viewModel::updateDocumentNumberRedactionEnabled
                )
                if (viewModel.documentNumberRedactionEnabled) {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsTextItem(
                        title = stringResource(R.string.settings_document_number_prefix),
                        description = stringResource(R.string.settings_document_number_prefix_desc),
                        value = viewModel.documentNumberPrefixDigitsVisible.toString(),
                        onClick = { redactionDialog = RedactionDialog.PrefixDigits }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    SettingsTextItem(
                        title = stringResource(R.string.settings_document_number_suffix),
                        description = stringResource(R.string.settings_document_number_suffix_desc),
                        value = viewModel.documentNumberSuffixDigitsVisible.toString(),
                        onClick = { redactionDialog = RedactionDialog.SuffixDigits }
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingsSwitchItem(
                    title = stringResource(R.string.settings_redact_mrz),
                    description = stringResource(R.string.settings_redact_mrz_desc),
                    checked = viewModel.redactMrz,
                    onCheckedChange = viewModel::updateRedactMrz
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                SettingsSwitchItem(
                    title = stringResource(R.string.settings_redact_barcode),
                    description = stringResource(R.string.settings_redact_barcode_desc),
                    checked = viewModel.redactBarcode,
                    onCheckedChange = viewModel::updateRedactBarcode
                )
            }
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

    when (redactionDialog) {
        RedactionDialog.Mode -> SingleChoiceDialog(
            title = stringResource(R.string.settings_redaction_mode),
            options = RedactionMode.entries,
            initialSelection = viewModel.redactionMode,
            optionLabel = { it.displayName() },
            onDismiss = { redactionDialog = null },
            onConfirm = { mode ->
                viewModel.updateRedactionMode(mode)
                redactionDialog = null
            }
        )

        RedactionDialog.Fields -> MultiChoiceDialog(
            title = stringResource(R.string.settings_redacted_fields),
            options = FieldType.entries,
            initialSelection = viewModel.redactedFields,
            optionLabel = { it.displayName() },
            onDismiss = { redactionDialog = null },
            onConfirm = { fields ->
                viewModel.updateRedactedFields(fields)
                redactionDialog = null
            }
        )

        RedactionDialog.PrefixDigits -> DigitCountDialog(
            title = stringResource(R.string.settings_document_number_prefix),
            initialValue = viewModel.documentNumberPrefixDigitsVisible,
            onDismiss = { redactionDialog = null },
            onConfirm = { digits ->
                viewModel.updateDocumentNumberPrefixDigitsVisible(digits)
                redactionDialog = null
            }
        )

        RedactionDialog.SuffixDigits -> DigitCountDialog(
            title = stringResource(R.string.settings_document_number_suffix),
            initialValue = viewModel.documentNumberSuffixDigitsVisible,
            onDismiss = { redactionDialog = null },
            onConfirm = { digits ->
                viewModel.updateDocumentNumberSuffixDigitsVisible(digits)
                redactionDialog = null
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

private enum class RedactionDialog {
    Mode,
    Fields,
    PrefixDigits,
    SuffixDigits
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

@Composable
private fun <T> MultiChoiceDialog(
    title: String,
    options: List<T>,
    initialSelection: List<T>,
    optionLabel: (T) -> String,
    onDismiss: () -> Unit,
    onConfirm: (List<T>) -> Unit
) {
    var selected by remember(initialSelection) { mutableStateOf(initialSelection.toSet()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                items(options) { option ->
                    val checked = option in selected
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = checked,
                                onValueChange = {
                                    selected = if (checked) selected - option else selected + option
                                },
                                role = Role.Checkbox
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = checked, onCheckedChange = null)
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
            // Keep the selected options in their original order
            TextButton(onClick = { onConfirm(options.filter { it in selected }) }) {
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

@Composable
private fun DigitCountDialog(
    title: String,
    initialValue: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val initialText = initialValue.toString()
    var textFieldValue by remember(initialValue) {
        mutableStateOf(
            TextFieldValue(
                text = initialText,
                selection = TextRange(0, initialText.length)
            )
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = {
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = { value ->
                    if (value.text.all { it.isDigit() } && value.text.length <= 3) {
                        textFieldValue = value
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(textFieldValue.text.toIntOrNull() ?: 0) }) {
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
