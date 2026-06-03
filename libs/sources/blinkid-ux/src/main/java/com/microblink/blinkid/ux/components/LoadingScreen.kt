package com.microblink.blinkid.ux.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.microblink.blinkid.ux.R
import com.microblink.blinkid.ux.theme.DarkColorScheme
import com.microblink.blinkid.ux.theme.LightColorScheme
import com.microblink.blinkid.ux.theme.SdkTheme

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(80.dp),
            strokeWidth = 7.dp,
            strokeCap = StrokeCap.Square
        )

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = stringResource(id = R.string.mb_blinkid_loading),
            style = SdkTheme.sdkTypography.loadingScreen,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun LoadingScreenPreviewLight() {
    MaterialTheme(colorScheme = LightColorScheme) {
        LoadingScreen()
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
private fun LoadingScreenPreviewDark() {
    MaterialTheme(colorScheme = DarkColorScheme) {
        LoadingScreen()
    }
}
