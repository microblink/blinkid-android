package com.microblink.blinkid.ux.utils

import android.content.Context
import android.view.OrientationEventListener
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Represents the possible orientations of a screen.
 */
enum class ScreenOrientation {
    /**
     * Indicates that the screen orientation is unknown.
     */
    Unknown,

    /**
     * Indicates that the screen is in portrait orientation.
     */
    Portrait,

    /**
     * Indicates that the screen is in landscape orientation with the right side of the device up.
     */
    LandscapeRight,

    /**
     * Indicates that the screen is in landscape orientation with the left side of the device up.
     */
    ReversePortrait,

    /**
     * Indicates that the screen is upside-down.
     */
    LandscapeLeft
}

@Composable
fun DeviceOrientationListener(
    applicationContext: Context,
    onOrientationChange: (ScreenOrientation) -> Unit
) {

    var currentOrientation: ScreenOrientation by remember { mutableStateOf(ScreenOrientation.Unknown) }

    DisposableEffect(Unit) {

        val orientationEventListener = object : OrientationEventListener(applicationContext) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) return
                val newOrientation = if (orientation >= 337 || orientation < 23) {
                    ScreenOrientation.Portrait
                } else if (orientation in 67..113) {
                    ScreenOrientation.LandscapeLeft
                } else if (orientation in 247..293) {
                    ScreenOrientation.LandscapeRight
                } else if (orientation in 157..203) {
                    ScreenOrientation.ReversePortrait
                } else {
                    currentOrientation
                }
                if (newOrientation != currentOrientation) {
                    onOrientationChange(newOrientation)
                    currentOrientation = newOrientation
                }
            }
        }

        orientationEventListener.enable()

        onDispose {
            orientationEventListener.disable()
        }

    }
}