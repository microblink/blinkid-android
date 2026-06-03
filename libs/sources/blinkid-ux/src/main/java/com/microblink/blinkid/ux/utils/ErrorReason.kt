package com.microblink.blinkid.ux.utils

import com.microblink.blinkid.ux.state.ErrorState

/**
 * Specifies the error reason.
 */
enum class ErrorReason {
    ErrorInvalidLicense,
    ErrorNetworkError,
    ErrorTimeoutExpired,
    ErrorDocumentClassFiltered,
    ErrorSettingsValidationFailed,
    ErrorGetResultFailed
}

fun ErrorReason.toErrorState(): ErrorState {
    return when(this) {
        ErrorReason.ErrorInvalidLicense -> ErrorState.ErrorInvalidLicense
        ErrorReason.ErrorNetworkError -> ErrorState.ErrorNetworkError
        ErrorReason.ErrorTimeoutExpired -> ErrorState.ErrorTimeoutExpired
        ErrorReason.ErrorDocumentClassFiltered -> ErrorState.ErrorDocumentClassFiltered
        ErrorReason.ErrorSettingsValidationFailed -> ErrorState.ErrorInvalidSettings
        ErrorReason.ErrorGetResultFailed -> ErrorState.ErrorGetResult
    }
}