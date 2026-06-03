package com.microblink.blinkid.ux.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.microblink.blinkid.ux.R

@Composable
fun DemoOverlay(modifier: Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.mb_blinkid_logo),
        contentDescription = "Microblink demo overlay"
    )
}