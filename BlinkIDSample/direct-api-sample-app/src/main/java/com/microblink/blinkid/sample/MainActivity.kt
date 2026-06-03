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
import com.microblink.blinkid.core.session.BlinkIdProcessResult
import com.microblink.blinkid.core.session.BlinkIdSessionSettings
import com.microblink.blinkid.core.session.InputImageSource
import com.microblink.blinkid.core.settings.ScanningSettings
import com.microblink.blinkid.core.settings.scanning.DocumentCaptureModuleSettings
import com.microblink.blinkid.core.settings.scanning.VizModuleSettings
import com.microblink.blinkid.sample.navigation.Destination
import com.microblink.blinkid.sample.result.BlinkIdResultHolder
import com.microblink.blinkid.sample.result.BlinkIdSampleResultScreen
import com.microblink.blinkid.sample.ui.MainScreenDirectApi
import com.microblink.blinkid.sample.ui.theme.BlinkIDTheme
import com.microblink.blinkid.sample.utils.MainViewModel
import com.microblink.blinkid.sample.utils.getBitmapFromAsset
import com.microblink.blinkid.core.image.InputImage
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


                MainScreenDirectApi(
                    viewModel,
                    launchDocumentScanning = {
                        viewModel.viewModelScope.launch {
                            viewModel.initializeLocalSdk(context)
                            viewModel.localSdk?.let {
                                val session = it.createScanningSession(
                                    BlinkIdSessionSettings(
                                        inputImageSource = InputImageSource.Photo,
                                        scanningSettings = ScanningSettings(
                                            // ensure that the scanned images are placed
                                            // in the BlinkIdScanningResult object
                                            documentCaptureModule = DocumentCaptureModuleSettings(
                                                faceImageExtractionEnabled = true,
                                                inputImageReturnEnabled = true,
                                                documentImageReturnEnabled = true,
                                                inputImageMargin = null
                                            ),
                                            vizModule = VizModuleSettings(
                                                signatureImageExtractionEnabled = true,
                                                resultAggregationEnabled = null
                                            )
                                        )
                                    )
                                ).getOrNull()
                                var result: Result<BlinkIdProcessResult>? = null

                                // define front image
                                val imageFront =
                                    getBitmapFromAsset(context, "test-images/front.jpg")
                                imageFront?.let {
                                    result =
                                        session?.process(InputImage.createFromBitmap(imageFront))
                                }

                                // optional - define back image
                                val imageBack =
                                    getBitmapFromAsset(context, "test-images/back.jpg")
                                imageBack?.let {
                                    result =
                                        session?.process(InputImage.createFromBitmap(imageBack))
                                }

                                if (result != null) {
                                    val sessionResult = session?.getResult()?.getOrNull()
                                    if (result.isSuccess && sessionResult != null) {
                                        viewModel.onDirectApiResultAvailable(sessionResult)
                                        navController.navigate(
                                            route = Destination.BlinkIdResult
                                        )
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Scanning failed.",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        viewModel.unloadSdk()
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Direct API Error",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    viewModel.unloadSdk()
                                }
                            }
                        }
                    }
                )
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