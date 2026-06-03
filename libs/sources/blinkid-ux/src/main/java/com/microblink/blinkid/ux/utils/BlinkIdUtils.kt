package com.microblink.blinkid.ux.utils

import com.microblink.blinkid.core.BlinkIdSdk
import com.microblink.blinkid.core.ping.config.PingSendTriggerPoint
import com.microblink.blinkid.core.result.DocumentRotation
import com.microblink.blinkid.core.utils.ping.sendPingletsIfAllowed
import com.microblink.blinkid.ux.state.PassportPage

internal const val passportMovePageAnimationDurationMs = 2000
internal const val pingletOrientationDelayMs = 1000L

fun getCorrectedDocumentRotation(
    documentRotation: DocumentRotation,
    screenOrientation: ScreenOrientation
): DocumentRotation {
    when (screenOrientation) {
        ScreenOrientation.Portrait -> {
            return when (documentRotation) {
                DocumentRotation.Zero -> DocumentRotation.Clockwise90
                DocumentRotation.Clockwise90 -> DocumentRotation.UpsideDown
                DocumentRotation.CounterClockwise90 -> DocumentRotation.Zero
                DocumentRotation.UpsideDown -> DocumentRotation.CounterClockwise90
                else -> DocumentRotation.Clockwise90
            }
        }

        ScreenOrientation.LandscapeRight -> {
            return documentRotation
        }

        ScreenOrientation.ReversePortrait -> {
            return when (documentRotation) {
                DocumentRotation.Zero -> DocumentRotation.CounterClockwise90
                DocumentRotation.Clockwise90 -> DocumentRotation.Zero
                DocumentRotation.CounterClockwise90 -> DocumentRotation.UpsideDown
                DocumentRotation.UpsideDown -> DocumentRotation.Clockwise90
                else -> DocumentRotation.CounterClockwise90
            }
        }

        ScreenOrientation.LandscapeLeft -> {
            return when (documentRotation) {
                DocumentRotation.Zero -> DocumentRotation.UpsideDown
                DocumentRotation.Clockwise90 -> DocumentRotation.CounterClockwise90
                DocumentRotation.CounterClockwise90 -> DocumentRotation.Clockwise90
                DocumentRotation.UpsideDown -> DocumentRotation.Zero
                else -> DocumentRotation.UpsideDown
            }
        }

        else -> {
            return when (documentRotation) {
                DocumentRotation.Zero -> DocumentRotation.Clockwise90
                DocumentRotation.Clockwise90 -> DocumentRotation.UpsideDown
                DocumentRotation.CounterClockwise90 -> DocumentRotation.Zero
                DocumentRotation.UpsideDown -> DocumentRotation.CounterClockwise90
                else -> DocumentRotation.Clockwise90
            }
        }
    }
}

fun getPassportPageFromRotation(
    documentRotation: DocumentRotation
): PassportPage {
    return when (documentRotation) {
        DocumentRotation.Zero -> PassportPage.Top
        DocumentRotation.Clockwise90 -> PassportPage.Right
        DocumentRotation.CounterClockwise90 -> PassportPage.Left
        DocumentRotation.UpsideDown -> PassportPage.Top
        DocumentRotation.NotAvailable -> PassportPage.Top
    }
}

fun onCameraPermissionCheck(sessionNumber: Int) {
    if (sessionNumber > 0) {
        UxPingletTracker.CameraPermission.trackCameraPermissionCheck(sessionNumber)
        BlinkIdSdk.sendPingletsIfAllowed(PingSendTriggerPoint.CameraPermissionCheck)
    }
}

fun onCameraPermissionRequest(sessionNumber: Int) {
    if (sessionNumber > 0) {
        UxPingletTracker.CameraPermission.trackCameraPermissionRequest(sessionNumber)
    }
}

fun onCameraPermissionUserResponse(sessionNumber: Int, cameraPermissionGranted: Boolean) {
    if (sessionNumber > 0) {
        UxPingletTracker.CameraPermission.trackCameraPermissionUserResponse(
            cameraPermissionGranted,
            sessionNumber
        )
    }
}

fun onCameraPreviewStarted(sessionNumber: Int) {
    UxPingletTracker.UxEvent.trackSimpleEvent(
        UxPingletTracker.UxEvent.SimpleUxEventType.CameraStarted,
        sessionNumber
    )
    BlinkIdSdk.sendPingletsIfAllowed(PingSendTriggerPoint.CameraStarted)
}

fun onCameraPreviewStopped(sessionNumber: Int) {
    UxPingletTracker.UxEvent.trackSimpleEvent(
        UxPingletTracker.UxEvent.SimpleUxEventType.CameraClosed,
        sessionNumber
    )
}

fun onCloseButtonClicked(sessionNumber: Int) {
    UxPingletTracker.UxEvent.trackSimpleEvent(
        UxPingletTracker.UxEvent.SimpleUxEventType.CloseButtonClicked,
        sessionNumber
    )
}

fun onAppMovedToBackground(sessionNumber: Int) {
    UxPingletTracker.UxEvent.trackSimpleEvent(
        UxPingletTracker.UxEvent.SimpleUxEventType.AppMovedToBackground,
        sessionNumber
    )
}