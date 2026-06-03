/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.contract

/**
 * @property Scanned Scanning process is successfully done.
 * @property Canceled Scanning process has been canceled by the user, or because of any other unexpected error.
 * @property ErrorSdkInit Scanning process has been canceled because of the license check error. This happens
 *        if you use license which is online activated, and activation fails.
 *
 */
public enum class ScanActivityResultStatus {
    Scanned,
    Canceled,
    ErrorSdkInit
}

enum class CancelReason {
    UserRequested,
    ErrorSdkInit
}
