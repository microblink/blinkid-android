package com.microblink.blinkid.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.microblink.blinkid.sample.navigation.Destination
import com.microblink.blinkid.sample.result.BlinkIdSampleResultScreen
import com.microblink.blinkid.sample.ui.MainScreen
import com.microblink.blinkid.sample.ui.theme.BlinkIDTheme
import com.microblink.blinkid.sample.result.BlinkIdResultHolder
import com.microblink.blinkid.sample.utils.MainViewModel
import com.microblink.blinkid.ux.BlinkIdCameraScanningScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlinkIDTheme {
                val navController = rememberNavController()
                MainNavHost(navController = navController)
            }
        }
    }

    @Composable
    fun MainNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        startDestination: Destination = Destination.Main,
    ) {
        val context = LocalContext.current
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable<Destination.Main> {
                val mainState by viewModel.mainState.collectAsStateWithLifecycle()

                mainState.error?.let {
                    Toast.makeText(this@MainActivity, "Error: $it", Toast.LENGTH_LONG).show()
                    viewModel.resetState()
                }

                MainScreen(
                    viewModel,
                    launchDocumentScanning = {
                        viewModel.viewModelScope.launch {
                            viewModel.initializeLocalSdk(context)
                            navController.navigate(Destination.DocumentScanning)
                        }
                    }
                )
            }
            composable<Destination.DocumentScanning> {
                val sdk = viewModel.localSdk
                if (sdk != null) {
                    BlinkIdCameraScanningScreen(
                        sdk,
                        uxSettings = viewModel.blinkIdUxSettings,
                        uiSettings = viewModel.blinkIdUiSettings,
                        cameraSettings = viewModel.cameraSettings,
                        sessionSettings = viewModel.scanningSessionSettings,
                        onScanningSuccess = { result ->
                            viewModel.onScanningResultAvailable(result)
                            navController.navigate(
                                route = Destination.BlinkIdResult
                            )
                        },
                        onScanningCanceled = {
                            navController.popBackStack(
                                route = Destination.Main,
                                inclusive = false
                            )
                            viewModel.onScanningCanceled()
                        },
                        onFrameProcessResult = viewModel.frameProcessResultCallback
                    )
                }
            }
            composable<Destination.BlinkIdResult> {
                BlinkIdSampleResultScreen(
                    result = BlinkIdResultHolder.blinkIdResult,
                    onNavigateUp = {
                        viewModel.resetState()
                        navController.popBackStack(
                            route = Destination.Main,
                            inclusive = false
                        )
                    }
                )
            }
        }
    }
}