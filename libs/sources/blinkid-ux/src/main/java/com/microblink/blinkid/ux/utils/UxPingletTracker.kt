package com.microblink.blinkid.ux.utils

import com.microblink.blinkid.core.ping.pinglets.CameraHardwareInfo
import com.microblink.blinkid.core.ping.pinglets.CameraInputInfo
import com.microblink.blinkid.core.ping.pinglets.CameraPermission
import com.microblink.blinkid.core.ping.pinglets.ScanningConditions
import com.microblink.blinkid.core.ping.pinglets.UxEvent
import com.microblink.blinkid.core.ping.util.PingletTracker
import com.microblink.blinkid.ux.camera.CameraDevicesDetails
import com.microblink.blinkid.ux.camera.CameraInputDetails
import com.microblink.blinkid.ux.camera.CameraLensFacing
import com.microblink.blinkid.ux.camera.FocusType

/**
 * Copyright (c) Microblink. All rights reserved. This code is provided for
 * use as-is and may not be copied, modified, or redistributed.
 */
/**
 * UX-specific Pinglet tracker for monitoring user experience events and camera-related activities.
 * This object extends [PingletTracker] to provide specialized tracking capabilities for user interface
 * events, camera hardware information, scanning conditions, and camera permissions within the UX layer.
 */
object UxPingletTracker : PingletTracker() {

    /**
     * Camera-related information tracking utilities.
     */
    object CameraInfo {
        /**
         * Tracks information about the camera input configuration used for document scanning.
         * @param cameraInputDetails The camera configuration details to track
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackCameraInputInfo(cameraInputDetails: CameraInputDetails, sessionNumber: Int) {
            addPingletToQueueIfManagerExists(
                pinglet = CameraInputInfo(
                    deviceId = cameraInputDetails.cameraId,
                    cameraFacing = when (cameraInputDetails.cameraFacing) {
                        CameraLensFacing.LensFacingFront -> CameraInputInfo.CameraFacing.FRONT
                        CameraLensFacing.LensFacingBack -> CameraInputInfo.CameraFacing.BACK
                    },
                    cameraFrameWidth = cameraInputDetails.cameraFrameWidth.toLong(),
                    cameraFrameHeight = cameraInputDetails.cameraFrameHeight.toLong(),
                    viewPortAspectRatio = cameraInputDetails.viewPortAspectRatio,
                    roiWidth = cameraInputDetails.roiWidth.toLong(),
                    roiHeight = cameraInputDetails.roiHeight.toLong()
                ),
                sessionNumber = sessionNumber
            )
        }

        /**
         * Tracks hardware capabilities of all available cameras on the device.
         * @param cameraDevicesDetails Complete camera hardware information for the device
         * @param sessionNumber The session identifier for associating this event with a spec
         */
        fun trackCameraHardwareInfo(
            cameraDevicesDetails: CameraDevicesDetails,
            sessionNumber: Int
        ) {
            addPingletToQueueIfManagerExists(
                pinglet = CameraHardwareInfo(
                    availableCameras = cameraDevicesDetails.devicesDetails.map { cameraDetails ->
                        CameraHardwareInfo.AvailableCamerasItem(
                            deviceId = cameraDetails.cameraId,
                            cameraFacing = when (cameraDetails.facing) {
                                CameraLensFacing.LensFacingFront -> CameraHardwareInfo.CameraFacing.FRONT
                                CameraLensFacing.LensFacingBack -> CameraHardwareInfo.CameraFacing.BACK
                            },
                            focus = when (cameraDetails.focusType) {
                                FocusType.Auto -> CameraHardwareInfo.Focus.AUTO
                                FocusType.Fixed -> CameraHardwareInfo.Focus.FIXED
                            },
                            availableResolutions = cameraDetails.resolutions.map { resolution ->
                                CameraHardwareInfo.AvailableResolutionsItem(
                                    width = resolution.width.toLong(),
                                    height = resolution.height.toLong()
                                )
                            }
                        )
                    },
                ),
                sessionNumber = sessionNumber
            )
        }
    }

    /**
     * User experience event tracking utilities.
     * Provides methods to track various user interface events, interactions, and system responses
     * that occur during the document scanning process.
     */
    object UxEvent {
        /**
         * Tracks simple UX events that don't require additional parameters.
         *
         * @param type The type of UX event that occurred
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackSimpleEvent(type: SimpleUxEventType, sessionNumber: Int) {
            trackUxPingletIfHaveSessionNumber(
                uxEventPinglet = UxEvent(
                    eventType = when (type) {
                        SimpleUxEventType.CameraStarted -> com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.CAMERASTARTED
                        SimpleUxEventType.CameraClosed -> com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.CAMERACLOSED
                        SimpleUxEventType.OnboardingInfoDisplayed -> com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.ONBOARDINGINFODISPLAYED
                        SimpleUxEventType.CloseButtonClicked -> com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.CLOSEBUTTONCLICKED
                        SimpleUxEventType.HelpTooltipDisplayed -> com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.HELPTOOLTIPDISPLAYED
                        SimpleUxEventType.HelpOpened -> com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.HELPOPENED
                        SimpleUxEventType.StepTimeout -> com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.STEPTIMEOUT
                        SimpleUxEventType.InactivityTimeout -> com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.INACTIVITYTIMEOUT
                        SimpleUxEventType.AppMovedToBackground -> com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.APPMOVEDTOBACKGROUND
                    }
                ),
                sessionNumber = sessionNumber
            )
        }

        /**
         * Tracks error message display events with specific error message types.
         *
         * @param errorMessageType The specific type of error message that was displayed
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackErrorMessageEvent(
            errorMessageType: com.microblink.blinkid.core.ping.pinglets.UxEvent.ErrorMessageType,
            sessionNumber: Int
        ) {
            trackUxPingletIfHaveSessionNumber(
                uxEventPinglet = UxEvent(
                    eventType = com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.ERRORMESSAGE,
                    errorMessageType = errorMessageType
                ),
                sessionNumber = sessionNumber
            )
        }

        /**
         * Tracks alert dialog display events with specific alert types.
         *
         * @param alertType The specific type of alert that was displayed
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackAlertDisplayedEvent(
            alertType: com.microblink.blinkid.core.ping.pinglets.UxEvent.AlertType,
            sessionNumber: Int
        ) {
            trackUxPingletIfHaveSessionNumber(
                uxEventPinglet = UxEvent(
                    eventType = com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.ALERTDISPLAYED,
                    alertType = alertType
                ),
                sessionNumber = sessionNumber
            )
        }

        /**
         * Tracks help screen closure events with information about how the help was closed.
         *
         * @param helpCloseType The manner in which the help screen was closed
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackHelpCloseEvent(
            helpCloseType: com.microblink.blinkid.core.ping.pinglets.UxEvent.HelpCloseType,
            sessionNumber: Int
        ) {
            trackUxPingletIfHaveSessionNumber(
                uxEventPinglet = UxEvent(
                    eventType = com.microblink.blinkid.core.ping.pinglets.UxEvent.EventType.HELPCLOSED,
                    helpCloseType = helpCloseType
                ),
                sessionNumber = sessionNumber
            )
        }

        /**
         * Simple UX event types that don't require additional parameters.
         */
        enum class SimpleUxEventType {
            CameraStarted,
            CameraClosed,
            OnboardingInfoDisplayed,
            CloseButtonClicked,
            HelpTooltipDisplayed,
            HelpOpened,
            StepTimeout,
            InactivityTimeout,
            AppMovedToBackground
        }

        /**
         * Internal helper method to track UX pinglets only when a valid session number is available.
         *
         * This method ensures that UX events are only tracked when they can be properly associated
         * with an active scanning session (session number > 0).
         *
         * @param uxEventPinglet The UX event pinglet to track
         * @param sessionNumber The session number to validate and use for tracking
         */
        private fun trackUxPingletIfHaveSessionNumber(
            uxEventPinglet: com.microblink.blinkid.core.ping.pinglets.UxEvent,
            sessionNumber: Int
        ) {
            if (sessionNumber > 0) {
                addPingletToQueueIfManagerExists(uxEventPinglet, sessionNumber)
            }
        }
    }

    /**
     * Scanning conditions tracking utilities.
     *
     * Provides methods to track environmental and device conditions that affect the scanning process,
     * such as device orientation, flashlight usage, and other scanning-related state changes.
     */
    object ScanningConditions {

        /**
         * Tracks flashlight (torch) state changes during scanning.
         *
         * @param torchOn `true` if the flashlight has been turned on, `false` if turned off
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackTorchStateUpdate(torchOn: Boolean, sessionNumber: Int) {
            if (sessionNumber > 0) {
                val scanningConditionsPinglet = ScanningConditions(
                    updateType = com.microblink.blinkid.core.ping.pinglets.ScanningConditions.UpdateType.FLASHLIGHTSTATE,
                    flashlightOn = torchOn,
                )
                addPingletToQueueIfManagerExists(scanningConditionsPinglet, sessionNumber)
            }
        }

        /**
         * Tracks device orientation changes during scanning.
         *
         * @param screenOrientation The new screen orientation
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackScreenOrientationChange(
            screenOrientation: ScreenOrientation,
            sessionNumber: Int
        ) {
            if (sessionNumber > 0) {
                val scanningConditionsPinglet = ScanningConditions(
                    updateType = com.microblink.blinkid.core.ping.pinglets.ScanningConditions.UpdateType.DEVICEORIENTATION,
                    deviceOrientation = when (screenOrientation) {
                        ScreenOrientation.LandscapeLeft ->
                            com.microblink.blinkid.core.ping.pinglets.ScanningConditions.DeviceOrientation.LANDSCAPELEFT

                        ScreenOrientation.LandscapeRight ->
                            com.microblink.blinkid.core.ping.pinglets.ScanningConditions.DeviceOrientation.LANDSCAPERIGHT

                        ScreenOrientation.ReversePortrait ->
                            com.microblink.blinkid.core.ping.pinglets.ScanningConditions.DeviceOrientation.PORTRAITUPSIDE

                        else ->
                            com.microblink.blinkid.core.ping.pinglets.ScanningConditions.DeviceOrientation.PORTRAIT
                    }
                )
                addPingletToQueueIfManagerExists(scanningConditionsPinglet, sessionNumber)
            }
        }
    }

    /**
     * Camera permission tracking utilities.
     *
     * Provides methods to track camera permission-related events, including permission checks,
     * requests, and user responses.
     */
    object CameraPermission {

        /**
         * Tracks when a camera permission check is performed.
         *
         * This method captures when the app checks whether camera permissions are currently granted,
         * typically before attempting to access the camera.
         *
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackCameraPermissionCheck(sessionNumber: Int) {
            addPingletToQueueIfManagerExists(
                pinglet = CameraPermission(
                    eventType = com.microblink.blinkid.core.ping.pinglets.CameraPermission.EventType.CAMERAPERMISSIONCHECK
                ),
                sessionNumber = sessionNumber
            )
        }

        /**
         * Tracks when a camera permission request is initiated.
         *
         * This method captures when the app requests camera permissions from the user..
         *
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackCameraPermissionRequest(sessionNumber: Int) {
            //if (sessionNumber > 0) {
            addPingletToQueueIfManagerExists(
                pinglet = CameraPermission(
                    eventType = com.microblink.blinkid.core.ping.pinglets.CameraPermission.EventType.CAMERAPERMISSIONREQUEST
                ),
                sessionNumber = sessionNumber
            )
            //}
        }

        /**
         * Tracks the user's response to a camera permission request.
         *
         * This method captures whether the user granted or denied camera permissions when prompted.
         *
         * @param cameraPermissionGranted `true` if the user granted camera permission, `false` if denied
         * @param sessionNumber The session identifier for associating this event with a specific scanning session
         */
        fun trackCameraPermissionUserResponse(
            cameraPermissionGranted: Boolean,
            sessionNumber: Int
        ) {
            addPingletToQueueIfManagerExists(
                pinglet = CameraPermission(
                    eventType = com.microblink.blinkid.core.ping.pinglets.CameraPermission.EventType.CAMERAPERMISSIONUSERRESPONSE,
                    cameraPermissionGranted = cameraPermissionGranted
                ),
                sessionNumber = sessionNumber
            )
        }
    }
}