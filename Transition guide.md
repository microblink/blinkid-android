# Transition Guide: BlinkID v6 to BlinkID SDK v7

This guide will help you migrate your application from BlinkID v6 to the new BlinkID v7 SDK. The new BlinkID v7 provides a modernized approach to document scanning and extraction with improved architecture and Jetpack Compose support.

## Key differences

### 1. Architecture changes

- **New Core Components**: Instead of Recognizer-based architecture, BlinkID uses a streamlined Session-based approach
- **Modern Kotlin Features**: Written fully in Kotlin, the code is simple and easy to work with
- **Jetpack Compose**: Jetpack Compose is the main driver for the UI through `blinkid-ux` package
- **Simplified Flow**: More straightforward API with clearer separation of concerns
- **Updated minimum OS requirement**: BlinkID SDK now requires Android API level 24 (Android 7.0 Nougat) or newer. This update allows us to leverage modern development practices, improve stability, and streamline future updates.

### 2. Integration methods

#### BlinkID v6 (Old):
```kotlin
1. Maven (maven.microblink.com)
2. Manual Integration (through .aar)
```

#### BlinkID v7 (New):
```kotlin
1. Maven (Maven Central)
2. Manual Integration (through .aar)
3. Custom integration (source-available UX module allows forking and customizations)
```

## Migration guide

### 1. Update dependencies

#### Remove declaration of old maven repository:

```kts
// remove
maven { url 'https://maven.microblink.com' }
// from repositories declaration in your gradle files
```

#### Remove old dependencies:
```kts
// remove
implementation(com.microblink:blinkid) 
// from build.gradle.kts

// or
microblink-blinkid = { module = "com.microblink:blinkid", version.ref = "microblinkBlinkId" }
// from libs.versions.toml

// or through .aar file
```

#### Add new dependencies:
```kotlin
// for the base BlinkID SDK version, add
implementation(com.microblink:blinkid-core)

// for the version that includes the scanning UX, add
implementation(com.microblink:blinkid-ux)
// to build.gradle.kts
// NOTE: blinkid-ux depends on blinkid-core, so there is no need to include both 

// alternatively, use libs.versions.toml
blinkid-core = { group = "com.microblink", name = "blinkid-core", version.ref = "blinkIdSdkVersion" }
blinkid-ux = { group = "com.microblink", name = "blinkid-ux", versions.ref = "blinkIdSdkVersion" }

// or through .aar file
```

### 2. Update Import Statements

#### Old:
```kotlin
import com.microblink.blinkid.*
```

#### New:
```kotlin
import com.microblink.blinkid.core*
// if using the UX components
import com.microblink.blinkid.ux*  
```

### 3. Initialization Changes

#### Old (BlinkID):
```kotlin
// old initialization
MicroblinkSDK.setLicenseFile("license-key", context)

// creating recognizer
mRecognizer = new BlinkIdMultiSideRecognizer();
// bundle recognizers into RecognizerBundle
mRecognizerBundle = new RecognizerBundle(mRecognizer);
```

#### New (BlinkID):
```kotlin
// New initialization
val instance = BlinkIdSdk.initializeSdk(
    BlinkIdSdkSettings(
        context = context,
        licenseKey = licenseKey
    )
)

when {
    instance.isSuccess -> {
        BlinkIdCameraScanningScreen(
            blinkIdSdk = instance,
            uxSettings = blinkIdUxSettings,
            uiSettings = uiSettings,
            sessionSettings = sessionSettings,
            onScanningSuccess = { },
            onScanningCancelled = { }
        )
    }

    maybeInstance.isFailure -> {
        val exception = instance.exceptionOrNull()
        Log.e(TAG, "Initialization failed", exception)
    }
}
```

### 4. UI Implementation Changes

#### Old (BlinkID):

Many different implementation methods exist for BlinkID, with the following being the simplest:
```kotlin
    private val resultLauncher = registerForActivityResult(TwoSideDocumentScan()) { twoSideScanResult: TwoSideScanResult ->
    when (twoSideScanResult.resultStatus) {
        ResultStatus.FINISHED -> {
            // code after a successful scan
            // use twoSideScanResult.result for fetching results, for example:
            val firstName = twoSideScanResult.result?.firstName?.value()
            Toast.makeText(this@MainActivity, "Name: $firstName", Toast.LENGTH_SHORT).show()
        }
        ResultStatus.EXCEPTION -> {
            // code after a failed scan
            Toast.makeText(this@MainActivity, "Scan failed: ${twoSideScanResult.exception?.message}", Toast.LENGTH_SHORT).show()
        }
        else -> {
            // code after a cancelled scan
            Toast.makeText(this@MainActivity, "Scan canceled!", Toast.LENGTH_SHORT).show()
        }
    }
}
```

#### New (BlinkID) using Jetpack Compose:

`CameraScanningScreen` is a `@Composable` function that can be invoked when needed.
It is recommended to use on its own separate screen through `Navigation` and `ViewModel` (see Sample app).
```kotlin
    BlinkIdCameraScanningScreen(
        blinkIdSdk = instance,
        uxSettings = BlinkIdUxSettings, // customize the behavior of the scanning screen
        uiSettings = uiSettings, // customize the appearance of the scanning screen (fonts, colors, strings)
        sessionSettings = sessionSettings, // define scanning session details such as number of document sides, image quality settings, anonymization, etc.
        onScanningSuccess = { 
            // define what happens when the scanning process completes successfully
        },
        onScanningCancelled = {
            // define what happens when the scanning process is cancelled
        }
    )
}
```

#### New (BlinkID) using Android Views:

Wrap the `Composable` in a `ComposeView` class:
```xml
<androidx.compose.ui.platform.ComposeView
android:id="@+id/my_composable"
android:layout_width="wrap_content"
android:layout_height="wrap_content" />
```

```kotlin
findViewById<ComposeView>(R.id.my_composable).setContent {
    MaterialTheme {
        Surface {
            BlinkIdCameraScanningScreen(...)
        }
    }
}
```

### New (BlinkID) using Activity:

```kotlin
val blinkIdLauncher = rememberLauncherForActivityResult(
   contract = MbBlinkIdScan(),
   onResult = { scanningResult ->
       if (scanningResult.status == BlinkIdScanActivityResultStatus.DocumentScanned) {
       // use scanningResult (BlinkIdScanningResult)
       }
   }
)

blinkIdLauncher.launch(
    BlinkIdScanActivitySettings(
        BlinkIdSdkSettings(
            licenseKey = <your_license_key>
        ),
    BlinkIdSessionSettings(
        scanningSettings = ScanningSettings(
        // define additional settings here
            )
        )
    )
)

```
For additional information on using Jetpack Compose with Views, visit [official docs](https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/compose-in-views).

### 5. Result Handling

#### Old (BlinkID):

Through Activity result implementation (see 4. UI Implementation changes):
```kotlin
        ResultStatus.FINISHED -> {
    // code after a successful scan
    // use twoSideScanResult.result for fetching results, for example:
    val firstName = twoSideScanResult.result?.firstName?.value()
    Toast.makeText(this@MainActivity, "Name: $firstName", Toast.LENGTH_SHORT).show()
}
```

Or through checking recognizer state:
```kotlin
  recognizerBundle.loadFromIntent(data)
  val result: BlinkIdSingleSideRecognizer.Result = recognizer.result
  val name = result.firstName?.value()
```

#### New (BlinkID):

Using the UX module to fetch the results:
```kotlin
BlinkIdCameraScanningScreen(
    blinkIdSdk = instance,
    uxSettings = uxSettings,
    uiSettings = uiSettings 
    sessionSettings = sessionSettings, 
    onScanningSuccess = { result ->
        // result is now available
        navController.navigateTo(
            route = Destination.ResultScreen, // navigate to result screen or somewhere else
            inclusive = false
        )
    },
    onCaptureCanceled = {
        navController.popBackStack(
            route = Destination.Main,
            inclusive = false
        )
    }
)
```

Using the `BlinkIdScanActivity` result:
```kotlin
val blinkIdLauncher = rememberLauncherForActivityResult(
   contract = MbBlinkIdScan(),
   onResult = { activityResult ->
      if (activityResult.status == BlinkIdScanActivityResultStatus.DocumentScanned) {
          // use activityResult.result (BlinkIdScanningResult)
      }
   }
)
```

### 6. Custom UI Implementation

#### Old (BlinkID):

Old BlinkID offered several ways of custom UI integration through resource and UI customization.
More info can be found here [on our GitHub page](https://github.com/Microblink/blinkid-android/tree/v6.13.0?tab=readme-ov-file#-blinkid-sdk-integration-levels).

#### New (BlinkID):

New implementation offers customization in two ways:
- Through `UiSettings` class
- By forking the repository and customizing certain classes

`UiSettings` offers quick and easy customization for colors, strings, and fonts used in the SDK.
```kotlin
public data class UiSettings(
    val typography: UiTypography? = null,
    val colorScheme: ColorScheme? = null,
    val uiColors: UiColors? = null,
    val sdkStrings: SdkStrings? = null,
    val showOnboardingDialog: Boolean = DefaultShowOnboardingDialog,
    val showHelpButton: Boolean = DefaultShowHelpButton
)
```

```kotlin
val blinkIdUiSettings = UiSettings(
    typography = ..., // override if necessary
    colorScheme = ...,  // override if necessary
    uiColors = ...,  // override if necessary
    sdkStrings = ...,  // override if necessary
    showOnboardingDialog = ...,  // default: true; override if necessary
    showHelpButton = ...,  // default: true; override if necessary
)

val blinkIdUxSettings = BlinkIdUxSettings(
   stepTimeoutDurationMs = ... // default: [Duration] 15000.milliseconds,
   classFilter = ... // default: [ClassFilter] null
)

fun CameraScanningScreen(
    sdkInstance,
    blinkIdUxSettings,
    blinkIdUiSettings,
    ...)
```

If these customization options are not enough, certain files can be modified.
Any class in `blinkid-ux` and `microblink-ux` libraries which has this license header is allowed to be modified.
```
/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */
```

Any modifications to classes which do not have this exact header are not allowed.

## Best Practices for Migration

1. **Gradual Migration**:
    - Consider migrating feature by feature if possible
    - Test thoroughly in a development environment before production deployment

2. **Resource Management**:
    - Decide between downloaded or bundled resources early in the migration
    - Set up proper resource paths and Blink ID resource loading

3. **UI/UX Considerations**:
    - Take advantage of Jetpack Compose if possible
    - Consider reimplementing custom UI components using the new architecture

4. **Error Handling**:
    - Update error handling to work with the new async/await pattern
    - Implement proper error handling for resource downloading if used

## Support and Resources

- For API documentation: Visit the BlinkID SDK [Android API](https://blinkid.github.io/blinkid-android/index.html) docs.
- For support: Contact technical support through the support portal
