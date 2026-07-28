package com.microblink.blinkid.sample.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Main: Destination

    @Serializable
    data object Settings: Destination

    @Serializable
    data object DocumentScanning: Destination

    @Serializable
    data object BlinkIdResult: Destination
}