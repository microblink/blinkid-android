# Release notes

## v7.8.0

### What's new

- The list of all supported documents and result fields is now available [here](https://docs.microblink.com/blinkid/supported-documents).
- We have added extraction of date of birth from document numbers on Egypt DL.
- If a residence permit has a "remarks" field on both the front and back side, values of these fields will be combined in the top level result.

## Bug fixes

- We have fixed MRZ parsing rules for Zimbabwe ID and the new version of Brunei ID; these are now successfully extracted.

### New documents support

- Argentina - Polycarbonate Passport
- Bhutan - Identity Card
- Georgia - Polycarbonate Passport
- Jamaica - Identity Card
- Maldives - Driver's License
- Mongolia - Identity Card
- New Zealand - Proof Of Age Card
- Pakistan - Origin Card
- Saint Kitts And Nevis - Polycarbonate Passport
- South Sudan - Identity Card
- Virgin Islands Of The United States - Driver's License
- Virgin Islands Of The United States - Identity Card

#### New document versions for supported documents

- Argentina - Alien ID
- Argentina - Identity Card
- Armenia - Identity Card
- Australia, Australian Capital Territory - Driver's License
- Australia, Australian Capital Territory - Identity Card
- Brunei - Identity Card
- Bulgaria - Residence Permit
- Denmark - Driver's License
- Georgia - Identity Card
- Greece - Residence Permit
- Guatemala - Alien ID
- Guatemala - Identity Card
- Guyana - Paper Passport
- Kosovo - Driver's License
- Kyrgyzstan - Polycarbonate Passport
- Liechtenstein - Identity Card
- Mauritius - Identity Card
- Nigeria - Identity Card
- Puerto Rico - Driver's License
- Puerto Rico - Identity Card
- Uganda - Identity Card
- USA - Paper Passport
- USA - Polycarbonate Passport
- USA, Montana - Driver's License
- USA, Montana - Identity Card
- USA, New York City - Identity Card
- Venezuela - Driver's License

#### New segments supported on documents

- Pakistan, proof of registration: renamed fathersName to additionalNameInformation
- Mauritania, ID: renamed documentNumber to personalIdNumber

### Minor API changes

- Added new items to enums:
  - new `FieldType` enum values: `CardAccessNumber`
  - new `Type` enum values: `ORIGIN_CARD`
  - new `Country` enum value: `VIRGIN_ISLANDS_OF_THE_UNITED_STATES`
- Added member results to `ScanningResult` and `VizResult`:
  -  'cardAccessNumber'
- Removed items from enums:
  -  removed `FieldType` enum values: Removed `ParentsLastName2`, `ParentsFirstName2`, `ChinPermanentExpiry`
  -  removed `Country` enum value: `VIRGIN_ISLANDS_US`

## v7.7.1

### What's new
- Improved SDK and session shutdown behavior, and removed misleading warning logs during normal cleanup
- Improved analytics reporting
- Improved SDK initialization to help reduce failures

## v7.7.0

### What's new
- Barcode extraction is marked as optional on documents where barcode detecton has bad performance (Cuba ID and Passport, Philippines DL, Haiti ID, Sudan ID, Egypt ID, Ecuador Passport, Ghana Passport, Iraq Passport, Nicaragua Passport, Pakistan Passport)

## Bug fixes
- In situations where some fields on different sides of a document have values in multiple alphabets it could happen that while merging results we overwrite them and keep only one alphabet. With this version this is fixed and both alphabets are returned.

### New documents support
- Angola - Identity Card
- Antigua And Barbuda - Paper Passport
- Barbados - Paper Passport
- Belize - Paper Passport
- Benin - Driver's License
- Benin - Polycarbonate Passport
- Bermuda - Paper Passport
- Bermuda - Polycarbonate Passport
- Bhutan - Paper Passport
- Botswana - Paper Passport
- Brazil, Acre - Identity Card
- Brazil, Espirito Santo - Identity Card
- Brazil, Mato Grosso Do Sul - Identity Card
- Brazil, Paraiba - Identity Card
- Brazil, Piaui - Identity Card
- Brazil, Rio Grande Do Norte - Identity Card
- Brazil, Tocantins - Identity Card
- Central African Republic - Paper Passport
- Chad - Paper Passport
- Chad - Polycarbonate Passport
- Congo - Paper Passport
- Democratic Republic Of The Congo - Paper Passport
- Djibouti - Paper Passport
- Djibouti - Polycarbonate Passport
- Equatorial Guinea - Paper Passport
- Equatorial Guinea - Polycarbonate Passport
- Eswatini - Identity Card
- Ethiopia - Paper Passport
- Federated States Of Micronesia - Paper Passport
- Gabon - Paper Passport
- Gabon - Polycarbonate Passport
- Ghana - Polycarbonate Passport
- Ghana - Social Security Card
- Guinea - Driver's License
- Guinea Bissau - Paper Passport
- Guinea Bissau - Polycarbonate Passport
- India, Odisha - Driver's License
- India, Uttarakhand - Driver's License
- Ireland - Proof Of Age Card
- Laos - Paper Passport
- Lesotho - Paper Passport
- Liberia - Driver's License
- Liberia - Social Security Card
- Madagascar - Paper Passport
- Malawi - Driver's License
- Mauritania - Identity Card
- Mauritania - Polycarbonate Passport
- Mexico - Social Security Card
- Mongolia - Paper Passport
- Mongolia - Polycarbonate Passport
- Namibia - Paper Passport
- Niger - Paper Passport
- Nigeria - Nin Card
- Papua New Guinea - Paper Passport
- Philippines - eID
- Philippines - MySSS Card
- Puerto Rico - Identity Card
- Saint Thomas And Prince - Paper Passport
- Saint Vincent And The Grenadines - Paper Passport
- Seychelles - Paper Passport
- Seychelles - Polycarbonate Passport
- South Sudan - Polycarbonate Passport
- Taiwan - Paper Passport
- Togo - Gendarmerie ID
- Togo - Military ID
- Togo - Police ID
- Togo - Residence Permit
- Togo - Voter ID
- Tonga - Paper Passport
- Yemen - Paper Passport
- Ghana - Health Insurance Card

#### New document versions for supported documents
- Afghanistan - Identity Card
- Cameroon - Identity Card
- Chile - Driver's License
- Colombia - Alien ID
- Costa Rica - Identity Card
- Malaysia - Driver's License
- Moldova - Identity Card
- Netherlands - Driver's License
- Panama - Driver's License
- South Korea - Driver's License
- South Korea - Identity Card
- Sweden - Driver's License
- UK - Polycarbonate Passport
- USA - Veteran ID
- USA, Alaska - Identity Card
- USA, California - Driver's License
- USA, California - Identity Card
- USA, North Carolina - Driver's License
- USA, Texas - Driver's License
- USA, Texas - Identity Card
- USA, Texas - Weapon Permit
- Vietnam - Driver's License
- Zimbabwe - Identity Card

#### New segments supported on documents
- Greece, Estonia, Finland, Hungary, Ireland, Latvia, Lithuania, Norway, Romania, Slovenia, Croatia, Slovakia, Poland, Malta, Austria, Luxembourg, Netherlands, Bulgaria, Portugal, Cyprus, Sweden, Czechia, Belgium, Germany, Italy, Spain, Switzerland, Denmark - Residence Permit: remarks, residencePermitType
- Belgium, Minors ID: added parentsInfo vector
- Nicaragua, Passport: barcode
- USA, Social Security Card: workRestriction
- China, Identity Card: permanentExpiry (Chinese)

### Minor API changes
- Added new items to enums:
  - new `FieldType` enum values: `EffectiveDate`, `ParentsLastName`, `ParentsLastName2`, `ParentsFirstName2`, `WorkRestriction`, `ParentsFirstName`, `SocialSecurityStatus`, `LegalStatus`, `HusbandName`, `ChinPermanentExpiry`
  - new `Type` enum values: `NinCard`, `MySSSCard`, `GendarmerieId`, `PoliceId`
  - new `Country` enum value:`SaintThomasAndPrince`
  - new `Region` enum values:`Acre`,`EspiritoSanto`,`MatoGrossoDoSul`,`Paraiba`,`Piaui`,`RioGrandeDoNorte`,`Tocantins`,`Odisha`,`Uttarakhand`

- Added member results to `ScanningResult` and `VizResult`
  - `effectiveDate`
  - `husbandName`
  - `legalStatus`
  - `socialSecurityStatus`
  - `workRestriction`
  - `parentsInfo` (new class)

- Android permissions:
  - `blinkid-ux`: Removed the mandatory ***camera hardware requirement*** from AndroidManifest.xml to improve compatibility with specific devices that incorrectly report camera hardware presence. The camera feature is now declared as optional:
    ```xml
    <uses-feature android:name="android.hardware.camera.any" android:required="false"/> 
    ```
  
### Breaking changes
The following changes will impact your implementation only if you have ***advanced SDK customizations*** and don’t use the default `Activity` or `Composable`:
- `DocumentSide`(`Front`, `Back`, `Barcode`) renamed to `UiScanningSide` (`First`, `Second`, `Barcode`)
- `CommonStatusMessage` changed its members (to remove `Document`, `Front`, and `Back` occurrences):
  - `ScanFrontSide` to `ScanFirstSide`
  - `ScanBackSide` to `ScanSecondSide`
  - `FlipDocument` to `Flip`
  - `KeepDocumentVisible` to `KeepVisible`
  - `AlignDocument` to `Align`
  - `ScanBarcode`, `RotateDocument`, `RotateDocumentShort`, `KeepFacePhotoVisible`, `IncreaseLightingIntensity`, `DecreaseLightingIntensity`, `EliminateGlare` and `FilterSpecificMessage`  have been moved from `CommonStatusMessage` to `BlinkIdStatusMessage`
- Composable `HelpScreens` is implemented through a list of `HelpScreenPage` data classes
- Composable `OnboardingDialog` is implemented through a single `HelpScreenPage` instance
- Error dialogs for the default UI are now defined in the `ComposableUtils.kt` file
- `BlinkIdScanActivityColors` replaced with `ScanActivityColors`
- `BlinkIdScanActivitySettings` now extends a new `ScanActivitySettings` interface
- Changed members of `ScanningUxEvent`:
  - `RequestDocumentSide(DocumentSide)` to `RequestSide(UiScanningSide)`
- `BlinkIdColorScheme` and `DarkBlinkIdColorScheme` are replaced with `LightColorScheme` and `DarkColorScheme`
- Removed `DocumentDrawable` (composable used for `FlipAnimation`) - animations now directly use a `Drawable`
- `DocumentFlipAnimation` replaced with `FlipAnimation`

The following changes will possibly impact any implementation:
- `DocumentAlreadyScannedException` renamed to `ScanAlreadyCompletedException`
- `BlinkIdScanActivityResultStatus`(`DocumentScanned`, `Canceled`, `ErrorSdkInit`) renamed to `ScanActivityResultStatus`(`Scanned`, `Canceled`, `ErrorSdkInit`)
  
These changes were introduced to make the transition to future BlinkID v8000 SDK easier.

### Improvements and bug fixes
- Accessibility fixes for dialogs (`OnboardingDialog` and `HelpDialog`)
- Removed `MoveDocumentFromEdge` scanning UI message
- UI messages now correctly reflect `skipImagesWithBlur`, `skipImagesWithGlare`, `skipImagesWithInadequateLightingConditions` and `skipImagesOccludedByHand` settings - when enabled, feedback message is provided to the user to adjust the document. These settings should be set to `true` when the goal of the scan is not only to correctly read all the data fields but also to capture the document image.
- Fixed bug which could cause a crash when closing `BlinkIdScanningSession` in some situations
- Added `MbLog` for additional debugging and logging capabilities

## v7.6.1

### What's New
- For some documents, the document type was returned as None, which was causing confusion. To prevent this, we are filling in the document type from the barcode in case customers are using BarcodeId mode.
- The fix is applied to all the AAMVA types, plus some others (Argentina, Canada, Colombia, Nigeria, Panama, Paraguay, SouthAfrica).

## v7.6.0

### What's New
- Added support for capturing the back of US and India passports that feature a barcode
- Prevent parsing of two-line MRZ in TD1 format unless it's explicitly allowed. This will prevent false positive MRZ extraction on documents where the last line of the MRZ is covered or not fully visible

### Bugfixes
- Users are no longer forced to scan back sides of Alien and Refugee passports
- Fixed the issue with Togo ID where document number from VIZ was overriden by a wrong value from MRZ

### New Documents Support
- Angola - Paper Passport
- Bahrain - Polycarbonate Passport
- Burkina Faso - Polycarbonate Passport
- Cameroon - Driver's License
- Canada, Manitoba - Metis Federation Card
- East Timor - Polycarbonate Passport
- El Salvador - Paper Passport
- Eritrea - Paper Passport
- France - Adr Certificate
- Germany - Adr Certificate
- Ghana - Voter ID
- India, Telangana - Driver's License
- Ivory Coast - Paper Passport
- Japan - Polycarbonate Passport
- Liberia - Paper Passport
- Liberia - Voter ID
- Malawi - Identity Card
- Malawi - Paper Passport
- Maldives - Polycarbonate Passport
- Mali - Paper Passport
- Mauritius - Paper Passport
- Oman - Vehicle Registration
- Paraguay - Polycarbonate Passport
- Rwanda - Driver's License
- Senegal - Driver's License
- Sierra Leone - Paper Passport
- Somalia - Paper Passport
- Switzerland - Adr Certificate
- Togo - Driver's License
- Togo - Paper Passport
- USA, Maryland - Medical Marijuana ID
- Vietnam - Paper Passport

#### New Document Versions for Supported Documents
- Chile - Polycarbonate Passport
- India - Paper Passport
- Moldova - Identity Card
- Pakistan - Identity Card
- Peru - Identity Card
- Romania - Identity Card
- Slovakia - Identity Card
- USA, California - Driver's License
- USA, California - Identity Card
- USA, New Hampshire - Identity Card
- USA, Georgia - Medical Marijuana ID
- USA, Pennsylvania - Medical Marijuana ID
- USA, South Carolina - Driver's License
- USA, South Carolina - Identity Card
- USA, Texas - Driver's License
- USA, Texas - Identity Card

#### New Segments Supported on Documents
- Switzerland, Residence Permit - 'dateOfEntry'
- Hungary, Identity Card - 'maidenName', 'nationality', 'sex', 'documentNumber', 'dateOfBirth'
- Greece, Identity Card - 'fathersName' (Latin and Greek), 'mothersName' (Latin and Greek), 'personalIdNumber', 'issuingAuthority' (Greek), 'municipalityOfRegistration' (Greek)
- Mexico, Voter ID - 'sectionCode', 'stateCode', 'municipalityCode', 'localityCode'
- Mexico, Consular Voter ID - 'stateCode', 'stateName'

#### Renamed segments 
- Hungary - Identity Card - `additionalNameInformation` -> `mothersName`

### Other changes

#### SDK updates
- Updated Kotlin to `v2.0.21` in the `blinkid-core` library. The `blinkid-ux` and `microblink-ux` libraries already use Kotlin `v2.1.20`.
- Target SDK updated to API Level 36
#### UI/UX updates
- Camera selection setting is now available for our integrated scanning solutions through `CameraSettings`
- Torch button is now not shown on devices and cameras that do not support it
- Accessibility improvements regarding `TalkBack` and navigation
- 33 new languages (Full list [here](https://github.com/microblink/blinkid-android?tab=readme-ov-file#-new-languages-v76))
- Changed default `tiltDetectionLevel` from `Off` to `Mid`
#### UI/UX bugs and fixes
- The correct color from `MaterialTheme` is now applied to the loading screen
- Timeout and help tooltip timers behave properly on configuration and UI changes (background, dialogs, and device rotation)


## v7.5.0

### What's New
- We introduced event tracking across the SDK lifecycle, giving you deeper insights into user journeys, success rates, and drop-off points during scanning sessions. These enhanced analytics make it easier to identify optimization opportunities and ensure the best possible user experience.
- Updated detection analysis logic in case of photo mode
- Added new `parsed` result member to the BarcodeResult which indicates whether the raw barcode data was successfully parsed
- Barcode recognition is now allowed on all document classes; unparsable barcodes will be returned as raw data
- 
### Bugfixes
- Fixed the issue with scanning the back of the Essad Card which was causing only Date of Expiry to be extracted
- Fixed the issue with additionalNameInformation extraction for France ID and Residence Permit
  Android

### Other Changes
- Declared additional `android.permission.ACCESS_NETWORK_STATE` in the `blinkid-core` library manifest
    - This is a normal protection-level permission, automatically granted at install time – no runtime request is required
    - The permission is used to check whether a network connection is available
- Added new optional callbacks to the `CameraCompose` Composable from the `microblink-ux` library:
    - `CameraPermissionCallbacks` - track camera permission events
    - `CameraPreviewCallbacks` - track camera preview lifecycle events
    - `CameraInputDetailsCallback` - receive camera input configuration details

## v7.4.0

#### New Documents Support
- Canada, Newfoundland And Labrador - Identity Card
- Canada, Northwest Territories - Driver's License
- Canada, Northwest Territories - Identity Card
- Canada, Prince Edward Island - Identity Card
- Canada, Yukon - Identity Card

#### New Document Versions for Supported Documents
- Canada, Yukon - Driver's License

### API Changes
- Renamed `Product` enum to `MbProduct`
- `dependentsInfo` in `VizResult` is now nullable

#### Bug fixes
- Replaced caching directory for storing downloaded resource files - avoid rare crashes on specific devices

## v7.3.0

### What's new
- Improved extraction for Canada/Nunavut ID and DL by introducing error correction for "1" and "I" characters which look the same in the font used on a document

#### New Documents Support
- Canada, Nunavut - Driver's License
- Canada, Nunavut - Identity Card
- Liberia - Identity Card
- Mali - Identity Card
- UK - Military ID

#### New Document Versions for Supported Documents
- Bahrain - Identity Card
- Canada - Weapon Permit
- Chile - Alien ID
- Chile - Identity Card
- Finland - Driver's License
- Indonesia - Driver's License
- Kosovo - Identity Card
- Latvia - Polycarbonate Passport
- Mexico, Chiapas - Driver's License
- Mexico, Ciudad de Mexico - Driver's License
- Mexico, Durango - Driver's License
- Mexico, Jalisco - Driver's License
- Sri Lanka - Driver's License
- USA, Alaska - Driver's License
- USA, New Hampshire - Driver's License
- European Union - Health Insurance Card

#### New Beta Documents Support
- Canada - Non Card Tribal ID
- Dominica - Paper Passport
- Dominica - Polycarbonate Passport
- UAE - Diplomatic ID
- USA, Georgia - Medical Marijuana ID

#### New Document Versions for Beta-Supported Documents
- Egypt - Driver's License
- Mexico, Quintana Roo - Driver's License
- Philippines - Postal ID
- Vietnam - Identity Card

#### New Segments Supported on Documents
- European Union, Health Insurance Card - `countryCode`
- Italy, Identity Card - `documentOptionalAdditionalNumber`
- France, Identity Card - `additionalNameInformation`
- UK, Asylum Request - `residencePermitType`, `remarks`
- UK, Residence Permit - `residencePermitType`, `remarks`, `certificateNumber`, `nationalInsuranceNumber`

#### Renamed segments
- Bahrain - Identity Card - `documentNumber` -> `personalIdNumber`

### Bugs and fixes
- Fixed document number extraction from Canada/Nunavut barcodes
- Fix for ARGENTINA ID and ALIEN_ID documents - made separate barcode scanning step optional for these documents; they have a barcode on the front side, and requiring barcode extraction was causing the scanning process to get stuck on the front
- Onboarding and help screen settings now correctly apply to `BlinkIdScanActivity`

### UI/UX updates
- Complete scanning instruction messages revamp - the scanning session is now more stable and cleaner, which ensures a better scanning experience
- Added new accessibility features
- Added haptic feedback during the scanning session (setting `allowHapticFeedback`)
- Added a separate timeout timer for the Barcode step
- Updated help screens with new illustrations
- Updated `Need help?` tooltip triggers
- Updated translations for Croatian language

## v7.2.1

### What's new
- We've temporarily lowered the minimum required Kotlin version to `v1.9.24` only for `blinkid-core` library to address compatibility issues. This change will be reverted in a near future.
- Added additional functions for better interoperability with Java to following classes: `BlinkIdSdkSettings`, `BlinkIdScanActivitySettings`, `BlinkIdUxSettings`, `BlinkIdSdkStrings`, `ParcelableTextStyle`, `ParcelableFont`

## v7.2.0

### What's new
- Added message for hand occlusion detection. The setting `skipImagesOccludedByHand` set to `true` by default
- Added message for poor lighting conditions detection. The setting `skipImagesWithInadequateLightingConditions` set to `true` by default
- Added "Demo" overlay for the demo licenses (non-production)
- Added "Powered by Microblink" overlay option for licenses with this enabled

### Other Changes
- Renamed class `StatusMessage` to `CommonStatusMessage`

### Bug Fixes
- Fixed data match bug when partial anonymisation is enabled
- Fixed face extraction bug for rotated face images on documents
- Improved face extraction for documents without face image (skipping face image extraction in that case)
- Fixed face extraction bug for Philippines Passport document
- Fixed an issue with filling the ProcessResult during the Barcode Capture step, ensuring only relevant fields are filled
- Fixed bug that forced scanning of the back side of the Germany Residence Permit in passport document
- Fixed bug that caused skipping back side when only signature was present

## v7.1.0

## What's New

#### New Documents Support
- Austria - Refugee Passport
- Austria - Polycarbonate Refugee Passport
- Burkina Faso - Driver's License
- Burkina Faso - Paper Passport
- Costa Rica - Residence Permit
- Gambia - Paper Passport
- Guinea - Polycarbonate Passport
- Rwanda - Polycarbonate Passport
- South Korea - Polycarbonate Passport
- Tanzania - Polycarbonate Passport
- Uganda - Paper Passport
- Uganda - Polycarbonate Passport
- Zambia - Driver's License
- Zambia - Paper Passport
- USA, Florida - Medical Marijuana ID
- USA, Pennsylvania - - Medical Marijuana ID

#### New Beta Documents Support
- Benin - Paper Passport
- Burundi - Polycarbonate Passport
- Chad - Identity Card
- Kenya - Driver's License
- Mozambique - Polycarbonate Passport
- Spain - Registration Certificate
- Sudan - Identity Card
- Zimbabwe - Driver's License
- USA, Nevada - Medical Marijuana ID
- USA, New York - Medical Marijuana ID
- USA, Oklahoma - Medical Marijuana ID

#### New Document Versions for Supported Documents
- Bangladesh - Second data page support on Paper Passport and Polycarbonate Passport
- Kosovo - Paper Passport
- Mexico, Colima - Driver's License
- Mexico, Mexico - Driver's License
- Netherlands - Identity Card
- Netherlands - Polycarbonate Passport
- Romania - Identity Card, Back side scanning
- Romania - Polycarbonate Passport
- Singapore - Employment Pass, Back side scanning
- Slovakia - Polycarbonate Passport
- Syria - Paper Passport
- USA, Wyoming - Driver's License

#### New Document Versions for Beta-Supported Documents
- Mexico, Guanajuato - Driver's License
- Mexico, Puebla - Driver's License
- Croatia - Health Insurance Card

#### New Segments Supported on Documents
- Greece
	- Identity Cards, Driver's Licenses, Residence Permits and Passports
	- expanding support for extracting segments in Greek script
- Saudi Arabia, Identity Card
	- expanding support for extracting segments in Arabic script
- Egypt, Driver's Licenses
	- expanding support for extracting segments in Arabic script

## New features
- Fixed data match overall result
- Added `Typography` customization to to `BlinkIDScanActivity` through `BlinkIdScanActivitySettings`

## v7.0.0

We’re excited to introduce BlinkID v7, a major upgrade designed to simplify your integration and deliver a simpler ID scanning experience. With BlinkID v7, we’re taking a fresh approach to scanning logic by introducing a more straightforward, session-based API for an easier configuration path, all while boosting first-time scan success rate.

### Highlights & Integration Improvements

- **Lighter SDK**: By incorporating dynamic model loading, BlinkID v7 drastically reduces download size from ~10MB to ~3.8MB, and lowers memory usage.
- **Unified session-based API**: We’ve moved away from juggling multiple recognizers (e.g., SingleSide, MultiSide) to a single session-based BlinkIDScanningSession, unifying scanning logic under one simplified API. There's no need to switch recognizers anymore.
- **Backward compatibility**: Existing production keys will continue to work with v7.0. No new license key is required for the upgrade.
- **More maintainable codebase**: This new architecture sets the stage for easier and faster updates.
- **Updated minimum OS requirement**: BlinkID SDK now requires Android API level 24 (Android 7.0 Nougat) or newer. This update allows us to leverage modern development practices, improve stability, and streamline future updates.

### Architecture Changes
- **New core components**: Instead of Recognizer-based architecture, BlinkID uses a streamlined Session-based approach.
- **Modern Kotlin features**: Written fully in `Kotlin`, the code is simple and easy to work with.
- **Jetpack Compose**: `Jetpack Compose` is the main driver for the UI through blinkid-ux package.
- **Simplified flow**: More straightforward API with clearer separation of concerns.

### Major API Changes

- New session-based API
  - Replaces the recognizer-based approach with a single, streamlined session model for easier scanning logic and better maintainability.

- Jetpack Compose UI & modular SDK
  - UI is now built with Jetpack Compose, simplifying integration via `blinkid-ux`.
  - The SDK is modular:
    - `blinkid-core` for scanning logic.
    - `blinkid-ux` for prebuilt UI components.

- Simplified initialization & result handling
  - New SDK initialization method:
    ```kotlin
    BlinkIdSdk.initializeSdk(BlinkIdSdkSettings(context, licenseKey))
    ```
  - Results are now retrieved through structured session-based callbacks instead of recognizer bundles.

- Enhanced UI customization & source-available UX
  - `UiSettings` enables direct customization of typography, colors, and strings.
  - The source-available UI layer allows advanced modifications for branding and accessibility.

- Many of the older settings have been renamed to be more intuitive, including among others:
  - `blurStrictnessLevel` → `blurDetectionLevel`
  - `enableBlurFilter` → `skipFramesWithBlur`
  - `glareStrictnessLevel` → `glareDetectionLevel`
  - `enableGlareFilter` → `skipFramesWithGlare`
  - `combineFrameResults` → `enableMultiFrameExtraction`
  - `cardRotation`→ `documentRotation`
  - `...`

### Plan Your Upgrade
We have prepared a [Transition Guide](https://github.com/microblink/blinkid-android/blob/master/Transition%20guide.md) to help you get started with BlinkID v7.0. It outlines all major API changes and best practices for migrating existing code. Read the transition guide here. For any questions or feedback, reach out to support@microblink.com. We value your input and look forward to hearing how BlinkID v7 improves your app’s experience!

## v6.13.0

### What's New

#### New Documents Support 
- Benin - Identity Card
- Costa Rica - Polycarbonate Passport
- Gambia - Identity Card
- Guinea - Identity Card
- Nicaragua - Paper Passport
- Pakistan - Polycarbonate Passport
- Saudi Arabia - Polycarbonate Passport
- Senegal - Paper Passport
- Suriname - Identity Card
- Uruguay - Paper Passport
- Zimbabwe - Polycarbonate Passport

#### New Document Versions for Supported Documents 
- Australia, Northern Territory - Proof Of Age Card
- Ecuador - Driver's License
- Ecuador - Paper Passport
- El Salvador - Identity Card
- Finland - Identity Card
- Germany - eID
- Greece - Driver's License
- Guatemala - Driver's License
- Kenya - Identity Card
- Luxembourg - Residence Permit
- Mexico, Hidalgo - Driver's License
- Mexico, Quintana Roo-Solidaridad - Driver's License
- Mexico, Tamaulipas - Driver's License
- Mozambique - Identity Card
- New Zealand - Driver's License
- Nicaragua - Identity Card
- Tunisia - Driver's License
- Uganda - Driver's License
- UK - Asylum Request
- UK - Proof Of Age Card
- USA - Passport Card
- USA, Michigan - Identity Card
- USA, Tennessee - Driver's License
- USA, Tennessee - Identity Card

#### New Beta Documents Support 
- Brazil, Alagoas - Identity Card
- Grenada - Paper Passport
- Honduras - Resident ID
- Paraguay - Paper Passport
- Saint Kitts and Nevis - Paper Passport
- UAE - Esaad Card

#### New Document Versions for Beta-Supported Documents 
- El Salvador - Driver's License
- Honduras - Driver's License
- Hong Kong - Polycarbonate Passport
- India, Gujarat - Driver's License
- Liechtenstein - Driver's License
- Mexico, Quintana Roo-Cozumel - Driver's License
- Mozambique - Driver's License
- Saint Lucia - Paper Passport

#### Out of beta
- UK - Asylum Request
- USA, North Dakota - Identity Card

### Minor API changes
- added new items to enums:
  - Type: `ESAAD_CARD`
  - FieldType: `VehicleOwner`
- added `vehicleOwner` result to `BlinkIdMultiSideRecognizer.Result`, `BlinkIdSingleSideRecognizer.Result`, and `VizResult`

### Bug Fixes
- NY ID/DL: Added logic to expose the Enhanced document subtype, allowing customers to distinguish between regular and enhanced versions.
- Bolivia & Namibia ID: Resolved data match inconsistencies that were causing data match failures.
- Romanian ID: Multi side scan no longer expects blank back card of old IDs, even with `skipUnsupportedBack` set to `false`.
- Fixed `backImageAnalysisResult.cardRotation` not being correctly populated when scanning the wrong side of a document. 

## v6.12.0

### What's New
- **Beta Feature: Second Page Passport Scanning**
     - We’ve launched support for scanning and extracting data from the second page of passports for select countries - Slovenia, Ireland, and New Zealand. When BlinkID detects one of these passports, a UI message will guide the user to the second page. By default, this feature is disabled but can be enabled via the `BlinkIdMultiSideRecognizer.scanPassportDataPageOnly` setting - if set to `false`, it will be required to scan the second page of certain passports. This feature is in beta, and your feedback is appreciated.
- USA Green Card - Enabled Data Match for the `Document Number` field, matching values from the VIZ (Visual Inspection Zone) and MRZ to further enhance extraction reliability.


### Bug Fixes
- Spain ID: Fixed an issue with indefinite expiry dates, ensuring consistent values between the MRZ and Visual Inspection Zone (VIZ).
- Bulgaria ID: Improved parsing for indefinite expiry dates in the MRZ for better accuracy.
- Netherlands ID & Norway Passport: Resolved issues with the `Personal ID number` field that were causing the data match feature to fail.
- Chinese Passport: Enhanced reliability of extracted data with additional logic for the `Document Number` field.
- German ID & Luxembourg ID: Adjusted name extraction logic to resolve issues with name separation, ensuring more consistent results.

## v6.11.2

### Bug fixes
- NYC Municipal ID & USA Border Crossing Card - Resolved an issue where the scanning process could get stuck on the back side during multi-side scanning. 

## v6.11.1

### Bug fixes
- Removed unused `libc++_shared.so` from the SDK 

## v6.11.0

### Expanded Document Coverage
#### New Documents Support
- Armenia - Paper Passport
- Bahamas - Temporary Residence Permit
- Bahamas - Work Permit
- Bangladesh - Polycarbonate Passport
- Comoros - Paper Passport
- Dominican Republic - Non Voter ID
- Greece - Polycarbonate Passport
- Jamaica - Voter ID
- Kazakhstan - Paper Passport
- Kyrgyzstan - Paper Passport
- Kyrgyzstan - Polycarbonate Passport
- Lebanon - Driver's License
- Tajikistan - Identity Card
- Turkmenistan - Polycarbonate Passport
- Uzbekistan - Identity Card

#### New Document Versions for Supported Documents
- Argentina - Alien ID
- Bulgaria - Identity Card
- Canada, British Columbia - Identity Card
- Canada, British Columbia - Minors Public Services Card
- Croatia - Driver's License
- Jamaica - Paper Passport
- Malaysia - i-Kad
- Mexico, Coahuila - Driver's License
- Montenegro - Driver's License
- Namibia - Identity Card
- Norway - Driver's License
- Pakistan - Proof of Registration
- Panama - Identity Card
- Panama - Residence Permit
- Peru - Minors ID
- Portugal - Identity Card
- Romania - Identity Card
- Trinidad and Tobago - Driver's License
- Turkey - Identity Card
- USA, Arizona - Identity Card
- USA, Colorado - Identity Card
- USA, Michigan - Identity Card
- USA, Mississippi - Driver's License
- USA, Mississippi - Identity Card
- USA, Montana - Identity Card
- USA, North Carolina - Identity Card
- USA, North Carolina - Driver's License
- USA, North Dakota - Driver's License
- USA, Vermont - Identity Card
- USA, Virginia - Identity Card
- USA, Wyoming - Driver's License
- USA, Wyoming - Identity Card
- Uzbekistan - Paper Passport

#### New Beta Documents Support
- Andorra - Paper Passport
- Andorra - Polycarbonate Passport
- Philippines - Driver's License
- Armenia - Work Permit
- Australia - Asic Card
- Azerbaijan - Driver's License
- Bahamas - Paper Passport 
- Bahamas - Polycarbonate Passport
- Cayman Islands - Paper Passport
- Cayman Islands - Polycarbonate Passport
- Cayman Islands - Voter ID
- Gibraltar - Driver's License
- Gibraltar - Identity Card
- Gibraltar - Paper Passport
- Gibraltar - Polycarbonate Passport
- Guyana - Paper Passport
- Kuwait - Bidoon Card
- Kyrgyzstan - Driver's License
- Monaco - Paper Passport
- Pakistan - Afghan Citizen Card
- Philippines, Bangsamoro - Driver's License
- Saint Lucia - Driver's License
- Saint Lucia - Paper Passport
- San Marino - Polycarbonate Passport
- Turks and Caicos Islands - Driver's License
- Turks and Caicos Islands - Identity Card
- Turks and Caicos Islands - Paper Passport
- Australia - Interim Health Insurance Card
- Australia - Reciprocal Health Insurance Card
- UAE - Vehicle Registration

#### New Document Versions for Beta-Supported Documents
- Barbados - Identity Card
- Brazil - Paper Passport
- Dominican Republic - Driver's License
- India, Gujarat - Driver's License
- Mexico, Puebla - Driver's License
- Philippines - Tax ID
- Philippines - Health Insurance Card
- Australia - Health Insurance Card

#### New Segments Supported on Documents
- USA - Green Card - `eligibilityCategory`
- USA - Work Permit - `eligibilityCategory`
- Greece - Driver's License - `placeOfBirth` (greek and latin)
- Saudi Arabia - Resident ID - `nationality` (arabic)
- Bahrain - `dateOfExpiry`
- USA - Driver's license & ID card - `specificDocumentValidity`
- Pakistan - Proof of Registration - `DependentDateOfBirth`, `DependentSex`, `DependentDocumentNumber` & `DependentFullName`

### Renamed segments 
- Barbados - Identity Card - `personalIdNumber` -> `documentNumber`
- Panama - Identity Card - `personalIdNumber` -> `documentNumber`
- Panama - Residence Permit - `personalIdNumber` -> `documentNumber`

### New Features
- **Greek Alphabet Support**
  - We’ve added support for extracting `Place of Birth` in both Greek and Latin scripts.
- API changes in `BlinkIdSingleSideRecognizer` and `BlinkIdMultiSideRecognizer`:
    - new fields in result: `manufacturingYear`, `vehicleType`, `eligibilityCategory`, `specificDocumentValidity`, `dependentsInfo`
    - new values in `ClassInfo` enums:
        - Region: `BANGSAMORO`
        - Type: `ASIC_CARD`, `BIDOON_CARD`, `INTERIM_HEALTH_INSURANCE_CARD`, `NON_VOTER_ID`, `RECIPROCAL_HEALTH_INSURANCE_CARD`, `VEHICLE_REGISTRATION`
    - new values in `FieldType` enum: `ManufacturingYear`, `VehicleType`, `DependentDateOfBirth`, `DependentSex`, `DependentDocumentNumber`, `DependentFullName`, `EligibilityCategory`, `SpecificDocumentValidity`

### Bug fixes
- Fix for duplicate attrs resource: `attr/mb_onboardingImageColor` when combining multiple Microblink's SDKs in the same app


## v6.10.0

### New features
- **Improved reliability and first-time successful scanning experience**
	- **_Avoiding Double Scans of the Front Side_**: For a more reliable scanning process, BlinkID now prompts users to flip the document when they scan the front side twice. This improves the overall experience and reduces the chance of mistakes.
	- **_Starting with the Right Side_**: If users attempt to scan the back side of a document first, BlinkID will prompt them to begin with the front side. This feature ensures that users follow the correct order, leading to a more reliable and user-friendly experience.
### Expanded document coverage
#### New document versions for supported documents
- Sweden - Social Security Card - Back side
- Bolivia - Driver's License - Back side
- Brazil, Distrito Federal - Identity Card - Back side
#### Out of beta
- Colombia - Temporary Protection Permit
#### New beta documents support
- France - Professional ID
- Mexico - Tax ID
#### New document versions for beta-supported documents
- Brazil, Parana - Identity Card - Back side
- Brazil, Santa Catarina - Identity Card - Back side
#### New segments supported on documents
- Peru - Alien ID - MRZ
### Accessibility improvements
- **Enhanced button contrast** - Improved contrast for the close and flashlight buttons, making them easier to distinguish.
- **Improved focus order** - Enhanced focus order on help and onboarding screens to ensure proper reading of instructions when using TalkBack.
- **Accessible button names** - Added accessible names to the help and flashlight buttons, enabling TalkBack to read them correctly.
- **Glare warning toast** - The toast is now correctly detected and read by TalkBack.
### Bug fixes
- Fallback for barcode scanning - `Barcode ID` recognition mode will now extract information from any supported barcode if full document recognition fails. Initially available only for US and Canadian documents, this feature is now available to all documents.
- MRZ field on US documents - Set to optional by default and currently refers to: Minnesota (DL & ID), New York (DL & ID), Vermont (DL) and Washington (DL & ID).
- `AdditionalProcessingInfo` - In cases of an empty result, this field now provides details about the reasons why scanning failed.
- Improved handling of face photo occlusion - The UI message is slightly changed to prompt users to keep the face photo fully visible when there is a finger occlusion.
### Other changes
- The SDK is now supported on Android 15 16KB page size devices.
- Added `imageExtractionFailures` to `AdditionalProcessingInfo` - this allows clients to actively track if any `ImageExtractionType` (`FullDocument`, `Face`, `Signature`) is not visible on the presented document.
- Added a new result member, `barcodeStepUsed`, to both `BlinkIdSingleSideRecognizer` and `BlinkIdMultiSideRecognizer`. This member indicates whether the barcode scanning step was utilized during the process.
- Added two new settings for `BlinkIdSingleSideRecognizer` and `BlinkIdMultiSideRecognizer`:
	- `allowBarcodeScanOnly` - allows barcode recognition to proceed even if the initial extraction fails - set to `false` by default
	- `combineFrameResults` - enables the aggregation of data from multiple frames - set to `true` by default

## v6.9.0

### New Features
- **Custom mandatory fields**
	- We’re introducing the option for customers to define their own set of mandatory fields. This feature allows greater flexibility in the scanning process by enabling the extraction of only the necessary information from identity documents. Now you can customize the process and focus on only those fields that are relevant to your use case.
	- Custom mandatory fields can be set at the document level or applied universally to all document types.
- **Face detector improvements**
	- We made significant enhancements to our face detector model.
- **Added new values to `ClassInfo` enum:**
	- `Region`:
		- `ALAGOAS` for Brazilian documents
	- `Type`:
		- `AFGHAN_CITIZEN_CARD`
		- `EID`
		- `PASS`
		- `SIS_ID`
### Expanded Document Coverage
#### New Documents Support
- Austria - Polycarbonate Passport
- Canada - Polycarbonate Passport
- Ecuador - Paper Passport
- Germany - eID
- Guatemala - Alien ID
- Iraq - Polycarbonate Passport
- Namibia - Identity Card
- Nepal - Polycarbonate Passport
- Sweden - SIS ID
- USA, Delaware - Identity Card
- USA, Louisiana - Identity Card
- USA, Vermont - Identity Card
#### New Document Versions for Supported Documents
- Bolivia - Identity Card
- Canada, British Columbia - Public Services Card
- Finland - Identity Card
- Greece - Identity Card
- Malaysia - iKAD
- Mexico, Nuevo Leon - Driver's License
- Mexico, Tlaxcala - Driver's License
- Morocco - Driver's License
- Thailand - Polycarbonate Passport
- USA - Alaska - Driver's License
- USA - Alaska - Identity Card
- USA - Connecticut - Identity Card
- USA - Delaware - Driver's License
- USA - District of Columbia - Driver's License
- USA - Hawaii - Identity Card
- USA - Idaho - Driver's License
- USA - Indiana - Driver's License
- USA - Indiana - Identity Card
- USA - Iowa - Identity Card
- USA - Kansas - Identity Card
- USA - Kentucky - Identity Card
- USA - Kentucky - Driver's License
- USA - Maine - Identity Card
- USA - Maine - Driver's License
- USA - Michigan - Driver's License
- USA - Minnesota - Driver's License
- USA - Minnesota - Identity Card
- USA - Montana - Identity Card
- USA - Montana - Driver's License
- USA - New Hampshire - Identity Card
- USA - New Mexico - Driver's License
- USA - New Mexico - Identity Card
- USA - New York - Identity Card
- USA - North Dakota - Identity Card
- USA - Oregon - Identity Card
- USA - Pennsylvania - Driver's License
- USA - Rhode Island - Identity Card
- USA - South Carolina - Identity Card
- USA - Vermont - Driver's License
- USA - Washington - Identity Card
- USA - Wyoming - Driver's License
- USA - Wyoming - Identity Card
#### Out of Beta
- European Union - Health Insurance Card
#### New Beta Documents Support
- Democratic Republic of the Congo - Voter ID
- Philippines - Alien ID
- Philippines - Postal ID
- Philippines - Work Permit
- Philippines - Health Insurance Card
#### New Document Versions for Beta-Supported Documents
- Honduras - Paper Passport
- Mexico, Sonora - Driver's License
- Peru - Alien ID
- Philippines - Alien ID
#### New Segments Supported on Documents
- China Exit Entry Permit - MRZ
- China Mainland Travel Permit Taiwan - MRZ
- France Residence permit - `Remarks` & `ResidencePermitType`
- Philippines Alien ID - `VisaType`
#### Bug Fixes
- German ID - Fixed issues in cases when documents contain multiple surnames
- Paraguay ID - Added support for parsing the smaller barcode found on the back side of the ID
- Bulgaria Driver’s License - The Address field is set to optional, improving the extraction process
- Improved MRZ parsing for the back side of the following identity documents:
	- Brazil Alien ID
	- Kenya ID
	- Bosnia and Herzegovina ID
	- Ivory Coast ID
	- Paraguay ID
- Fixed `NullPointerException` when using haptic feedback on Android API < 23
- Added missing `ParaguayID` to `IdBarcodeDocumentType`
- Defined the `format` for all attributes to resolve the `style attribute 'attr/mb_helpTooltipBackground not found` error
#### Other changes
- Set `BlinkIdUISettings.setShowMandatoryFieldsMissing` default value to `false`. If you want detailed UI messages about missing mandatory fields, set it to `true`
- Added `CustomClassRules` setting to `BlinkIdSingleSideRecognizer` and `BlinkIdMultiSideRecognizer` to support above mentioned custom mandatory fields
- Exposed `BlinkIdUISettings.setScanUIEventListener`, which can listen for various UI events during the scanning process. Supported events are:
	- scan the barcode message was shown
	- data match dialog was shown
	- document unsupported dialog was shown
	- first side scanning is done
	- second side timeout dialog was shown

## v6.8.0

### New Features
- **Glare detection**
	- We’ve introduced glare detection to BlinkID, which removes occlusion on document images caused by glare. You can control the strictness of glare detection with three options (relaxed, normal, and strict) to suit your use case.
	- Real-time feedback during scanning includes a new UI message to help users position the document correctly and reduce glare.
- **Improved blur detection**
	- We’ve raised the threshold for our blur model, making it stricter. This improvement ensures that sharper images are accepted for processing. You can now control the strictness of blur detection with three options (relaxed, normal, and strict).
	- Real-time feedback during scanning includes a new UI message to help users position the document optimally for a clear image.
- **Support for extracting the subtypes of US driver’s licenses & ID cards**
	- BlinkID now extracts precise information about subtypes of driver’s licenses and ID cards (e.g., conditional driver’s license, learner’s permit, provision, enhanced, etc.). This information is included in the scanning results, allowing you to tailor workflows or processes based on document limitations.
- **Classifier improvements**
	- We upgraded our classifier model to prevent double-capturing of the front side of a document, ensuring the front and the back sides are captured correctly. If the back side of a document is not detected, the processing status will return `UnsupportedClass`.
- **Option to anonymize barcode data**
	- You can now anonymize specific fields in the barcode results from an identity document, in addition to the anonymized fields already supported in the Visual Inspection Zone (VIZ).
- **Accessibility and UI improvements**
	- UI has now been improved to work better with a wide range of system font and UI elements sizes.
### Breaking API changes
- Changes to the `BlinkIdSingleSideRecognizer` and `BlinkIdMultiSideRecognizer` settings:
	- renamed `allowBlurFilter` to `enableBlurFilter`
### Minor API changes
- Added a new result member `documentSubtype` in `BlinkIdSingleSideRecognizer.Result`, `BlinkIdMultiSideRecognizer.Result,` and `VIZResult` to include subtype information for US driver’s licenses or ID cards (commercial, provisional, etc.)
- Changes to the `BlinkIdSingleSideRecognizer` and `BlinkIdMultiSideRecognizer` settings:
	- added `enableGlareFilter` which is set to `true` by default
	- added `blurStrictnessLevel` which is set to `Normal` by default (`Strict`, `Normal`, or `Relaxed`)
	- added `glareStrictnessLevel` which is set to `Normal` by default (`Strict`, `Normal`, or `Relaxed`)
- Changes to the `ImageAnalysisResult`:
	- renamed `glare` to `glareDetected`
	- renamed `blurred` to `blurDetected`
- Changes to the `ClassAnonymizationSettings`:
	- `FieldType` array may now be empty when using Builder pattern
#### Bug Fixes
- Fixed data match for Paraguay ID 2023 to cover inconsistencies between the MRZ and Visual Inspection Zone (VIZ)

## v6.7.0

### New Features
- **Real ID symbol detection on US driver's license**
	- BlinkID can now identify Real ID symbols from US driver’s licenses, providing feedback on their presence or absence. This ensures customers can quickly determine if a Real ID symbol is available on a scanned US driver’s license to be compliant with Real ID regulations.
- **UX Improvements**
	- Extended duration for UI messages
		- UI messages now remain visible for a longer time after scanning, improving user experience. Users can now review post-scanning messages at a comfortable pace, leading to a smoother process.
	- Success indicator for front side capture
		- BlinkID now displays a clear success indicator after scanning the front side of a document. This visual cue enhances user confidence by providing immediate feedback during the scanning process.
- **Partial anonymization of the "Document Number"**
	- To ensure user privacy and security, BlinkID now offers the option of partially anonymizing the document number from the scanned document.
- **Mandatory Barcode Presence on US documents**
	- To minimize the cases of capturing the front side of the document as the back frame in the results, BlinkID now requires the presence of a barcode before saving the back frame on US documents. Processing status `BarcodeDetectionFailed` is returned when the mandatory barcode is not present on the back of US documents.

### Minor API changes
- Added `BarcodeDetectionFailed` as a new `ProcessingStatus`
	- This status is triggered once the barcode was not found on the image. This processing status can only occur if the document has the mandatory barcode.
- Added new boolean member `realIdDetectionStatus` to the `ImageAnalysisResult`. If `true`, Real ID symbol is present, `false` otherwise.
- Added new member `documentNumberAnonymizationSettings` to the `ClassAnonymizationSettings` for seamless integration with the document number anonymization feature.
- Deprecated `DocumentScanUISettings`, please use `BlinkIdUISettings`

### Bug Fixes
- Updated internal mapping for Myanmar passports to display nationality as "Myanmarese" instead of "Burmese" on Myanmar passports.
- On Peru ID `Date of Issue` is marked optional, offering customers more flexibility in capturing ID information.
- Improved `Date of Expiry` handling logic for MRZ in cases where documents with a date of expiry year 1969 were not correctly sanitized.
- Fixed Ping server URL error when using `proguard-android-optimize.txt` default proguard file.

## v6.6.1

- fixed URL of the server performing online license check when it's enabled
  - in v9.1.1 the URL depended on the `BUILD_TYPE` property, pointing to production server only when `BUILD_TYPE` was set to `distribute`. However, apparently the `BUILD_TYPE` is not a compile-time property on Android like it's on other platforms and native code, so it was affected by the setting of the app that was integrating the SDK and that caused the SDK to call to a dev server which is unavailable from the external network
- added android.permission.INTERNET permission to the manifest of LibBlinkCard
  - this permission is needed in order to correctly perform license key validation for licenses that require that

## v6.6.0

### Document Updates

#### New Documents Support:
- Australia - Polycarbonate Passport
- Indonesia - Polycarbonate Passport
- Mexico - Consular Voter ID
- Moldova - Polycarbonate Passport
- Pakistan - Proof Of Registration
- Panama - Polycarbonate Passport
- USA - West Virginia - ID Card


#### New Beta Documents Support:
- Brazil - Ceara - ID Card
- Brazil - Goias - ID Card
- Brazil - Sergipe - ID Card
- China - Exit Entry Permit
- China - Mainland Travel Permit Taiwan
- Colombia - Temporary Protection Permit
- India - DL
- India - Andhra Pradesh - DL
- India - Haryana - DL
- European Union - Health Insurance Card


#### New Document Versions for Supported Documents:
- Australia - Queensland - DL
- Australia - Victoria - DL
- Australia - Western Australia - DL
- Bolivia - Minor's ID
- Brazil - Alien ID
- Estonia - DL
- Finland - Alien ID
- Guatemala - Paper Passport
- India - Paper Passport - New side type
- Malta - DL
- Mexico - Guanajuato - DL 
- Mexico - San Luis Potosi - DL
- New Zealand - Polycarbonate Passport
- Paraguay - ID Card
- Slovenia - Residence Permit
- USA - Green Card
- USA - Work Permit
- USA - Arizona - ID Card
- USA - Colorado - DL
- USA - Colorado - ID Card
- USA - District of Columbia - DL
- USA - District of Columbia - ID Card
- USA - Idaho - DL
- USA - Idaho - ID Card
- USA - Missouri - ID Card
- USA - Nebraska - ID Card
- USA - Nevada - ID Card
- USA - New York - ID Card
- USA - North Dakota - DL
- USA - Oklahoma - ID Card
- USA - Pennsylvania - ID Card
- USA - Utah - ID Card
- USA - Virginia - DL
- USA - Virginia - ID Card
- USA - West Virginia - DL
- USA - Wisconsin - DL
- USA - Wisconsin - ID Card


#### New Document Versions for Beta Supported Documents:
- Australia - Queensland - Proof of Age Card
- Brazil - ID Card
- Ireland - Residence Permit
- Mexico - Consular ID
- Mexico - Residence Permit
- Mexico - Nayarit - DL
- USA - North Dakota - ID Card


#### Out of Beta:

- Croatia - Residence Permit
- Moldova - Paper Passport

### Platform Updates

- Expanded geographic support with new `Region` enum values:
  - `ANDHRA_PRADESH`
  - `CEARA`
  - `GOIAS`
  - `GUERRERO_ACAPULCO_DE_JUAREZ`
  - `HARYANA`
  - `SERGIPE`   

- Document recognition enhanced with new `Type` enum values:
  - `EXIT_ENTRY_PERMIT`
  - `MAINLAND_TRAVEL_PERMIT_TAIWAN`
  - `NBI_CLEARANCE`
  - `PROOF_OF_REGISTRATION`
  - `TEMPORARY_PROTECTION_PERMIT`

- UI flexibility:
  - Introducing optional Torch button and cancel button through `BlinkIdUISettings` (`setShowTorchButton` and `setShowCancelButton`) and `ReticleOverlayView`.

- Data extraction improvements:
  - Added `Sponsor` and `BloodType` result fields to `BlinkIdMultiSideRecognizer.Result`, `BlinkIdSingleSideRecognizer.Result` and `VizResult`
 
#### Accessibility Changes:
- Refined UI to meet required contrast ratios, ensuring better readability for all users.
- Clickable button areas have been enlarged to comply with accessibility standards, providing easier interaction and ensuring that users with motor impairments can comfortably use the interface.
- Haptic feedback has been introduced throughout the scanning flow, highlighting important events and providing tactile confirmation to users

#### Deprecated Functionality:
- `IdBarcodeRecognizer` is now marked as deprecated. We recommend transitioning to `BlinkIdMultiSideRecognizer`, which not only covers the functionality of `IdBarcodeRecognizer` but also offers additional features.


## v6.5.1
- Improved scanning of Bolivia IDs by addressing cases where the expiration date is covered by a signature, allowing the completion of the scanning process.

## v6.5.0
- Improved the document side detection for US driver licenses which reduces the possibility of capturing the wrong side of the document. Barcode is now mandatory for the document back side of the US driving licenses

### Bugfixes 
- Fixed data matching issue in Guatamala Passport
- Fixed `ArrayIndexOutOfBoundsException` in default UI
- Fixed `NullPointerException` in `RecognizerRunnerView` that happened in rare cases

## v6.4.0
### New features
- Added new result member `cardRotation` to `ImageAnalysisResult` structure
- New `CardOrientation` status - `NotAvailable` 
- New `Region` enum values:
		- `DISTRITO_FEDERAL`
		- `MARANHAO`
		- `MATO_GROSSO`
		- `MINAS_GERAIS`
		- `PARA`
		- `PARANA`
		- `PERNAMBUCO`
		- `SANTA_CATARINA`
- New `Type` enum values:
		- `CONSULAR_VOTER_ID`
		- `TWIC_CARD`

### Bugfixes
- Fixed the issue where the wrong values of `DetectionStatus` were returned and wrong instruction messages were shown. This is also a breaking change because the names and the order of the enum values have changed.
- Fixed `toString` method of `Date` and `DateResult`

### Added support for 8 new documents
- Bolivia - Paper Passport
- Brazil - Alien ID
- Brazil - Distrito Federal - Identity Card
- Brazil - Minas Gerais - Identity Card
- Cameroon - Polycarbonate Passport
- Costa Rica - Paper Passport
- UK - Proof of Age Card
- USA - Twic Card

### Added support for 17 new documents in BETA
- Australia - Australian Capital Territory - Identity Card
- Australia - Queensland - Proof of Age Card
- Australia - Tasmania - Identity Card
- Australia - Western Australia - Identity Card
- Bahrain - Driver’s License
- Bahrain - Paper Passport
- Brazil - Maranhao - Identity Card
- Brazil - Mato Grosso - Identity Card
- Brazil - Para - Identity Card
- Brazil - Parana - Identity Card
- Brazil - Pernambuco - Identity Card
- Brazil - Santa Catarina - Identity Card
- Cameroon - Paper Passport
- Mexico - Quintana Roo, Benito Juarez - Driver’s License
- Mexico - Quintana Roo -Driver’s License
- Switzerland - Polycarbonate Passport
- UAE - Polycarbonate Passport

### Added support for 14 new versions of already supported documents
- Brazil - Driver’s License
- Luxembourg - Identity Card
- North Macedonia - Identity Card
- Portugal - Driver’s License
- Slovakia - Identity Card
- Switzerland - Identity Card
- Switzerland - Driver’s License
- Turkey - Polycarbonate Passport
- Ukraine - Driver’s License
- USA - Arizona - Driver’s License
- USA - Arizona - Identity Card
- USA - Nebraska - Driver’s License
- USA - Pennsylvania - Identity Card
- USA - Virginia - Driver’s License

### Added support for 2 new versions of already supported documents in BETA
- Iceland - Paper Passport
- Mexico - Consular ID

## v6.3.0
### New features
- Changes to the `BlinkIdSingleSideRecognizer` and `BlinkIdMultiSideRecognizer`:
  - add new methods inside `StringResult`:
    - `location(AlphabetType)` - location coordinates of every non-empty result are available
    - `side (AlphabetType)` - document side of every non-empty result is available
  - new results in `Recognizer.Result`:
    - `faceImageLocation`
    - `faceImageSide`
- Add new `Country` enum value - `SCHENGEN_AREA`
- And new `Type` enum value - `IMMIGRANT_VISA`
- Add Builder pattern to `ClassAnonymizationSettings` class for easier Anonymization settings setup

### Bugfixes
- Fix for Anonymization settings being set to default (all) when null values for arguments are selected
- Fix crashing after device rotation when using `OneSideDocumentScan` and `TwoSideDocumentScan` implementations
- Remove autofill on `Russian` passport `DateOfExpiry` when present on a document
- Improved scanning success rate and stability when using `BlinkIdMultiSideRecognizer`

## v6.2.2
- Fixed the issue with reading the back side of the USA/Washington driving license and ID documents.

## v6.2.1
- Fixed setting license key with dynamic licensee
- Bugfixes

## v6.2.0
### New features
- new and improved machine learning models for data extraction
- expanded support for arabic documents
- added `isFilledByDomainKnowledge` flag to `Date` and `DateResult`
	- indicates that date is not extracted from image but filled based on our internal document knowledge
- added new setting `additionalAnonymization`
	- enables custom anonymization for any field per country, region and type of document
- added new items to enums:
	- Region:
		- NORTHWEST_TERRITORIES (added item to enum, no document support for NORTHWEST_TERRITORIES yet)
		- NUNAVUT (added item to enum, no document support for NUNAVUT yet)
		- PRINCE_EDWARD_ISLAND
	- Type:
		- ASYLUM_REQUEST
		- DRIVER_QUALIFICATION_CARD
		- PROVISIONAL_DL
		- REFUGEE_PASSPORT
		- SPECIAL_ID
		- UNIFORMED_SERVICES_ID
	- FieldType:
		- BloodType
		- Sponsor
- added more UI customization options
  - For more information, see [README](README.md) in the **Scan overlay theming** section
- added haptic effect feedback to the scanning process
  - error, first side success, and overall scan success actions provide different haptic feedback. This option is enabled by default, it can be disabled by using `BlinkIdUISettings.setAllowHapticFeedback(false)`
- added missing mandatory fields feedback during scanning
  - specific missing fields are shown if there are less than two, otherwise a general message is presented. This option is enabled by default, it can be disabled by using `BlinkIdUISettings.setShowMandatoryFieldsMissing(false)` which always shows a general message
- new onboarding screens based on recognizer type (Viz, MRZ, Barcode)
- improved scanning reticle animations and transitions

### Added support for 9 new documents
- Belarus - ID Card
- Guyana - ID Card
- Jamaica - Paper Passport
- Myanmar - Paper Passport
- Palestine - Paper Passport
- Saint Kitts and Nevis - Driving License
- Syria - ID Card
- Trinidad and Tobago - Paper Passport
- USA - Uniformed Services ID Card

### Added support for 23 new documents in BETA
- Barbados - Driving License
- Belarus - Polycarbonate Passport
- Belarus - Residence Permit
- Belgium - Provisional Driving License
- Belgium - Special ID Card
- Bulgaria - Alien ID Card
- Bulgaria - Residence Permit
- Canada - New Brunswick - ID Card
- Canada - Prince Edward Island - Driving License
- Estonia - Polycarbonate Passport
- Germany - Driver Qualification Card
- Guyana - Driving License
- Kuwait - Paper Passport
- Lebanon - Paper Passport
- Liechtenstein - Driving License
- Malta - Paper Passport
- Malta - Polycarbonate Passport
- Moldova - Driving License
- Netherlands - Alien ID Card
- Oman - Paper Passport
- Peru - Alien ID Card
- Romania - Residence Permit
- UK - Asylum Request

### Added support for 28 new versions of already supported documents
- Canada - British Columbia - Public Services Card
- Canada - British Columbia - Driving License
- Cyprus - Residence Permit
- Denmark - Polycarbonate Passport
- Germany - ID Card
- Italy - ID Card
- Ireland - Passport Card
- Malta - ID Card
- Montenegro - ID Card
- Montenegro - Polycarbonate Passport
- North Macedonia - ID Card
- North Macedonia - Polycarbonate Passport
- Norway - Driving License
- Norway - Residence Permit
- Norway - Polycarbonate Passport
- Philippines - Driving License
- Sweden - Polycarbonate Refugee Passport
- Sweden - Social Security Card
- UAE - Resident ID Card
- UK - Proof of Age Card
- USA - Arkansas - ID Card
- USA - Colorado - ID Card
- USA - Idaho - ID Card
- USA - Illinois - ID Card
- USA - New York - Driving License
- USA - New York - ID card
- USA - Pennsylvania - Driving License
- USA - Washington - Driving License

### Added support for 3 new versions of already supported documents in BETA
- Denmark - Residence Permit
- Estonia - Residence Permit
- Latvia - Residence Permit

## v6.1.2
- Fixed flash icon on orientation change while scanning - the behavior of the icon is now consistent with the behavior of the torch
- Fixed ImageDpi setting when setting the processed image quality through recognizer settings

## v6.1.1
- Internal improvements

## v6.1.0
### New features
- new and improved machine learning models for data extraction
- further improved barcode scanning (parsing for non-standard US DLs)
- added anonymization for QR code on Dutch ID card
- anonymization of religion field on Malaysian documents
- updated reticle UI and animations flow

### Added support for 9 new documents:
- Australia - Victoria - Proof of Age Card
- Brazil - Rio de Janeiro - ID Card
- Liechtenstein - ID Card
- Luxembourg - Passport
- Mozambique - ID Card
- Norway - ID Card
- Togo - ID Card
- USA - Wyoming - ID Card
- Zimbabwe - ID Card

### Added support for 9 new documents in BETA:
- Barbados - ID Card
- Belgium - Passport
- Brazil - Rio Grande do Sul - ID Card
- Ireland - Residence Permit
- Japan - Residence Permit
- Lithuania - Residence Permit
- Saint Lucia - ID Card
- USA - New Hampshire - ID Card
- USA - South Dakota - ID Card

### Added support for 34 new versions of already supported documents:
- Belgium - Resident ID Card
- Canada - Residence Permit
- Estonia - ID Card
- Finland - Alien ID Card
- Finland - ID Card
- Latvia - Alien ID Card
- Lithuania - ID Card
- Luxembourg - ID Card
- Malta - Residence Permit
- Netherlands - ID Card
- Netherlands - Residence Permit
- Poland - ID Card
- Sweden Residence Permit
- USA - Alabama - ID Card
- USA - Alaska - ID Card
- USA - Colorado - Driving License
- USA - Connecticut - ID Card
- USA - District of Colombia - Driving License
- USA - District of Colombia - ID Card
- USA - Iowa - ID Card
- USA - Kansas - ID Card
- USA - Louisiana - Driving License
- USA - Maine - Driving License
- USA - Maine - ID Card
- USA - Minnesota - ID Card
- USA - Mississippi - ID Card
- USA - Nevada - Driving License
- USA - New York - Driving License
- USA - South Carolina - ID Card
- USA - South Dakota - Driving License
- USA - Texas - ID Card
- USA - Vermont - Driving License
- USA - Washington - ID Card
- USA - Wisconsin - Driving License

### Added support for 2 new versions of already supported documents in BETA:
- Poland - Residence Permit
- Portugal - Residence Permit

## v6.0.0
### New features:
#### Extracting ID data from Arabic and Cyrillic IDs
- When we say you can scan IDs across the globe, we really mean it. Our [list of supported](https://microblink.com/full-list-of-supported-identity-documents/) documents got richer now that we can scan IDs including Arabic and Cyrillic scripts. This fantastic new feature also covers multiscript IDs meaning that we extract data written in both Latin and Arabic data fields.

#### Better barcode scanning and data extraction

- We’ve improved scanning accuracy for all IDs that hold a PDF417 or other barcode types. This means that the error rate is now 20% lower, which brings even cleaner and more reliable data with every scan of an identity document. 

#### New user instructions that lead to successful scans
- More detailed instructions on how to scan identity documents, via an intro tutorial or tooltip during scanning, leading to improved success rates in ID scanning and data extraction.

#### In-depth error messaging for developers
- You asked, we listened! With v6, you’ll get more detailed error messages regarding end-user activity, to better understand if scans should be repeated or accepted.

#### Easier implementation
- Less code, less headaches! You can now implement basic Identity Document Scanning in a single line of code.


### What's new in the BlinkId(Combined) Recognizer?

- Renamed `BlinkIdRecognier` to **BlinkIdSingleSideRecognizer**
- Renamed `BlinkIdCombinedRecognizer` to **BlinkIdMultiSideRecognizer**
- We introduced new classes: `StringResult`, `DateResult`, and `Date` in order to support multiple alphabets. If a recognizer supports multiple alphabets, its result class (e.g., `BlinkIdMultiSideRecognizer.Result`) will return `StringResult` for results that previously returned `String`.
- Added new result property of an `AdditionalProcessingInfo` type that provides information about `missingMandatoryFields`, `invalidCharacterFields`, and `extraPresentFields`
- Unified `DataMatchResult` and `DataMatchDetailedInfo` into a single structure `DataMatchResult` (removed `dataMatchDetailedInfo` result member)
- Added new result member `cardOrientation` to `ImageAnalysisResult` structure
- More info about transitioning to **BlinkID v6.0.0** can be found in [`Transition guide.md`](Transition guide.md) 

### Added support for 50 new versions of already supported documents:
- Mexico - Aguascalientes - Driving license
- Mexico - Baja California - Driving license 
- Mexico - Hidalgo - Driving license
- USA - Delaware - Driving license
- USA - Florida - ID card
- USA - Hawaii - Driving license
- USA - Kentucky - ID card
- USA - Maryland - ID card
- USA - Michigan - ID card
- USA - Mississippi - Driving license
- USA - Mississippi - ID card
- USA - Missouri - ID card
- USA - Ohio - ID card
- USA - Oklahoma - ID card
- USA - Rhode Island - Driving license 

### These documents are no longer BETA:
- Australia - New South Wales - ID card
- Australia - South Australia - Proof of Age Card 
- Belgium - Minors ID card
- Belgium - Passport
- Canada - Tribal ID card
- Canada - Weapon Permit
- Canada - British Columbia - Minors Public Services Card
- Ireland - Public Services Card
- Israel - ID card
- Ivory Coast - Driving License
- Lebanon - ID card
- Libya - Polycarbonate Passport 
- Mexico - Colima - Driving license
- Mexico - Michoacan - Driving license
- Mexico - Tamaulipas - Driving license 
- Mexico - Zacatecas - Driving license
- Myanmar - Driving license
- Panama - Temporary Residence Permit
- Slovenia - Residence Permit
- Trinidad and Tobago - Driving license
- USA - Passport
- USA - Maine - ID card

### Added support for 2 new ID types in BETA:
#### Latin America and the Caribbean
- Cuba - ID card
- Cayman Islands - Drivers License

### Already supported documents but now with option to enable extraction for new scripts
#### Arabic - 5 ID types
- Egypt - ID card
- Jordan - ID card
- UAE - ID card
- UAE - Passport
- UAE - Resident ID

#### Cyrillic - 10 ID types
- Bosnia and Herzegovina - ID card
- Bulgaria - Drivers License
- Bulgaria - ID card
- North Macedonia - Drivers License
- North Macedonia - ID card
- Serbia - ID card
- Ukraine - Drivers License
- Ukraine - ID card
- Ukraine - Residence Permit
- Ukraine - Temporary Residence Permit


## 5.20.0

### New feature:
- ML models with new architecture that result in further 8% decrease in error rate

### Support for 8 new document types:
#### Northern America
- USA - Polycarbonate Passport 
- USA - Nebraska - ID Card
- USA - New York - ID Card
- USA - Utah - ID Card

#### Latin America and the Caribbean
- Mexico - Polycarbonate Passport
- Brazil - Sao Paolo - ID Card

#### Europe 
- Austria - Residence Permit

#### Asia
- Philippines - ID Card

### Back side support added:
- Australia - South Australia - Driving license

### Added support for 29 new versions of already supported documents:
- Australia - Northern Territory - Proof of Age Card
- Belgium - Minors ID Card
- Belgium - Residence Permit
- Bolivia - ID Card
- Croatia - Residence Permit
- Cyprus - ID Card
- Czechia - ID card
- Czechia - Residence Permit
- Dominican Republic - Paper Passport
- Greece - Residence Permit
- Italy - Residence Permit
- Ivory Coast - Driving license
- Kuwait - Driving license
- Mexico - Jalisco - Driving license
- Mexico - Nuevo Leon - Driving license
- Peru - ID Card
- Poland - Driving license
- Slovenia - ID Card
- Sweden - ID Card
- Sweden - Polycarbonate Passport
- USA - Georgia - ID Card 
- USA - Iowa - ID Card
- USA - Kansas - Driving license
- USA - Maryland - ID Card
- USA - Nebraska - ID Card
- USA - New York - Driving license
- USA - New York - ID Card
- USA - Oklahoma - Driving license
- Vietnam - ID Card

### These documents are no longer BETA:
- Finland - Residence Permit
- Guatemala - Driving license

### Added support for 2 new ID types in BETA:
- Antigua and Barbuda - Driving license
- Mexico - Professional ID Card

### Changes to BlinkID(Combined) Recognizer
- ClassInfo:
	- Added to JSON serialization:
		- `isoNumericCountryCode`
		- `isoAlpha2CountryCode`
		- `isoAlpha3CountryCode`

- BarcodeData:
	- JSON serialization update: `stringData` member is now in Base64 format

- Added new item to enums:
	- Region: 
		- Sao Paulo, when scanning Brazilian Driving licenses

- Fixed scanning for Argentina ID - there were confusions for Veteran ID, now we enabled successful extraction on Veteran ID as well

## 5.19.0

What's new in the BlinkID(Combined) Recognizer?

### Support for atypical Vietnam passports
- Extract data from Vietnam Passports that have non-ICAO compliant MRZ fields. For example, when the filler arrow is facing the other way (>) instead of the standard way (<)

### Data anonymization
- We've added the option to not extract the religion field on all supported Malaysian documents (MyKad, MyKas, MyKid, MyPR, MyTentera)

### Bugfixes
- Resolved issues with RGB color overlay while extracting document image, which was present on some devices
- Fixed issue with Motorola Moto E40 camera - Camera 2 API wasn’t working

### Minor API breaking change
- We have changed the DirectAPI recognize methods to make it easier to understand which one you should be using. If you want to process:
 - **Still** Android `Bitmaps` (for example, obtained from the gallery), use `recognizeBitmap` or `recognizeBitmapWithRecognizers`.
 - **Video** `Images` that are built from custom camera video frames (for example, when you use your own or third-party camera management), use `recognizeVideoImage` or `recognizeVideoImageWithRecognizers`. Recognition will be optimized for speed and will rely on time-redundancy between consecutive video frames in order to yield the best possible recognition result.
 - **Still** `Images` which are not part of a video stream and you want to get the best possible results from the single `Image` use `recognizeStillImage` or `recognizeStillImageWithRecognizers`. Image type comes from our SDK or it can be created by using ImageBuilder.

## 5.18.0

### New feature:
- Updated machine learning models resulting in a 41% reduced error rate.

### Support for 16 new document types:
#### Northern America
- USA - Passport Card
- USA - District of Columbia - ID Card
- USA - Iowa - ID Card
- USA - Tennessee - ID Card

#### Latin America and the Caribbean
- Cuba - Paper Passport
- Dominican Republic - Paper Passport
- Panama - Residence Permit (front only)
- Peru - Paper Passport

#### Europe 
- Cyprus - Paper Passport
- Germany - Minors Passport
- UK - Proof of Age Card (front only)
- Ukraine - Residence Permit
- Ukraine - Temporary Residence Permit

#### The Middle East and Africa
- Qatar - Paper Passport
- UAE - Paper Passport

#### Oceania
- Australia - Northern Territory - Proof of Age Card

### Back side support added:
- Austria - ID Card
- Australia - South Australia - Driving license
- Australia - Tasmania - Driving license
- Canada - Quebec - Driving license
- Mexico - Quintana Roo Solidaridad - Driving license
- USA - Washington - Driving license

### Added support for 26 new versions of already supported documents:
- Afghanistan - ID Card
- Bahrain - ID Card
- Hungary - Residence Permit
- India - ID Card
- Mexico - Tabasco - Driving license
- New Zealand - Driving license (front only)
- The Philippines - Professional ID (front only)
- Slovakia - Residence Permit
- South Africa - ID Card
- Switzerland - Residence Permit
- UK - Driving license 
- USA - Colorado - Driving license 
- USA - Idaho - Driving license 
- USA - Kansas - ID Card 
- USA - Kentucky - Driving license 
- USA - Maine - Driving license 
- USA - Massachusetts - ID Card 
- USA - Nebraska - Driving license 
- USA - New Hampshire - Driving license 
- USA - New Jersey - ID Card 
- USA - New Mexico - ID Card 
- USA - North Carolina - ID Card 
- USA - Utah - Driving license 
- USA - Vermont Driving license 
- USA - West Virginia - Driving license 

### These documents are no longer BETA:
- Algeria - Paper Passport
- Slovakia - Residence Permit
- USA - Mississippi - ID Card

### Added support for 8 new ID types in BETA:
- Iceland - Paper Passport
- South Africa - ID Card (front only)
- Brazil - Consular Passport (beta)
- Quintana Roo Cozumel - Driving license 
- Canada - Social Security Card (front only)
- Canada - British Columbia - Minor Public Services Card
- USA - Maine - ID Card
- USA - North Dakota - ID Card


### Changes to BlinkID(Combined) Recognizer
- Added new enums:
	- Region: `QUINTANA_ROO_COZUMEL` 
	- Type: `CONSULAR_PASSPORT`, `MINORS_PASSPORT`, and `MINORS_PUBLIC_SERVICES_CARD`

## 5.17.0

### New features:
- We've added support for launching the scan activity via the new Activity Result API. Check out our sample to see how to implement it.

### Changes to BlinkID(Combined) Recognizer
- Introduced the expanded DataMatch functionality for the BlinkID with the new result member called `dataMatchDetailedInfo`
	- This result member will enable you to see for which field has been performed, or it did not, the DataMatch functionality. This is enabled for `dateOfBirth`, `documentNumber` and `dateOfExpiry`.
	- For example, if the date of expiry is scanned from the front and back side of the document and values do not match, this method will return DataMatchResult: `Failed`.
Result will be DataMatchResult: `Success` only if scanned values for all fields that are compared are the same. If data matching has not been performed, the result will be DataMatchResult: `NotPerformed`. This information is available for every of the three mentioned field values above.

- Fixed issues with scanning Argentina AlienID, where there were confusions with the regular ID. `ClassInfo` now correctly returns which ID type is present based on the barcode data.

## 5.16.1

### Fixes
- Bugfix for extracting data from the USA Minnesota DL barcode

## 5.16.0

### We've added new documents to our list of supported documents:

#### Europe
- Austria - ID Card (front only)
- Germany - ID Card

#### Latin America and the Caribbean
- Brazil - ID Card (beta)
- Colombia - ID Card (front only)
- Ecuador - ID Card

#### Mexico
- Baja California Sur - Driving Licence (beta)
- Ciudad De Mexico - Driving Licence (front only)
- Colima - Driving Licence (front only, beta)
- Michoacan - Driving Licence (beta)
- Nayarit - Driving Licence (beta)
- Quintana Roo Solidaridad - Driving Licence (front only)
- Tlaxcala - Driving Licence
- Veracruz - Driving Licence (beta)

#### Oceania
- Australia - Northern Territory (beta)

#### Asia
- Japan - My Number Card (front only)
- Singapore - Resident ID

#### Northern America
- USA - Missouri - ID Card
- USA - Nevada - Driving Licence
- USA - New York City - ID Card
- USA - Oklahoma - ID Card

#### Back side support added:
- Mexico - Chiapas - Driving License 

#### No longer BETA:
- Mexico - Baja California - Driving Licence
- Mexico - Chihuahua - Driving Licence
- Mexico - Coahuila - Driving Licence
- Mexico - Guanajuato - Driving Licence
- Mexico - Mexico - Driving Licence


### Changes to BlinkID(Combined) Recognizer

- Added the setting `saveCameraFrames` for saving camera frames with the default value being `false`. Note that the memory consumption is a bit bigger if this setting is set to `true`.
	- New result members are available for: 
		- BlinkIDRecognizer:`cameraFrame` and `barcodeCameraFrame`.
		- BlinkIDCombinedRecognizer:`frontCameraFrame`, `backCameraFrame`, and `barcodeCameraFrame`.
- We've added new result members when scanning Australian Driving Licences: `vehicleClass`, `licenceType`, `effectiveDate` and `expiryDate`.
	- Result members are displayed under the `VehicleClassInfo` field; we can also extract data from multiple rows when this vehicle class info data is present on the document (e.g. multiple expiry dates for different vehicle classes).
- We've added new enum values:
	- Region: `QUINTANA_ROO`, `QUINTANA_ROO_SOLIDARIDAD`, `TLAXCALA` which are available when scanning Mexican Driving Licences.
	- Type: `MY_NUMBER_CARD` which is available when scanning Japanese My Number Card documents.
- We've added new result member `additionalOptionalAddressInformation` which gives additional address information about the document owner.
	- This result member can be present when scanning the Pakistani ID Card for the field `Country of Stay`.
- We've removed 2 recognizers: `GermanyDLBack Recognizer` and `SingaporeChangiEmployeeId Recognizer`.

	
#### Changes to IDBarcodeRecognizer
- For barcodes in countries: Argentina, Colombia, Nigeria, Panama, and South Africa, we now also extract data from the field `Sex` when it's populated with the character "X".

 
### Improvements
- We've added support for Brazil ID Card when the cardholder's face image is rotated for 90 degrees on the document.
	- We will return face image and document image + data from VIZ part present on the back side.
- `SuccessFrameGrabber` setting is now called `saveCameraFrames` and it can return the full frame from the front side now when using BlinkIdCombinedRecognizer, whereas before it returned the full frame only for the back side.


## 5.15.2

### Visual identity update
- We’ve updated Microblink logo and colors

### Fixes
- Fixed crash on some devices (mostly Huawei) when opening camera if SDK was used in full screen mode


## 5.15.0

### Newly added versions of documents for already supported documents
We’ve added 24 new  documents to our list:

### Europe
- Belgium - Driving License (front only)
- Croatia - ID Card
- France - ID Card
- France - Residence Permit (beta)
- Spain - ID Card
- Switzerland - Residence Permit
- UK - Residence Permit

### Oceania
- Australia - Northern Territory - Driving License (front only, beta)

### Middle East and Africa
- UAE - ID Card
- UAE - Resident ID

### Northern America
- Honduras - ID Card (beta)
- USA - Colorado - ID Card 
- USA - Minnesota - Driving License
- USA - Nevada - Driving License
- USA - Oklahoma - Driving License
- USA - Wyoming - Driving License


### Improvements
- `DataMatch` functionality is now enabled for single side documents (Passports)
	- Added a special case to support `DataMatch` for UAE ID Card and Resident ID Card documents for the field `personalIdNumber`
- We can now extract `additionalPersonalIdNumber` on Ecuador ID Card
- Improvements for reading NRIC number on Malaysian documents that have an asterisk (*) character present 
- Improved document detection and cropping of the document image
 
### Changes to USDLRecognizer
- Fixed magneticStripeParser crashing
- Calling originalString on dates coming from USDLRecognizer will now return exact actual strings dates were parsed from instead of custom reformatted strings

### Changes to IDBarcodeRecognizer
- Added document type ArgentinaAlienID and parser for ArgentinaAlienID2012BarcodeParser

### Changes to MRTDRecognizer
- Added support for parsing Dominican Republic ID Card, Senegal ID Card and Ecuador ID Card
- Renamed MRTDDocTypeSmallIDPakistan to MRTDDocTypeSmallIDPakistanConsular
- Bugfixes for parsing:
	- Mexico Professional ID
	- Croatia Driving License


## 5.14.0
### Back side support added:
- Thailand - ID Card

### Changes to BlinkID(Combined) Recognizer
- Added new result members - `fathersName` and `mothersName` both in BlinkID and BlinkIDCombined Recognizers, as well as in VIZ result

### Improvements
- We can now extract `fathersName` and `mothersName` from Mexico Voter ID Card
- Australian Driving Licenses for New South Wales, Northern Territory, Queensland, Victoria and Western Australia now have the driver license unique card number field extracted as `document_additional_number`

### Changes to BarcodeRecognizer
- We’ve removed support for `aztec` and `dataMatrix` barcode formats from BarcodeRecognizer

### Changes to MRTDRecognizer
- Added `MRTD_TYPE_BORDER_CROSSING_CARD` to MrtdDocumentType enum 

### Fixes
- Fixed rare NullPointerException that happened only on some devices, caused by a lifecycle issue where the camera error callback was called after the view has already been destroyed

### Minor breaking API change
- We've changed how `RecognizerRunner` processes images. Now it can treat `Image` objects as either video frame or photo frame. Until now, Direct API always processed images as photo frames, that not giving ability to recognizers to use time-redundant information for yield better recognition quality.


## 5.13.0
### New additions to our supported document list

We’ve added 61 new documents:

#### Europe
- Austria - Paper Passport
- Belarus - Paper Passport
- Belgium - Paper Passport (beta)
- Bulgaria - Paper Passport
- Estonia - Paper Passport
- France - Paper Passport (beta)
- Georgia - Paper Passport (beta)
- Germany - Paper Passport
- Greece - Paper Passport
- Hungary- Paper Passport
- Italy - Paper Passport (beta)
- Kosovo - Paper Passport
- Moldova - Paper Passport (beta)
- Poland - Paper Passport
- Portugal - Paper Passport
- Spain - Paper Passport
- Switzerland - Paper Passport
- UK - Paper Passport

#### Middle East and Africa
- Algeria - Paper Passport (beta)
- Egypt - Paper Passport (beta)
- Eswatini - Paper Passport
- Ghana - Paper Passport
- Iran - Paper Passport (beta)
- Iraq - Paper Passport (beta)
- Israel - Paper Passport (beta)
- Jordan - Paper Passport (beta)
- Kenya - Polycarbonate Passport
- Libya - Polycarbonate Passport (beta)
- Morocco - Paper Passport (beta)
- Nigeria - Paper Passport
- Nigeria - Polycarbonate Passport (beta)
- Qatar - ID Card (front only, beta)
- Saudi Arabia - Paper Passport
- Syria - Paper Passport
- Tanzania - ID Card (beta)
- Tanzania - Voter ID (front only, beta)
- Tunisia - Paper Passport
- Turkey - Paper Passport
- Zimbabwe - Paper Passport

#### Latin America and the Caribbean
- Argentina - Paper Passport
- Brazil - Paper Passport (beta)
- Guatemala - Paper Passport
- Haiti - Paper Passport
- Honduras - Paper Passport (beta)
- Mexico - Paper Passport (beta)
- Mexico - Nayarit - Driving Licence (beta)

#### Asia
- Bangladesh - Paper Passport
- China - Paper Passport (beta)
- India - Paper Passport
- Indonesia - Paper Passport
- Japan - Paper Passport
- Nepal - Paper Passport
- Pakistan - Paper Passport
- Philippines - Paper Passport
- South Korea - Paper Passport (beta)
- Sri Lanka - Paper Passport
- Uzbekistan - Paper Passport

#### Oceania
- Australia - Paper Passport

#### Northern America
- Canada - Paper Passport
- Canada - Weapon Permit (front only, beta)
- USA - Paper Passport (beta)

#### Back side support added:
- Greece - ID Card
- Burkina Faso - ID Card
- Democratic Republic of the Congo - Driving Licence
- Mexico - Veracruz - Driving Licence
- Canada - Citizenship Certificate

#### No longer BETA:
- Belarus - Driving Licence
- UK - Polycarbonate Passport
- Argentina - Alien ID
- Bahamas - Driving Licence
- Mexico - Durango - Driving Licence
- Venezuela - ID Card
- USA - Kansas - ID Card

### Changes to BlinkID(Combined) Recognizer
- We’ve renamed the Swaziland country to Eswatini in results and ClassInfo
- Improved result validation
    - `FieldIdentificationFailed` processing status is used to indicate if unexpected fields are present on the document. Those fields are then deleted from the result
- We are filling out COUNTRY and REGION fields in ClassInfo, without the field TYPE of document, when using BarcodeID mode for scanning documents where the Front side is not supported, and back side results are extracted from AAMVA compliant barcodes
    - This applies only if `ClassInfo` isn’t already prepopulated in some other way and when you’re not in `FullRecognition` mode

### Improvements
- We can now extract the date of birth from the document number on the South Korean identity card and from the personal identification number on the driving licence

### Anonymization
- We’ve added anonymization support for new documents:
    - Document number on Germany paper bio-data page Passport
    - Document number on South Korea Identity Card
    - Personal identification number on South Korea driving licence
    - Personal identification number on South Korea paper bio-data page Passport

### Fixes
We’ve fixed camera issues for the following devices:
	- Motorola Moto G100 - Camera 2 API wasn’t working
	- Realme X50 5G - Problem with Camera 2 API in legacy mode
	- LG K4 - Camera 2 API wasn’t working
	- Galaxy Tab Active 2 - Aspect ratio was broken


## 5.12.0.
### We've added 15 new documents to our list of supported documents:

#### Europe
- North Macedonia - Polycarbonate Passport

#### Middle East and Africa
- Botswana - ID Card
- Sudan - Polycarbonate Passport

#### Mexico
- Baja California Sur - Driving License (beta)
- Campeche - Driving License (beta)
- Colima - Driving License (beta)

#### Oceania
- Australia - Health Insurance Card (front only, beta)

#### Asia
- Azerbaijan - Polycarbonate Passport (beta)
- Tajikistan - Polycarbonate Passport (beta)

#### Northern America
- Canada - Citizenship Certificate (front only, beta)
- Canada - Ontario - Health Insurance Card (front only)
- Canada - Quebec - Health Insurance Card (front only, beta)
- USA - Military ID Card
- USA - Rhode Island - ID Card
- USA - South Carolina - ID Card

#### Back side support added:
- Ireland - Passport Card
- Mexico - Puebla - Driving License
- Singapore - S PASS

#### No longer BETA:
- Finland - Polycarbonate Passport
- Ireland - Passport Card
- Ireland - Polycarbonate Passport
- Kosovo - Driving License
- Latvia - Polycarbonate Alien Passport
- Latvia - Polycarbonate Passport
- Poland - Polycarbonate Passport
- Cameroon - ID Card
- Ghana - ID Card
- Iraq - ID Card
- Tanzania - Driving License
- Turkey - Polycarbonate Passport
- Uganda - Driving License
- Bolivia - Minors ID
- Chile - Driving License
- Ecuador - Driving License
- Haiti - Driving License
- India - Karnataka - Driving License
- India - Maharashtra - Driving License
- Pakistan - Punjab - Driving License
- USA - Global Entry Card
- USA - New Mexico - ID Card
- USA - Wisconsin - ID Card

### Changes to BlinkID(Combined) Recognizer

- We've added the parameter `maxAllowedMismatchesPerField` to settings. When this is set to a non-zero value, DataMatch will be successful as long as the number of mismatched characters doesn't exceed the specified value.
- We've added the parameter `allowUncertainFrontSideScan` to settings. When this parameter is set to true, the Recognizer will proceed scanning the back side of the document even if the front side scanning result `State` is `Uncertain`.
- We've enabled the return of image and back side data results, even when the `State` is `Uncertain`. Keep in mind that returned images, in this case, might be blurry or low quality.
	- This applies to all image types: full document image, face and signature image.
- We've added two separate fields for the processing status in the Recognizer Result: `frontProcessingStatus` and `backProcessingStatus`. They indicate the status of the last recognition process for each side.

### Improvements
- We added support for the Malaysian NRIC numbers that hold an asterisk (*) character.
- While using `FullRecognitionMode` for scanning unsupported Passports, we are now extracting `ClassInfo` from MRZ
- Improved quality of fully cropped vertical images
- Better parsing of Bermuda Driving License AAMVA-compliant barcode dates
- Fix for correct calculation of check digit for Saudi Arabia ID Card MRZ
- We are splitting first and last name from the additional name information (e.g., Nom d’ usage, Epouse, Geb. etc.)  into two different results. The additional name info will be a part of the `name_additional_info` field. This applies to the following documents:

	- France
		- ID Card
		- Residence Permit
	- Germany
		- ID Card
	- Luxembourg
		- ID Card
	- Netherlands
		- Driving License
		- Polycarbonate Passport

- We are removing title prefixes (e.g., Mrs., Mr., Ing., etc.) from `full_name`, `first_name` and `last_name` for these documents:
	- Austria
		- Driving License
		- ID Card
	- Czechia
		- Driving License
	- Germany
		- ID Card
	- Thailand
		- ID Card
	- UK
		- Driving License


#### Anonymization
- We've added anonymization for new documents:
	- Document number on Germany Polycarbonate Passport
	- Document number on Hong Kong Polycarbonate Passport
	- Document number and personal ID number on Singapore Polycarbonate Passport

### Breaking change

To optimize our scanning process, we decided to remove support for processors which don't have VFPv4 support because it blocked us from implementing those optimizations.
This affects only a small number of older devices with armeabi_v7 CPUs. Those devices are rarely used.


## 5.11.0

### Newly supported identity documents:

We’ve added 98 new documents:

#### Europe
- Albania - Driver Card (front only)
- Albania - Professional Driver License (front only)
- Belarus - Driver License (front only, beta)
- Belgium - Minors ID (beta)
- Czechia - Residence Permit
- Finland - Alien ID
- Finland - Residence Permit (beta)
- Georgia - Driver License (front only)
- Greece - Residence Permit
- Ireland - Passport Card (beta)
- Ireland - Public Services Card (beta)
- Kosovo - Driver License (front only, beta)
- Latvia - Alien ID
- Luxembourg - ID Card
- Moldova - ID Card (beta)
- North Macedonia - Driver License (front only)
- North Macedonia - ID Card
- Poland - Passport (beta)
- Slovenia - Residence Permit (beta)
- Spain - Alien ID
- UK - Passport (beta)

#### Middle East and Africa
- Algeria - Driver License
- Burkina Faso - ID Card (front only)
- Cameroon - ID Card (beta)
- Democratic Republic Of The Congo - Driver License (front only, beta)
- Egypt - Driver License (beta)
- Ghana - ID Card (beta)
- Iraq - ID Card (beta)
- Ivory Coast - Driver License (front only, beta)
- Ivory Coast - ID Card
- Lebanon - ID Card (beta)
- Morocco - Driver License
- Mozambique - Driver License (front only, beta)
- Oman - Driver License (beta)
- Rwanda - ID Card (front only)
- Senegal - ID Card
- Tanzania - Driver License (front only, beta)
- Tunisia - Driver License (front only)
- Uganda - Driver License (front only, beta)

#### Latin America & the Caribbean
- Argentina - Alien ID (beta)
- Bahamas - ID Card (front only, beta)
- Bolivia - Minors ID (beta)
- Jamaica - Driver License
- Mexico - Residence Permit (beta)
- Mexico - Chiapas - Driver License (front only)
- Mexico - Coahuila - Driver License (beta)
- Mexico - Durango - Driver License(front only, beta)
- Mexico - Guerrero-cocula - Driver License (beta)
- Mexico - Guerrero-juchitan - Driver License (beta)
- Mexico - Guerrero-tepecoacuilco - Driver License (front only, beta)
- Mexico - Guerrero-tlacoapa - Driver License (front only, beta)
- Mexico - Hidalgo - Driver License
- Mexico - Mexico - Driver License (beta)
- Mexico - Morelos - Driver License (front only)
- Mexico - Oaxaca - Driver License
- Mexico - Puebla - Driver License (front only, beta)
- Mexico - San Luis Potosi - Driver License (front only)
- Mexico - Sinaloa - Driver License (front only, beta)
- Mexico - Sonora - Driver License (beta)
- Mexico - Tabasco - Driver License (beta)
- Mexico - Yucatan - Driver License (beta)
- Mexico - Zacatecas - Driver License (beta)
- Panama - Temporary Residence Permit (beta)
- Peru - Minors ID (beta)
- Trinidad And Tobago - Driver License (front only, beta)
- Trinidad And Tobago - ID Card

#### Oceania
- Australia - South Australia - Proof Of Age Card (front only, beta)

#### Asia
- Armenia - ID Card
- Bangladesh - Driver License (beta)
- Cambodia - Driver License (front only, beta)
- India - Gujarat - Driving Licence (front only, beta)
- India - Karnataka - Driving Licence (front only, beta)
- India - Kerala - Driving Licence (beta)
- India - Madhya Pradesh - Driving Licence (front only, beta)
- India - Maharashtra - Driving Licence (front only, beta)
- India - Punjab - Driving Licence (front only, beta)
- India - Tamil Nadu - Driving Licence (beta)
- Kyrgyzstan - ID Card
- Malaysia - Mypolis (beta)
- Malaysia - Refugee ID (front only)
- Myanmar - Driver License (beta)
- Pakistan - Punjab - Driving Licence (front only, beta)
- Sri Lanka - Driving Licence (front only)
- Thailand - Alien ID (front only)
- Thailand - Driver License (beta)
- Uzbekistan - Driver License (front only, beta)

#### Northern America
- Canada - Tribal ID (beta)
- Canada - Nova Scotia - ID Card (beta)
- Canada - Saskatchewan - ID Card (beta)
- USA - Border Crossing Card (front only)
- USA - Global Entry Card (beta)
- USA - Nexus Card (beta)
- USA - Veteran ID (front only)
- USA - Work Permit
- USA - Mississippi - ID Card (beta)
- USA - Montana - ID Card
- USA - New Mexico - ID Card (beta)
- USA - Wisconsin - ID Card (beta)

#### Back side support added:
- Hungary - Residence Permit
- Luxembourg - Residence Permit (no longer beta)
- Mauritius - ID Card
- Colombia - Alien ID (no longer beta)
- Mexico - Baja California - Driver License
- Mexico - Chihuahua - Driver License
- Mexico - Guanajuato - Driver License
- Mexico - Michoacan - Driver License
- Malaysia - MyKid
- Malaysia - MyPR

#### No longer beta:
- Albania - Passport
- Malta - Residence Permit
- Switzerland - Residence Permit
- Bolivia - Driver License
- Chile - Passport
- El Salvador - ID Card
- Peru - ID Card
- Singapore - S Pass (front only)

### Changes to the BlinkId(Combined)Recognizer
- You can now retrieve an image of the document owner along with cropped images of the document itself whenever you’re scanning an AAMVA-compliant ID:

	- Using `BarcodeId` as a `RecognitionMode` lets you scan US driver licenses and IDs that BlinkID can’t read from the Visual Inspection Zone (VIZ) alone. Use it to extract:
		- A face image from the front side
		- Barcode data from the back side
		- Cropped document images of both sides
	- You can disable this `RecognitionMode` by setting `enableBarcodeId` to `false` in the `RecognitionModeFilter`.
- We've improved data extraction through the MRZ:
	- We now allow standard M/F values for gender on Mexican documents (along with localized H/M values)
- We're now converting dates to the Gregorian calendar for:
	- Taiwan documents with Republic of China (ROC) calendar dates
	- Saudi documents with Islamic calendar dates
- We're now auto-filling all ‘partial’ dates found on identity documents (showing year or month-year only):
	- Date of issue will be converted to the first day of the (first) month
		- E.g. '1999' will be converted to '01.01.1999.'
		- E.g. '03.1999.' will be converted to '01.03.1999.'
	- Date of expiry will be converted to the last day of the (last) month
		- E.g. '1999' will be converted to '31.12.1999.'
		- E.g. '03.1999.' will be converted to '31.03.1999.'


## 5.10.0

### Improvements

- **Newly supported identity documents**

    - Saudi Arabia - DL (front)
    - Saudi Arabia - Resident ID (front)

- We've introduced another variant of `resetRecognitionState` method that lets you reset the scanning process in different recognition stages:
- If you’re using our Direct API, call `RecognizerRunner.resetRecognitionState(false)` to clear the scanning cache as well as results gathered in a specific recognition step, i.e. after scanning the back side of a document.
- If you’re using our built-in UI to scan IDs from the camera stream, call `RecognizerRunnerView.resetRecognitionState(false)` to do the same.

### Changes to the BlinkId(Combined)Recognizer:

- We're now able to extract the additional address on Hungary Address Cards
- We've improved data extraction through the MRZ:
    - We now return the document type through `ClassInfo`, regardless of the `RecognitionMode` you're using (`MrzId`, `MrzPassport` or `MrzVisa`).
    - This means you can now use `ClassFilter` to filter these documents by their type.
    - We now return the document number on Nigeria IDs complete with its check digit.
    - We now support Italy Residence Permits with a *CR* document code.
- We've extended the `ClassInfo` structure with helper methods so you can filter documents by country more easily:
    - Use `countryName`, `isoNumericCountryCode`, `isoAlpha2CountryCode` and `isoAlpha3CountryCode` to get the full country names or their representative codes defined by [ISO](https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes).
- We've extended the `BarcodeResult` structure with `extendedElements`
    - You can find all data from AAMVA-compliant barcodes under their respective `BarcodeElementKey` in the `BarcodeElements` structure
    - For a full list of keys please see `BarcodeElementKey` enum
- We've added another `ProcessingStatus` called `AwaitingOtherSide`
    - This status is triggered once BlinkID has finished with the first side of a document and expects the other side, too.
- We're now able to extract the date of birth from the CURP field on Mexico Voter IDs
- Direct API:
	- We've added a new recognition mode for recognizing still images of documents that have already been cropped:
		- Set the `scanCroppedDocumentImage` to true when you're feeding BlinkID images of documents that have already been cropped and don't require detection.
		- Keep in mind that this setting won't work on document images that haven't been properly cropped.


### Changes to the IdBarcodeRecognizer:

- We've extended the results with `extendedElements`
    - You can find all data from AAMVA-compliant barcodes under their respective `BarcodeElementKey` in the `BarcodeElements` structure
    - For a full list of keys please see `BarcodeElementKey` enum

### Deprecated recognizers:

-  We've deprecated `UsdlRecognizer`. Please use `IdBarcodeRecognizer` instead.

### Minor API changes

- We've replaced `Using time-limited license!` warning with `Using trial license!` warning. The warning message is displayed when using a trial license key.
- We’ve renamed `MrtdDocumentType.MRTD_TYPE_IDENITY_CARD` enum member to `MrtdDocumentType.MRTD_TYPE_IDENTITY_CARD`.

### Fixes:

- We've fixed the front-facing camera error on `Oukitel WP8 Pro`.
- We've fixed a rare crash in `BlinkIdOverlayController`, which happened as a result of rapidly launching the scanning process multiple times in a row.

## 5.9.0

### New features:
- We’ve updated SDK's Android **target API level to 30**.
- We’ve translated the complete SDK to the **Serbian** language.
- We’ve  made the SDK safe from **tapjacking**, a form of attack where a user is tricked into tapping something he or she didn’t intend to tap. We did this by adding a new security option that prompts the SDK to discard touches when the activity's window is obscured by another visible window. To activate it, use `UISettings.setFilterTouchesWhenObscured(true)`.
- We’ve added a new feedback message to users, reminding them to keep a document fully visible in case they accidentally cover a part of it with their finger or an object.
    - It appears while scanning the document by using `BlinkIdUISettings` and `BlinkIdOverlayController`:
    - It will display an error message "Keep the document fully visible".
    - This message is displayed only when using `BlinkIdRecognizer` or `BlinkIdCombinedRecognizer` for scanning.
- We’ve introduced a new `FrameRecognitionCallback` callback for the `RecognizerRunnerView`, which is invoked each time a camera frame from a video stream is recognized. Use `RecognizerRunnerView.setFrameRecognitionCallback` method to set the callback.

### Newly supported identity documents

- 53 documents added:
	- ALBANIA - DL (front)
	- BELGIUM - RESIDENCE PERMIT (front, back)
	- BOLIVIA - ID (front, back)
	- BOSNIA AND HERZEGOVINA - PASSPORT
	- CAMBODIA - PASSPORT
	- CANADA - RESIDENCE PERMIT (front, back)
	- CANADA - MANITOBA - ID (front)
	- CANADA - ONTARIO - HEALTH INSURANCE CARD (front)
	- CHILE - ALIEN ID (front, back)
	- CHINA - ID (front, back)
	- COLOMBIA - MINORS ID (front, back)
	- CYPRUS - RESIDENCE PERMIT (front, back)
	- CZECHIA - PASSPORT
	- GREECE - ID (front)
	- HAITI - ID (front, back)
	- ITALY - RESIDENCE PERMIT (front, back)
	- LATVIA - DL (front)
	- LATVIA - PASSPORT
	- LITHUANIA - PASSPORT
	- LUXEMBOURG - DL (front)
	- MONTENEGRO - DL (front)
	- MONTENEGRO - ID (front, back)
	- MONTENEGRO - PASSPORT
	- NETHERLANDS - RESIDENCE PERMIT (front, back)
	- NICARAGUA - ID (front, back)
	- NIGERIA - ID (front, back)
	- NORWAY - RESIDENCE PERMIT (front, back)
	- OMAN - RESIDENT ID (front, back)
	- PARAGUAY - DL (front, back)
	- PERU - DL (front, back)
	- PHILIPPINES - SOCIAL SECURITY CARD (front)
	- ROMANIA - PASSPORT
	- RUSSIA - PASSPORT
	- SERBIA - PASSPORT
	- SLOVAKIA - PASSPORT
	- SLOVENIA - PASSPORT
	- SOUTH KOREA - DL (front)
	- SPAIN - RESIDENCE PERMIT (front, back)
	- SWEDEN - RESIDENCE PERMIT (front, back)
	- THAILAND - PASSPORT
	- UKRAINE - DL (front)
	- UKRAINE - PASSPORT
	- USA - ARKANSAS - ID (front, back)
	- USA - CONNECTICUT - ID (front, back)
	- USA - GREEN CARD (front, back)
	- USA - MARYLAND - ID (front, back)
	- USA - MINNESOTA - ID (front, back)
	- USA - NEVADA - ID (front, back)
	- USA - NEW YORK CITY - ID (front, back)
	- USA - TEXAS - WEAPON PERMIT (front)
	- USA - VIRGINIA - ID (front, back)
	- VENEZUELA - DL (front)
	- VENEZUELA - PASSPORT

- Beta support added for 46 documents:
	- ALBANIA - PASSPORT
	- BAHAMAS - DL (front)
	- BERMUDA - DL (front)
	- BOLIVIA - DL (front)
	- CHILE - DL (front)
	- COLOMBIA - ALIEN ID (front)
	- DENMARK - RESIDENCE PERMIT (front, back)
	- DOMINICAN REPUBLIC - DL (front, back)
	- ECUADOR - DL (front)
	- EL SALVADOR - DL (front, back)
	- ESTONIA - RESIDENCE PERMIT (front, back)
	- GUATEMALA - DL (front, back)
	- HAITI - DL (front)
	- HONDURAS - DL (front, back)
	- HONDURAS - ID (front, back)
	- HUNGARY - ADDRESS CARD (front, back)
	- HUNGARY - RESIDENCE PERMIT (front)
	- ICELAND - DL (front)
	- ISRAEL - ID (front, back)
	- JAPAN - DL (front)
	- JORDAN - DL (front)
	- LATVIA - ALIEN PASSPORT
	- LATVIA - RESIDENCE PERMIT (front, back)
	- LUXEMBOURG - RESIDENCE PERMIT (front)
	- MALTA - RESIDENCE PERMIT (front, back)
	- MEXICO - BAJA CALIFORNIA - DL (front)
	- MEXICO - CHIHUAHUA - DL (front)
	- MEXICO - CIUDAD DE MEXICO - DL (front)
	- MEXICO - PROFESSIONAL DL (front)
	- MEXICO - GUANAJUATO - DL (front)
	- MEXICO - MICHOACAN - DL (front)
	- MEXICO - TAMAULIPAS - DL (front, back)
	- MEXICO - VERACRUZ - DL (front, back)
	- PHILIPPINES - TAX ID (front)
	- PHILIPPINES - VOTER ID (front)
	- POLAND - RESIDENCE PERMIT (front, back)
	- PORTUGAL - RESIDENCE PERMIT (front, back)
	- PUERTO RICO - VOTER ID (front)
	- SLOVAKIA - RESIDENCE PERMIT (front, back)
	- SOUTH KOREA - ID (front)
	- SWITZERLAND - RESIDENCE PERMIT (front, back)
	- TAIWAN - TEMPORARY RESIDENCE PERMIT (front)
	- TURKEY - RESIDENCE PERMIT (front)
	- USA - KANSAS - ID (front, back)
	- VENEZUELA - ID (front)
	- VIETNAM - DL (front)

- Added back side support for 7 documents:
	- ARGENTINA - ID
	- ECUADOR - ID
	- FINLAND - ID
	- NIGERIA - DL
	- QATAR - RESIDENCE PERMIT
	- URUGUAY - ID
	- USA - NEW YORK - DL

- 9 documents are no longer beta:
	- BRAZIL - DL
	- CANADA - ALBERTA - ID
	- MALAYSIA - MyKAS
	- MEXICO - NUEVO LEON - DL
	- PANAMA - DL
	- PORTUGAL - DL
	- SAUDI ARABIA - ID
	- SRI LANKA - ID
	- USA - IDAHO - ID


### New features and updates to the `BlinkIdRecognizer` and `BlinkIdCombinedRecognizer`

- We’re now able to read partial MRZ formats (2.5 lines), like the ones found on Switzerland and Liechtenstein DLs.
- We’ve added `documentOptionalAdditionalNumber` to the main part of the result, as well as front and back side VIZ results.
- We’ve expanded the set of possible recognizer states with `StageValid`. This state fixes `BlinkIDCombinedRecognizer` timeout issues, and enables better control of the Combined scanning pipeline. It activates when the first side of a document has been successfully scanned and scanning of the second side is required.

### Fixes

- We’ve fixed an uncommon bug where you’d get incomplete results upon scanning of the MRZ with the `allowUnparsed` setting enabled.
- SDK does not require permission `android.permission.ACCESS_NETWORK_STATE` to unlock itself anymore, in cases when the license key needs online activation.


## 5.8.0

### New features:

* We added user feedback when turning ON the flashlight on `BlinkIdOverlayController`:
	* It warns the user to watch out for flashlight glare.
	* It can be disabled by using `BlinkIdUISettings.setShowFlashlightWarning(false)` option.

### New additions to our supported documents list

#### Plastic page passports

We added support for scanning the visual inspection zone - VIZ includes everything except MRZ or barcode. Keep in mind that BlinkID scans and extracts data only from the VIZ that is on the first **plastic page** found in the passport list below:

* **Chile** Passport (BETA)
* **Colombia** Passport
* **Croatia** Passport
* **Denmark** Passport
* **Finland** Passport (BETA)
* **Germany** Passport
* **Hong Kong** Passport (BETA)
* **Ireland** Passport (BETA)
* **Malaysia** Passport
* **Netherlands** Passport
* **New Zealand** Passport
* **Norway** Passport
* **Singapore** Passport
* **South Africa** Passport
* **Sweden** Passport
* **Turkey** Passport (BETA)

If you need to scan the MRZ only, you can always set the "MRZ Passport" [recognition mode](https://microblink.github.io/blinkid-android/com/microblink/entities/recognizers/blinkid/generic/RecognitionModeFilter.html#enableMrzPassport).
​
#### Vertical US documents

* **California** ID
* **Illinois** ID
* **New York** ID
* **North Carolina** ID
* **Texas** ID

#### Other documents

* **Canada** Newfoundland and Labrador DL
* **Croatia** Residence Permit (BETA)
* **Guatemala** Consular ID
* **Malaysia** MyKAS (BETA)
* **Mexico** Jalisco DL / front side only
* **Mexico** Nuevo Leon DL (BETA)
* **Peru** ID (BETA)
* **Singapore** S Pass (BETA)
* **Uruguay** ID / front side only
* **USA** Missouri ID
* **USA** Texas ID

#### European DLs with a single line MRZ

BlinkID extracts data from driver’s licenses that contain single line MRZ:

* **Croatia** DL
* **Estonia** DL
* **France** DL
* **Ireland** DL
* **Netherlands** DL
* **Slovakia** DL

#### Back side supported on:
* **Azerbaijan** ID
* **Singapore** DL
* **Singapore** Employment Pass

#### No longer BETA
* **Slovakia** DL

### New features and updates in BlinkId(Combined)Recognizer

* We added `signatureImage` to the result. Extract signature image from the documents below:
	* Australia Victoria DL
	* Austria ID
	* Austria DL
	* Brunei Military ID
	* Colombia ID
	* Croatia ID (on 2013 and 2015 versions)
	* Cyprus ID
	* Czechia ID (on the 2012 version)
	* Germany ID (2010 version)
	* Germany DL (2013 version)
	* Indonesia ID
	* Ireland DL
	* Italy DL
	* Mexico Voter ID
	* New Zealand DL
	* Slovenia ID
	* Spain DL
	* Sweden DL
	* Switzerland ID
	* UAE ID
	* UAE Resident ID

* We enabled extraction of the **date of birth** from the **NRIC** from Malaysian documents:
	* MyKad
	* MyKas
	* MyKid
	* MyPR
	* MyTentera

* We added anonymization support for:
	* MRZ (OPT2 containing the ID number) on China Mainland Travel Permit Hong Kong
	* MRZ (Document number) on Germany Alien Passport
	* Document number, MRZ (Document number) on Germany ID
	* MRZ (Document number) on Germany Minors Passport
	* MRZ (Document number) on Germany Passport
	* Document number on Hong Kong ID
	* MRZ (Document number, OPT1 containing the passport or ID number) on Hong Kong Passport
	* Personal ID number on Netherlands DL
	* Personal ID number, MRZ (OPT1 containing the BSN) on Netherlands ID
	* MRZ (OPT1 containing the BSN) on Netherlands Passport
	* Document number on Singapore DL
	* Personal ID number on Singapore Employment Pass
	* Document number on Singapore FIN Card
	* Document number on Singapore ID
	* MRZ (Document number, OPT1 containing the NRIC) on Singapore Passport
	* Document number on Singapore Resident ID
	* Document number on Singapore S Pass
	* Personal ID number on Singapore Work Permit
	* MRZ (OPT1 containing the resident registration number) on South Korea Diplomatic Passport
	* MRZ (OPT1 containing the resident registration number) on South Korea Passport
	* MRZ (OPT1 containing the resident registration number) on South Korea Residence Passport
	* MRZ (OPT1 containing the resident registration number) on South Korea Service Passport
	* MRZ (OPT1 containing the resident registration number) on South Korea Temporary Passport
* We improved MRZ data extraction on:
	* **Russia Passport**

### Other features and updates

* We added the field `middleName` to `BlinkId(Combined)Recognizer`, `IdBarcodeRecognizer` and `Usdl(Combined)Recognizer` results. This field is extracted from AAMVA standard compliant barcodes, whenever available.

### Deprecated recognizers

We have deprecated the following recognizers:

- `PassportRecognizer`, `VisaRecognizer` - **use `BlinkIdCombinedRecognizer` or `BlinkIdRecognizer` instead**

- `DocumentFaceRecognizer`, `MrtdRecognizer` - **use `BlinkIdRecognizer` instead**

- `MrtdCombinedRecognizer` - **use `BlinkIdCombinedRecognizer` instead**

- `UsdlRecognizer` - **use `IdBarcodeRecognizer` instead**

### Major API change

`ScanResultListener` interface now has an additional method called when the scanning cannot continue because of an unrecoverable error. You have to implement `onUnrecoverableError` method.
If you’re using built-in activities, when `onActivityResult` is called with `RESULT_CANCELED` result code, the exception will be available via `ActivityRunner.EXTRA_SCAN_EXCEPTION` intent extra. If the user canceled the scan, the exception would be `null`.

### Fixes

* We improved the data match logic for **Guatemala Consular ID** in BlinkId(Combined)Recognizer.
* We fixed race conditions in camera management, which in some cases caused that the camera was unable to resume after it has been paused.

## 5.7.0

## New features
- We translated complete SDK to the following additional languages: **Malay**, **Dutch**, **Hungarian**, **Slovenian**, **Indonesian**, **Arabic(UAE)**, **Romanian**, **Chinese traditional**, **Chinese simplified**, **Thai**, **Hebrew**, **Vietnamese**, **Filipino**.

## Improvements to existing features:

- We have improved parsing of **MRZ** formats deviating from the ISO/IEC 7501 standard:
	- Document discriminator was in place of the document number on driver licenses and IDs from:
		- **New York**
		- **Michigan**
		- **Canada**
	- Different check digit calculation for **Mexico** (Consular) ID
	- Recognition of the unofficial `XCT` country code for Northern **Cyprus** ID
	- Recognition of different country codes and check digit calculation on **China** Mainland Travel Permit for Hong Kong and Macao Residents
- We added **anonymization** support for:
	- MRZ on the Mainland Travel Permit for Hong Kong and Macao Residents
- We have made some changes to the **BlinkID(Combined)Recognizer**
	- You can now see `ProcessingStatus` in the results to inspect potential processing errors, such as when barcode detection fails, a mandatory field is missing, etc.
	- You can now also see a more detailed `ImageAnalysisResult` showing you when:
		- Face image is detected
		- MRZ is detected
		- Barcode is detected
	- We added a `RecognitionModeFilter` settings group. You can toggle flags on this object to control the recognition mode of the recognizer:
		- `enableMrzId` lets you scan MRZ on all identity documents except visas and passports.
		- `enableMrzVisa` lets you scan MRZ on visa documents.
		- `enableMrzPassport` lets you scan MRZ on passports.
		- `enablePhotoId` lets you scan photo IDs. Use it to enable or disable document and face image extraction on unsupported documents.
		- `enableFullRecognition` lets you scan all data from our [supported](https://github.com/microblink/blinkid-android/blob/master/documentation/BlinkIDRecognizer.md) documents.
		- Your license key still controls which of the above recognition modes are allowed.
	- We have added a `RecognitionMode` result member describing which recognition mode was used to produce the results.
	-  We are now retrieving sex and nationality fields from the MRZ in cases where those two fields cannot be found in the document’s VIZ. Previously, we only used to do this for dates, name fields and document numbers.
	- We are now preserving the original string (raw data) of the dates we couldn’t parse.
- We have improved the thresholds for card detection feedback messages ("move closer" and "move farther"). This will improve the UX when scanning in landscape mode as the document can now be closer to the camera.

## Minor API Changes
- We have made some changes to the `BlinkIDRecognizer` and `BlinkIDCombinedRecognizer`:
	- We renamed `DocumentImageMoireStatus` to `ImageAnalysisDetectionStatus`
	- We grouped the `conditions` member from the results with the `DriverLicenseDetailedInfo` structure
- We renamed `RecogitionMode` to `RecognitionDebugMode` in `RecognizerBundle`.

## Bug fixes:
- We fixed an issue in default UI that was causing reticle to reappear while card flip animation was showing.
- We fixed [Issue #220](https://github.com/microblink/blinkid-android/issues/220)  - methods marked with `@NonNull` no longer return `null`.

## 5.6.0

### New features:

- In `BlinkIdCombinedRecognizer` and `BlinkIdRecognizer` we added:
    - Support for US documents with **vertical** orientations:
        - Alabama DL
        - Arizona DL
        - California DL
        - Colorado DL
        - Connecticut DL
        - Georgia DL
        - Illinois DL
        - Iowa DL
        - Kansas DL
        - Kentucky DL
        - Maryland DL
        - Massachusetts DL
        - Minnesota DL
        - Missouri DL
        - New Jersey DL
        - Ohio DL
        - Pennsylvania DL
        - South Carolina DL
        - Tennessee DL
        - Texas DL
        - Utah DL
        - Washington DL
        - Wisconsin DL
    - Support for **new document types**:
        - Croatia Health Insurance Card / front side / BETA
        - Ecuador ID / front side
        - El Salvador ID / BETA
        - Sri Lanka ID / BETA
    - No longer BETA:
        - Canada Nova Scotia DL
        - Canada Yukon DL
        - Norway DL
    - Back side support:
        - Kenya ID
    - **Result anonymization** - with this option enabled, results are not returned for protected fields on documents listed here. The full document image will also have this data blacked out.
        - Protected fields are:
             - Document number on Hong Kong ID
             - MRZ on Hong Kong passports
             - Personal ID number on Netherlands DL
             - Personal ID number and MRZ on Netherlands ID
             - MRZ on Netherlands passports
             - Document number on Singapore DL, ID, Fin Card, Resident ID
             - Personal ID number on Singapore Employment Pass
             - Document number and personal ID number on Singapore Work Permit
             - MRZ on Singapore passports.
        - By using `setAnonymizationMode` method, you can choose the `AnonymizationMode` : `ImageOnly`, `ResultFieldsOnly`, `FullResult` or `None`.
        - `FullResult` anonymization (both images and data) is set by default.

- We added support for new **MRZ** formats:
    - Guatemala ID
    - Kenya ID

### Improvements to existing features:

- We updated `UsdlRecognizer.Result` and `IdBarcodeRecognizer.Result` with additional address fields:
    - `street`, `postalCode`, `city` and `jurisdiction`
- We added `isExpired()` method to `BlinkIdRecognizer.Result`, `BlinkIdCombinedRecognizer.Result` and `IdBarcodeRecognizer.Result`.
    - It compares the current time on the device with the date of expiry and checks whether the document has expired or not.
- We made changes to the result structure of `BlinkIdCombinedRecognizer` and `BlinkIdRecognizer`:
    - Barcode data is now encapsulated in its own result structure: `BarcodeResult`.
    - Data from all OCR-ed fields, without MRZ data, is now encapsulated in a `VizResult` structure, representing the "Visual Inspection Zone" data. In `BlinkIdCombinedRecognizer`, front side data is available in its own structure (`frontVizResult`), back side data in its own (`backVizResult`), so you can now **access data from each side separately**.
    - The main part of the result, outside these structures, is filled according to these rules:
        - Document number is filled with data from the MRZ, if present.
        - Remaining data is then filled with barcode data.
        - Remaining data is filled from the back side's visual inspection zone (OCR data outside of MRZ).
        - Remaining data is filled from the front side's visual inspection zone.
        - Remaining data is filled with data from the MRZ.

### Minor API changes:

- We moved `BlinkIdRecognizer.Result` members `colorStatus` and `moireStatus` to the result's `imageAnalysisResult` (`frontImageAnalysisResult` and `backImageAnalysisResult` in `BlinkIDCombinedRecognizer.Result`).
- We changed default `IntentDataTransferMode` to `IntentDataTransferMode.PERSISTED_OPTIMISED`. It can be configured by using `MicroblinkSDK.setIntentDataTransferMode`.

### Bug fixes:

- We fixed US driver's license address extraction (Oregon, Mississippi, Rhode Island).
- We fixed a bug which caused that, in some cases, camera has not been resumed after the device screen was turned OFF and back ON.


## 5.5.0

### New features:

- In `BlinkIdCombinedRecognizer` and `BlinkIdRecognizer` we added:
	- Support for obtaining full document image for IDs with barcodes. Now you can capture document image and extract barcode data with a single scan.
	- Scanning & data extraction for  travel visas and passports.
	- Field validation - we validate if the results from certain fields match predefined character sets.
			- If validation fails, the recognizer's state is `Recognizer.Result.State.Uncertain`.
			- Use `setValidateResultCharacters()`to enable or disable validation.
	- Field anonymization for sensitive data.
			- Enable or disable whether certain sensitive data should be anonymized in full document image result.
			- Use `setAnonymizeImage()` to enable or disable image anonymization.
	- Support for new document types:
		- Australia New South Wales - ID Card / Front only / BETA
		- Brazil - Driver License / BETA
		- Brunei - Military ID / BETA
		- Brunei - Residence Permit / BETA
		- Brunei - Temporary Residence Permit / BETA
		- Ghana - Driver License / Front only
		- Latvia - ID Card
		- Norway - Driving Licence / Front only / BETA
		- Oman - ID Card
		- Saudi Arabia - ID Card / BETA
		- Sweden - Social Security Card / Front only
		- USA - Social Security Card / BETA
		- Back side supported:
			- Malaysia - MyTentera


	- No longer BETA:
		- Australia Tasmania - Driving Licence
		- Canada British Columbia - ID Card
		- Germany - Residence Permit
		- Morocco - ID Card
		- Nigeria - Voter ID
		- Singapore - Work Permit
		- USA Alaska - ID Card
		- USA District Of Columbia - Driver License
		- USA Indiana - ID Card
		- USA Kentucky - ID Card

	- Barcode scanning on the following documents:
		- Argentina ID
		- Colombia ID
		- Nigeria Voter ID
		- South Africa ID

### Improvements for existing features:

- Improvements in `BlinkIdCombinedRecognizer` and `BlinkIdRecognizer`:
	- Documents discarded with the class filter are now reported as not supported
		-  `ClassifierCallback.onDocumentSupportStatus(false)` will be called if a documents is filtered out by the `ClassFilter`
	- For Malaysian MyKad we are now returning if a Moire pattern is present on the scanned document (detected or not detected).
		- use `getDocumentImageMoireStatus()` in `BlinkIdRecognizer`.
		- use `getDocumentFrontImageMoireStatus()` and `getDocumentBackImageMoireStatus()` in `BlinkIdCombinedRecognizer `.
- We added digital signature support to `PassportRecognizer`.
- We updated `IdBarcodeRecognizer.Result` with specific driving license info.
	- Use `getRestrictions()`, `getEndorsements()` and `getVehicleClass()`.
- We enabled usage of combined recognizers through the Direct API:
    - Recognition state is not automatically reset with every image that comes to the recognition through the Direct API. If reset is required, you should call `RecognizerRunner.resetRecognitionState()`

### Bug fixes:

- We fixed bug in `BlinkIdCombinedRecognizer` and `BlinkIdRecognizer` which caused that dates on Belgian ID cards are not parsed correctly in cases when month is July.


## 5.4.0

### Breaking change:

Based on the feedback, we have restored support for **x86_64** processor architecture. Due to the architecture specifics, there is no guarantee that the scanning process will work equally well as it does on the ARM architectures. The main reason for restoring support is to ease the compliance issue for developers.
We distribute SDK with **ARMv7**, **ARM64**, **x86**, and **x86_64** native library binaries.

### New features:

- We added age verification feature:
    - Now you can more easily obtain the age of the document owner in years and check whether it is above some age limit.
    - Use `age` and `ageLimitStatus` helper methods in `MrzResult`, `BlinkIdRecognizer.Result`, `BlinkIdCombinedRecognizer.Result`, `UsdlRecognizer.Result`, `UsdlCombinedRecognizer.Result`, and `IdBarcodeRecognizer.Result`.

### Improvements:

- We have translated complete SDK to following languages: **Croatian**, **Czech**, **English**, **French**, **German**, **Italian**, **Portuguese**, **Slovak**, and **Spanish**.

- We added support for new document types in `BlinkIdCombinedRecognizer` and `BlinkIdRecognizer`:

    - Australia - Australian Capital Territory - Driving licence / front only
    - Australia - Northern Territory - Driving licence / BETA
    - Australia - Tasmania - Driving licence / front only / BETA
    - Canada - Alberta - ID card / BETA
    - Canada - British Columbia - Driver license / Public services card (Combined)
    - Canada - British Columbia - ID card / BETA
    - Canada - British Columbia - Public services card
    - Canada - New Brunswick - Driving license
    - Canada - Nova Scotia - Driving license / BETA
    - Canada - Yukon - Driving license / BETA
    - Panama - Driving license / front only / BETA
    - Panama - ID card / front only
    - Singapore - Work permit / BETA
    - Taiwan - ID card / front only / BETA
    - USA - Alabama - ID card
    - USA - Alaska - ID card / BETA
    - USA - District Of Columbia - Driver license / BETA
    - USA - Idaho - ID card / BETA
    - USA - Indiana - ID card / BETA
    - USA - Kentucky - ID card / BETA
    - USA - Massachusetts - ID card
    - USA - Oregon - ID card
    - USA - Washington - ID card
    - We added support for back side to:
        - Australia - Western Australia - Driving licence
        - Mexico - Voter ID
        - Netherlands - Driving licence
- Additional improvements in `BlinkIdCombinedRecognizer` and `BlinkIdRecognizer`:
    - When the back side of the document is not fully supported by the `BlinkIdCombinedRecognizer`, we will capture and return the back side image without performing data extraction. You can disable this behaviour by using `BlinkIdCombinedRecognizer.setSkipUnsupportedBack(true)`.
    - We are now returning color status for the scanned document (black and white or color) in the following result fields:
        - `documentImageColorStatus` in `BlinkIdRecognizer.Result`.
        - `documentFrontImageColorStatus` and `documentBackImageColorStatus` in `BlinkIdCombinedRecognizer.Result`.
    - We are now returning `ClassInfo` which holds the following information about the scanned document: `Country`, `Region`, and `Type` of the document. Use  `BlinkIdRecognizer.Result.getClassInfo()` and `BlinkIdCombinedRecognizer.Result.getClassInfo()`.
    - We introduced `ClassFilter` which determines whether a document should be processed or is filtered out, based on its `ClassInfo`. Use  `BlinkIdRecognizer.setClassFilter` and `BlinkIdCombinedRecognizer.setClassFilter` to enable it.
    - To improve the scanning performance, we added additional feedback for users that ensures a detected document is entirely inside the frame. When a document is too close to the edge of the camera frame, we will display an appropriate message to the user in `BlinkIdOverlayController`. You can configure the minimum distance from the edge of the frame by using the `paddingEdge` settings method.

- Improvements in `BlinkIdOverlayController`:
    - When a document is too close to the edge of the camera frame, we display *`Move the document from the edge`* message.
    - We added better user instructions when barcodes are being scanned in `UsdlCombinedRecognizer`. We display *`Scan the barcode`* message.
- We are now delivering the complete list of open source dependencies used in the SDK. Please check the `open-source-software-used` directory.

### Minor API changes:

- We removed `RecognizerRunnerView` custom attributes: `mb_initialOrientation` and `mb_aspectMode`. Use `RecognizerRunnerView.setInitialOrientation` and `RecognizerRunnerView.setAspectMode` to configure the attributes in the code.

### Bug fixes:

- We fixed bug in `BlinkIdOverlayController` which caused that `MrtdRecognizer.Result` is cleared after scanning is done and empty result is returned.

## 5.3.0

### Breaking change:

We decided to remove support for **x86_64** processor architecture.
 **x86** and **x86_64** architectures are used on very few devices today; most of them are manufactured before 2015 and they take about 1% of all Android devices, according to the device catalog on Google Play Console.
We distribute SDK with **ARMv7**, **ARM64**, and **x86** native library binaries. Native library x86 in SDK is kept mainly for emulator support.

### New features:

- We added a new recognizer specialized for scanning and parsing barcodes on various identity cards - `IdBarcodeRecognizer`.  Supported document types are:
    - AAMVA compliant (US DL, Canada DL, etc.)
    - Argentina ID
    - Panama ID
    - Colombia ID
    - South Africa ID
    - Nigeria Voter ID and driver license

- We added the option to disable Microblink logs in the console output. Use `LoggingSettings.disableMicroblinkLogging()`. Be careful with this option. We need full log outputs from the application for support purposes. In case of having problems with scanning certain items, undesired behavior on the specific device(s), crashes inside SDK or anything unmentioned, we will need a full log from your side. If you disable Microblink logging, you won't be able to provide us this information. Hence support might be limited.

### Improvements for existing features:

- We added new result fields in `MrzResult`, returned by all recognizers which scan MRZ (Machine Readable Zone):
    - `issuerName`
    - `nationalityName`
- Improvements in `BlinkIdCombinedRecognizer` and `BlinkIdRecognizer`:
    - new recognizer options: `allowUnparsedMrzResults` and `allowUnverifiedMrzResults`
    - new result field: `dateOfExpiryPermanent`
    - support for 14 new document types: `South Australia DL`, `Bosnia and Herzegovina DL`, `Canada Ontario ID`, `Costa Rica DL`, `Denmark DL`, `Estonia DL`, `Finland DL`, `Kenya ID`, `Lithuania DL`, `Malaysia MyPR`, `Malta DL`, `Mexico Consular ID`, `Nigeria Voter ID`, `Philippines Professional ID`
    - improved support for: `USA Hawaii DL`, `Colombia DL`, `Costa Rica ID`, `France ID`, `India ID`, `Kazakhstan ID`, `Kuwait Resident ID`, `Israel DL`, `Kuwait DL`, `Thailand ID`

### Minor API changes:

- `CameraPermissionManager` constructor accepts object of type `CameraPermissionManager.ResourceIds` as a second parameter which can be used to customise "ask permission" layout - for previously used layout, use default implementation `com.microblink.hardware.camera.CameraPermissionManagerResources`
- `RecognizerRunnerFragment` extends `androidx.fragment.app.Fragment`, instead of deprecated `android.app.Fragment`
- All provided scan activities extend `AppCompatActivity`

### Bug fixes:

- We fixed crash when using Direct API on high resolution `com.microblink.image.Image` from `HighResImageWrapper`


## 5.2.0

### Improvements:

- **overall size impact on application reduced for almost 30%** when BlinkID SDK v5.2 is used, relative to size impact of the previous v5.1
- enabled setting `MrzFilter` on `MrtdCombinedRecognizer`:
    - determines whether document should be processed or it is filtered out, based on its MRZ (Machine Readable Zone)
    - this feature is also available for `MrtdRecognizer`
-  added Canada Alberta DL support for `BlinkIdRecognizer` and `BlinkIdCombinedRecognizer`
- enabled scanning back sides of 6 documents with `BlinkIdCombinedRecognizer`:
    - Czechia ID
    - Egypt ID
    - Germany ID
    - Italy ID
    - Pakistan Consular ID
    - Pakistan ID
- added property `localizedName` to `BlinkIdRecognizer.Result` and `BlinkIdCombinedRecognizer.Result` (CCC to Chinese alphabet conversion for Hong Kong ID)
- enabled digital signing of `BlinkIdCombinedRecognizer.Result`
- improved camera performance on some Samsung devices

### Minor API changes:

- `MrzFilter` now accepts `MrzResult` for filtering, previously filtering has been performed based on the given `MrtdRecognizer` result.
- `RecognizerRunner.getSingletonInstance()` does not throw `FeatureNotSupportedException` anymore

### Bug fixes:

- `BlinkIdCombinedRecognizer` - fixed issue when the front side of a document was returned as a back side

## 5.1.0

### New features:

- added 38 new documents for `BlinkIdRecognizer` and `BlinkIdCombinedRecognizer`: Algeria ID, Argentina DL, Australia Victoria DL, Australia Queensland DL, Australia Western Australia DL, Belgium Resident ID, Canada British Columbia DL, Canada Ontario DL, Canada Quebec DL, Cyprus ID, Czechia DL, Czechia ID, Egypt ID, Greece DL, India ID, India Pan Card, Israel DL, Malaysia MyKid, Malaysia MyTentera, Maldives ID, Morocco ID, Nigeria DL, Paraguay ID, Portugal DL, Puerto Rico DL, Qatar DL, Russia DL, Singapore Fin Card, Singapore Resident ID, Slovakia DL, Slovenia DL, South Africa DL, South Africa ID, Thailand ID, Tunisia ID, UAE DL, Uganda ID and Vietnam ID.
Details on each new document and the complete list of the supported documents is [here](https://github.com/microblink/blinkid-android/blob/cf93a77081a1f92806255ca44292c28f2989c472/documentation/BlinkIDRecognizer.md).

### Improvements:

- **overall size impact on application reduced for almost 50%** when BlinkID SDK v5.1 is used, relative to size impact of the previous v5.0
- added new fields in `MrzResult`:
    - `sanitizedDocumentCode`
    - `sanitizedDocumentNumber`
- improved `BlinkIdRecognizer` and `BlinkIdCombinedRecognizer`:
    - introduced blur filter that discards blurred frames and prevents reading data from them. This option is enabled by default, it can be disabled by using `BlinkIdRecognizer.setAllowBlurFilter(false)` and `BlinkIdCombinedRecognizer.setAllowBlurFilter(false)`

## 5.0.1

### New features:

- new options in `BlinkIdUISettings`, `DocumentUISettings`, `DocumentVerificationUISettings` and `BlinkIdOverlaySettings`:
    - option to disable displaying of "Document Not Supported" dialog when `BlinkIdRecognizer` or `BlinkIdCombinedRecognizer` is used in combination with other recognizers - use method `setShowNotSupportedDialog`
    - option to configure back side scanning timeout - use `setBackSideScanningTimeoutMs`
- it is possible to set theme that will be used by activity launched from the UISettings - use UISettings.setActivityTheme

### Minor API changes:
- all scan activity classes are final now

### Bug fixes:
- fixed problems with aspect ratio of camera preview on Huawei Mate 10

## 5.0.0

### **BlinkID v5 Release Announcement**

We are very proud to announce BlinkID v5, the latest upgrade of our most popular product. BlinkID v5 is our first product powered entirely by machine learning.

#### Highlights in the BlinkID v5 Release

BlinkID v5 introduces improvements in the speed and accuracy of scanning and data extraction, and you will find:

- Best-in-class card detection.
- Automatic classification of different document types (no need to preselect the country or document type).
- Innovative and seamless UX with real-time instructions for the users.
- Data matching – we match the expiry date, DOB, date of issue, and document number captured from the front side with the data from the barcode or MRZ on the back.

Besides the new features listed above, we have worked on the improved performance of several other document components:

- Scanning of all documents with MRZ (IDs, passports, travel visas), or PDF417 barcodes.
- Scanning and data extraction of the front side of US driver’s licenses and IDs (horizontal).
- Capturing cropped face image supported even on vertical documents, passports and travel visas.
- Capturing document image.

#### Integration improvements

One of the main benefits that BlinkID v5 will bring is scalability and faster support for new document types.

- The integration of the mobile SDK will be significantly easier and faster.
- Mobile SDK build size will be much smaller.

#### How to get started and plan your upgrade?

BlinkID v5 mobile SDK release is currently available for native platforms.

- It’s advisable to get familiar with the range of supported document types. We will keep adding support for new documents and regularly update [the list on Github](https://github.com/microblink/blinkid-android/blob/master/documentation/BlinkIDRecognizer.md).
- If currently supported document types are not yet supported in BlinkID v5 please follow future releases.
- To upgrade to BlinkID v5, you will need to obtain a new license key specifically for the document types that you require.
- Already licensed production keys will continue to work as usual, until you decide to upgrade.
- What if you require document types supported in the previous version (and not yet added in BlinkID v5) and another document type supported in BlinkID v5? That’s manageable, and in that case, we will create a custom build for you.

We strongly recommend checking the [list of supported document types](https://github.com/microblink/blinkid-android/blob/master/documentation/BlinkIDRecognizer.md) regularly. Please star and watch this repository, and take the time to test BlinkID v5 as much as possible.

Your opinion, especially on the performance and redesigned UX is very important for future developments. We would appreciate your feedback at blinkid@microblink.com.

### Major API changes:

- removed all country specific recognizers - use `BlinkIdRecognizer` and `BlinkIdCombinedRecognizer` which classify and extract data from all countries that have been supported by country specific recognizers available in 4.x.x versions of the SDK
- available recognizers are:
    - `BlinkIdRecognizer` and `BlinkIdCombinedRecognizer`
    - `MrtdRecognizer` and `MrtdCombinedRecognizer`
    - `UsdlRecognizer` and `UsdlCombinedRecognizer`
    - `DocumentFaceRecognizer`
    - `PassportRecognizer`
    - `VisaRecognizer`
- detectors, parsers and barcode recognizers are removed from this version onward
- `Templating API` is no longer supported
- `field by field` scanning feature is no longer supported
- SDK has been migrated to **AndroidX** dependencies - previous SDK dependency com.android.support:appcompat-v7 has been replaced with  **androidx.appcompat:appcompat**

### New features:

- `BlinkIdRecognizer` and `BlinkIdCombinedRecognizer` now support new document types from different countries, all supported document types are listed [here](https://github.com/microblink/blinkid-android/blob/c76754bf6dcd1f6c9d39bb1fefe613273dd701f2/documentation/BlinkIDRecognizer.md)

### Minor API changes:
- in combined recognizers results, `documentDataMatch` value is now returned as `DataMatchResult` enum with three possible values: `NotPerformed`,  `Failed` and `Success`
- new API for configuring camera options on `UISettings` -  use `UISettings.setCameraSettings`, which accepts object of `CameraSettings` type

## 4.11.1

### Bug fixes:

- fixed crash on some devices that use armeabi-v7a ABI - SDK was unable to reserve address space due to memory fragmentation
- fixed camera busy error in camera management that was happening during the quick closing and opening of the camera
- fixed rare crash on Samsung J5 Prime which has been caused by race condition during the initialization of native camera frame

## 4.11.0

### New features:

- added support for reading front and back side of Belgium ID - use `BelgiumIdFrontRecognizer`, `BelgiumIdBackRecognizer` and `BelgiumCombinedRecognizer`
- added support for reading all visa documents containing Machine Readable Zone - use `VisaRecognizer`

### Improvements for existing features:

- improved `MrtdRecognizer`:
    - added support for documents with non-binary gender specification (symbol X)
- improved `DocumentFaceRecognizer`:
    - improved scanning time (faster scan)
    - added support for vertical IDs
    - removed the `tryBothOrientations` option (improved scan in all directions is enabled by default)
- improved scanning time (faster scan) for `PassportRecognizer`

### Minor API changes:

- FieldByFieldOverlay is now configured by using FieldByFieldOverlaySettings instead of FieldByFieldUISettings
- DocumentVerificationUISettings and DocumentUISettings now support BlinkID recognizer
- BlinkIdUISettings now allow customisation of strings, colors and icons
- moved some classes to new packages

### Bug fixes:

- fix for document not supported never reported if using BlinkIdRecognizer wrapped in SuccessFrameGrabberRecognizer

## 4.10.0

### New features:
- added `BlinkIdRecognizer` for scanning front side of ID cards and `BlinkIdCombinedRecognizer` for combined scanning of front and back side of ID cards
    - for now, these recognizers classify and extract data from **87** different classes of **United States driver's licenses and IDs** (front and back side)
    - list of all supported document types can be found [here](https://github.com/microblink/blinkid-android/blob/d2112fa823377ec932e0c3f123dfc1a8fbdb4e3c/documentation/BlinkIDRecognizer.md)
    - in the upcoming releases, we are planning to add support for more document types from different countries
- completely new UX for scanning ID cards with new scan activity and overlay: `BlinkIdActivity` and `BlinkIdOverlayController`:
    -  best suited for scanning with `BlinkIdRecognizer` and `BlinkIdCombinedRecognizer`
    - other single side and combined document recognizers are also supported
- added support for reading back side of Nigerian Voter ID card - use `NigeriaVoterIdBackRecognizer`

### Improvements for existing features:
- improved `RomaniaIdFrontRecognizer`
    - now extracts `CNP` number
- improved `SloveniaIdFrontRecognizer` and `SloveniaCombinedRecognizer`:
    - return boolean flag which indicates whether **date of expiry** is permanent - use `SloveniaIdFrontRecognizer.Result.isDateOfExpiryPermanent()` and `SloveniaCombinedRecognizer.Result.isDateOfExpiryPermanent()`
- improved `GermanyPassportRecognizer`:
    - better passport classification
- improved `ColombiaIdFrontRecognizer`:
    - support for document number in format 1-3-3
- improved `SlovakiaIdFrontRecognizer`:
    - support for German letters
- Malaysia:
    - `MalaysiaMyTenteraFrontRecognizer` supports 6-digit army number
    - `MalaysiaIkadFrontRecognizer` - better extraction of the following fields (**DeepOCR** support): date of birth, sector, employer, address and date of expiry
- United Arab Emirates:
    - glare detection is disabled by default for `UnitedArabEmiratesIdFrontRecognizer` and `UnitedArabEmiratesIdBackRecognizer`
    - `UnitedArabEmiratesIdBackRecognizer` - optimized detection for black backgrounds

### Minor API changes:
- overlay controllers are no longer using UI settings, they're now using Overlay settings, you can convert UI settings to Overlay settings like this: `uiSettings.toOverlaySettings()`
- `Recognizer` now only has one type parameter
- `Recognizer.Result` no longer has any type parameters
- `DocumentOverlayController` and `BarcodeOverlayController` are replaced with single overlay controller - `BasicOverlayController`

### Removed features:
- `BarcodeScanActivity` no longer supports results dialog
- verification activities no longer support action bar built in obsolete activity themes

### Bug fixes:
- all default scan activities correctly set volume to media instead of ring
- all default scan activities now apply secure flag if enabled in ui settings
- fixed scanning bug for devices with problematic camera resolution, which caused that SDK was unable to scan data, known affected devices were: `OnePlus 6T`, `OnePlus 7 Pro` and `Vivo V15`

## 4.9.1

### Bug fixes:

- fixed bug in `DocumentFaceRecognizer` which caused that DPI settings has not been applied to dewarped images
- fixed bug in `DocumentVerificationOverlayController`/`DocumentVerificationActivity` - alert dialog has not been shown in cases when front and back side of the scanned document don't match
- `ColombiaIdBackRecognizer` - fixed result strings encoding problem

## 4.9.0

### New features:

- added support for reading all passports with MRZ - use `PassportRecognizer`

### Improvements for existing features:

- added `tryBothOrientations` option to `DocumentFaceRecognizer` which defines whether document will be scanned in both orientations (normal and upside down)
- `GermanyCombinedRecognizer.Result`:
    - added getter for the full MRZ string: `getRawMrzString`
- added support for reading commercial code in two rows for `HongKongIdFrontRecognizer`
- added support for `HongKongIdFrontRecognizer` 2018 version
- improved reading accuracy for the following recognizers (**DeepOCR** support):
`MalaysiaIKadFrontRecognizer`
- improved scanning time of all Malaysian ID front recognizers: `MalaysiaMyKadFrontRecognizer`, `MalaysiaMyKasFrontRecognizer`, `MalaysiaMyPrFrontRecognizer`, `MalaysiaMyTenteraFrontRecognizer`

### Minor API changes:

- Scanning timeout that can be configured by using `RecognizerBundle.setNumMsBeforeTimeout` is by default set to `RecognizerBundle.TIMEOUT_INFINITY`, which means that timeout is disabled by default. Previous default timeout value was 10 seconds.

### Bug fixes:

- DPI options on images are now correctly applied to dewarped image results in `DocumentFaceRecognizer.Result`
- fixed a validation issue for the gender field in `SloveniaCombinedRecognizer`

## 4.8.0

**Important notice on MRTD recognizer in the latest BlinkID SDK release (v4.8.0)**

Please note that we have significantly improved accuracy for MRZ/MRTD scanning because now we switched to the newest OCR technology based on machine learning.
To be more precise, we measured and compared existing vs. new MRTD scanning. The new OCR system based on machine learning achieves 99.9% accuracy on the character level, which results with a 50% reduction in the error rate in MRZ extraction.

In order to use new *MrtdRecognizer* or *MrtdCombinedRecognizer* or to continue using any additional *Recognizer for scanning any ID with the MRZ (machine readable zone)* within the latest BlinkID SDK update, you *must* have a new license key. Before updating to the SDK version 4.8.0, please contact your account manager or send an email to support@microblink.com to obtain the *new production license key*.

**Important notes**:

- The MRTD scanning with the older BlinkID SDK versions (v4.7.0 and below) will continue to work without any problems - until you decide to update.
- If you upgrade to the SDK version 4.8.0 without a new license key scanning of MRTD/MRZ documents will not work.
- Contact us at support@microblink.com to obtain a new license key if you plan to update your app with the latest release.

For any questions, you might have, we stand at your service.

### New features:

- added support for reading front and back side of Brunei Military ID - use `BruneiMilitaryIdFrontRecognizer` and `BruneiMilitaryIdBackRecognizer`

### Improvements for existing features:

- improved reading accuracy for all MRZ recognizers
- added option to force overlay orientation for `DocumentOverlayController` (`DocumentScanActivity`) and `BarcodeOverlayController` (`BarcodeScanActivity`) - use `DocumentUISettings.setForcedOrientation(OverlayOrientation)` and `BarcodeUISettings.setForcedOrientation(OverlayOrientation)`

### Minor API changes:

- removed `SerbiaIdFrontRecognizer`, `SerbiaIdBackRecognizer` and `SerbiaCombinedRecognizer`
- fields that are **not** deprecated anymore:
    - Sweden DL - reference number
    - Ireland DL - driver number
    - Malaysia iKad - passport number
    - Hong Kong ID - commercial code
- deprecated the following methods in `UsdlRecognizer.Result` and `UsdlCombinedRecognizer.Result`: (they have been replaced with new getters):
    - getField(UsdlKeys)
    - getOptionalElements
- added new getters to following results:
    - `UsdlRecognizer.Result` and `UsdlCombinedRecognizer.Result`:
        - `firstName`
        - `lastName`
        - `fullName`
        - `address`
        - `documentNumber`
        - `sex`
        - `restrictions`
        - `endorsements`
        - `vehicleClass`
        - `dateOfBirth`
        - `dateOfIssue`
        - `dateOfExpiry`
    - `MrzResult`:
       - `sanitizedOpt1`
       - `sanitizedOpt2`
       - `sanitizedNationality`
       - `sanitizedIssuer`
- moved `SwedenDlFrontRecognizer` from package `com.microblink.entities.recognizers.blinkid.sweden.dl` to `com.microblink.entities.recognizers.blinkid.sweden`
- renamed methods in the following recognizers and its results:
    - `CzechiaCombinedRecognizer`:
        - `lastName` to `surname`
        - `firstName` to `givenNames`
        - `identityCardNumber` to `documentNumber`
        - `address` to `permanentStay`
        - `issuingAuthority` to `authority`
        - `personalIdentificationNumber` to `personalNumber`
    - `GermanyCombinedRecognizer`:
        - `lastName` to `surname`
        - `firstName` to `givenNames`
        - `identityCardNumber` to `documentNumber`
        - `issuingAuthority` to `authority`
        - `eyeColor` to `colourOfEyes`
    - `JordanCombinedRecognizer`:
        - `issuer` to `issuedBy`
    - `PolandCombinedRecognizer`:
        - `issuer` to `issuedBy`
    - `RomaniaIdFrontRecognizer`:
       - `lastName` to `surname`
       - `cardNumber` to `documentNumber` from `MrzResult`
       - `parentNames` to `parentName`
       - `nonMRZNationality` to `nationality`
       - `nonMRZSex` to `sex`
       - `validFrom` to `dateOfIssue`
       - `validUntil` to `dateOfExpiry`
       - removed field `idSeries`
       - removed field `cnp`
       - MRZ fields are available through `MrzResult` which can be obtained by using getter `RomaniaIdFrontRecognizer.Result.getMrzResult()`
    - `SlovakiaCombinedRecognizer`:
       - `issuingAuthority` to `issuedBy`
       - `personalIdentificationNumber` to `personalNumber`
    - `SloveniaIdFrontRecognizer`:
       - `lastName` to `surname`
       - `firstName` to `givenNames`
    - `SloveniaIdBackRecognizer`:
       - `authority` to `administrativeUnit`
       - MRZ fields are available through `MrzResult` which can be obtained by using getter `SloveniaIdBackRecognizer.Result.getMrzResult()`
    - `SloveniaCombinedRecognizer`:
       - `lastName` to `surname`
       - `firstName` to `givenNames`
       - `identityCardNumber` to `documentNumber`
       - `citizenship` to `nationality`
       - `issuingAuthority` to `administrativeUnit`
       - `personalIdentificationNumber` to `pin`

### Bug fixes:

- fixed crashes on Nexus 6

## 4.7.0

### New features:

- added support for reading front and back side of Brunei Temporary Residence Permit - use `BruneiTemporaryResidencePermitFrontRecognizer` and `BruneiTemporaryResidencePermitBackRecognizer`
- added new scan activity and overlay: `BlinkCardActivity` and `BlinkCardOverlayController` which are best suited for scanning payment cards with `BlinkCardRecognizer`

### Improvements for existing features:

- enabled reading year-only dates of birth on **Kuwait IDs**
- improved `SingaporeIdBackRecognizer`:
    - better reading of documents with sticker
- improved `MrtdRecognizer`:
    - added `allowSpecialCharacters` option which is required for parsing Malaysian Passport IMM13P MRZ type
- all recognizers now reset their results on shake, except Combined recognizers
- `BlinkCardRecognizer` returns card issuer

### Minor API changes:

- `MrtdRecognizer` and `MrtdCombinedRecognizer` do not return MRZ image any more
- `MrtdComginedRecognizer` does not have glare detection options (it does not detect glare any more)
- replaced `PaymentCardFrontRecognizer`, `PaymentCardBackRecognizer` and `PaymentCardCombinedRecognizer` with single recognizer - `BlinkCardRecognizer`
- replaced `ElitePaymentCardFrontRecognizer`, `ElitePaymentCardBackRecognizer` and `ElitePaymentCardCombinedRecognizer` with single recognizer - `BlinkCardEliteRecognizer`
- `PolandIdBackRecognizer.Result` does not extend `MRTDResult` any more, it has getter `getMrzResult` for obtaining MRZ results

### Bug fixes:

- removed incorrect autofocus check that was performed before concrete camera type is chosen
- `MrtdRecognizer`:  result state is now properly invalidated after detection fails
- various other bug fixes and improvements

## 4.6.0

### New features:

- added support for reading front side of German Driver's License - use `GermanyDlFrontRecognizer`
- added support for reading back side of Brunei ID - use `BruneiIdBackRecognizer`
- added support for reading front side of Brunei Residence Permit - use `BruneiResidencePermitFrontRecognizer`
- added support for reading back side of Brunei Residence Permit - use `BruneiResidencePermitBackRecognizer`
- updated default UI icons

### Improvements for existing features:

- improved reading accuracy for the following recognizers (**DeepOCR** support):
	- `MalaysiaMyKadBackRecognizer`

### Minor API changes:
- if using high res frame capture in custom UI, you now have to explicitly enable it using new `RecognizerRunnerView` method  `setHighResFrameCaptureEnabled`
- renamed `GermanyOldIdRecognizer` to `GermanyIdOldRecognizer`
- removed `sex` and `signatureImage` from `MalaysiaMyKadBackRecognizer `
- renamed methods in `CroatiaCombinedRecognizer` and its result:
    - `identityCardNumber` to `documentNumber`
    - `address` to `residence`
    - `issuingAuthority` to `issuedBy`
    - `personalIdentificationNumber` to `oib`
    - `nonResident` to `documentForNonResident`
- removed `mrzImage` from `MrtdCombinedRecognizer` and its result
- renamed methods in `AustraliaDlFrontRecognizer.Result`:
    - `name` to `fullName`
    - `dateOfExpiry` to `licenceExpiry`
- renamed `eyeColour` to `colourOfEyes` in `GermanyIdBackRecognizer.Result`
- deprecated the following recognizers:
    - `SerbiaIdBackRecognizer`
    - `SerbiaIdFrontRecognizer`
    - `SerbiaCombinedRecognizer`
- deprecated the following result fields:
    - `HongKongIdFrontRecognizer.Result`:
        - `commercialCode`
    - `IndonesiaIdFrontRecognizer.Result`:
        - `bloodType`
        - `district`
        - `kelDesa`
        - `rt`
        - `rw`
    - `NewZealandDlFrontRecognizer.Result`:
        - `donorIndicator`
        - `cardVersion`
    - `MalaysiaMyKadBackRecognizer.Result`:
        - `extendedNric`
    - `MexicoVoterIdFrontRecognizer.Result`:
        - `electorKey`
    - `IrelandDlFrontRecognizer.Result`:
        - `driverNumber`
    - `SwedenDlFrontRecognizer.Result`:
        - `referenceNumber`
    - `MalaysiaIkadFrontRecognizer.Result`:
        - `passportNumber`
    - `AustriaIdBackRecognizer.Result`:
        - `principalResidence`
        - `height`
        - `eyeColour`
    - `AustriaPassportRecognizer.Result`:
        - `height`
     - `GermanyIdBackRecognizer.Result`:
        - `colourOfEyes`
        - `height`
    - `SwitzerlandIdBackRecognizer.Result`:
        - `height`
    - `SwitzerlandPassportRecognizer.Result`:
        - `height`
     - `SingaporeIdBackRecognizer.Result`:
        - `bloodGroup`
    - `ColombiaIdBackRecognizer.Result`:
        - `bloodGroup`
    - `SwitzerlandPassportRecognizer.Result`:
        - `height`
    - `PolandIdFrontRecognizer.Result`:
        - `familyName`
        - `parentsGivenNames`
    - `MoroccoIdBackRecognizer.Result`:
        - `fathersName`
        - `mothersName`
    - `RomaniaIdFrontRecognizer.Result`:
        - `parentNames`

### Bug fixes:

- fixed crash on some devices when using `VIDEO_RESOLUTION_MAX_AVAILABLE`

## 4.5.0

### New features:

- added support for reading front side of Brunei ID - use `BruneiIdFrontRecognizer`
- added support for reading front and back side of Cyprus ID, issued after 2015.  - use `CyprusIdFrontRecognizer` and `CyprusIdBackRecognizer`
- added support for reading front side of Malaysian MyKAS - use `MalaysiaMyKasFrontRecognizer`
- added support for reading front side of Malaysian MyPR - use `MalaysiaMyPrFrontRecognizer`
- enabled capturing high resolution camera frames:
    - When custom UI integration is performed, use `RecognizerRunnerView.captureHighResImage`
    - When using provided scan activities, high resolution full camera frames taken at the moment of successful scan are returned if this option is enabled through `UISettings`. Concrete `UISettings` which implement interface `HighResSuccessFrameCaptureUIOptions` support this feature.

### Improvements for existing features:
- improved reading accuracy for the following recognizers (**DeepOCR** support):
    - `HongKongIdFrontRecognizer`
    - `MalaysiaMyKadFrontRecognizer`
    - `MalaysiaMyKadBackRecognizer`
    - `MalaysiaMyTenteraFrontRecognizer`
    - `MalaysiaDlFrontRecognizer`
    - `NewZealandDlFrontRecognizer`
- improved `PaymentCard` recognizers:
    - better OCR and data extraction
    - added support for reading payment card numbers in 4x6x4 and 4x6x5 format
- improveed UAE recognizers:
    - glare detection is enabled for all images returned from `UnitedArabEmiratesDlFrontRecognizer`, `UnitedArabEmiratesIdBackRecognizer` and `UnitedArabEmiratesIdFrontRecognizer` recognizers
- improved `MrtdRecognizer`:
    - added option to set extension factors for full document image: use method `setFullDocumentImageExtensionFactors`
    - added option to encode `fullDocumentImage` and `mrzImage` to JPEG and save them to `MrtdRecognizer.Result`: use `setEncodeMrzImage` and `setEncodeFullDocumentImage` to enable encoding
- `RecognizerRunnerView` is lifecycle-aware now, it implements `android.arch.lifecycle.LifecycleObserver` interface

### Minor API changes:

- renamed `IkadRecognizer` to `MalaysiaIkadFrontRecognizer` and  methods in recognizer and its `Result`:
    - `expiryDate` to `dateOfExpiry `
    - `sex ` to `gender`
- renamed `MyKadFrontRecogniezer ` to `MalaysiaMyKadFrontRecognizer ` and  methods in recognizer and its `Result`:
    - `ownerFullName ` to `fullName `
    - `ownerAddress ` to `fullAddress `
    - `addressStreet ` to `street `
    - `ownerAddressZipCode ` to `zipcode `
    - `ownerAddressCity ` to `city `
    - `ownerAddressState ` to `ownerState `
    - `ownerBirthDate ` to `birthDate `
    - `ownerSex ` to `sex `
    - `ownerReligion ` to `religion `
    - `nricNumber ` to `nric `
- `MalaysiaMyKadFrontRecognizer` does not extract `armyNumber` anymore, use `MalaysiaMyTenteraFrontRecognizer` for scanning `MyTentera`
- `MrtdRecognizer`:
    - method `setSaveImageDPI` which has been used to set DPI for full document and MRZ image is replaced with methods `setFullDocumentImageDpi` and `setMrzImageDpi`

- renamed methods in `SwitzerlandIdBackRecognizer` and its `Result`:
    - `nonMrzDateOfExpiry` to `dateOfExpiry`
    - `nonMrzSex` to `sex`
- renamed methods in `SwitzerlandPassportRecognizer` and its `Result`:
    - `placeOfBirth` to `placeOfOrigin`
    - `nonMrzDateOfBirth` to `dateOfBirth`
    - `nonMrzDateOfExpiry` to `dateOfExpiry`
    - `nonMrzSex` to `sex`

### Bug fixes:
- fixed problems in camera management:
    - default camera surface is `TextureVeiw` for devices that use Camera1 API, otherwise `SurfaceView` is used
- `DocumentFaceRecognizer` now correctly applies DPI settings to returned face and full document images
- various other bug fixes and improvements

## 4.4.0

### New features:

- added support for reading back side of Germany Driver's License (reading of single B10 field - date of issue for B category)  - use `GermanyDlBackRecognizer`

- added support for reading front side of Mexico Voter ID  - use `MexicoVoterIdFrontRecognizer`

### Improvements for existing features:

- added support for hiding sensitive parts of images returned by payment card recognizers:
    - `PaymentCardFrontRecognizer`: setAnonymizeOwner, setAnonymizeCardNumber
    - `PaymentCardBackRecognizer`: setAnonymizeCvv
    - `PaymentCardCombinedRecognizer`:  setAnonymizeOwner, setAnonymizeCardNumber, setAnonymizeCvv
    - `ElitePaymentCardFrontRecognizer`: setAnonymizeOwner
    - `ElitePaymentCardBackRecognizer`: setAnonymizeCardNumber, setAnonymizeCvv
    - `ElitePaymentCardBackRecognizer`: setAnonymizeOwner,  setAnonymizeCardNumber, setAnonymizeCvv
- `CyprusIdBackRecognizer`: added support for reading date of expiry, use `getExpiresOn` on its result
- `SlovakiaIdFrontRecognizer`: improved reading of `personalNumber` field
- improved reading accuracy for the following recognizers:
    - `MalaysiaMyTenteraFrontRecognizer`
    - `SingaporeDlFrontRecognizer`
    - `CroatiaIdFrontRecognizer`
    - `IndonesiaIdFrontRecognizer`
- improved image return processor:
    - the processor now estimates detected (dewarped) document image quality and returns the best quality dewarped image from the best quality detection
- `DocumentVerificationActivity` does not extend `AppCompatActivity` any more

### Minor API changes:
- renamed methods in `GermanyIdFrontRecognizer` and its `Result`:
    - `lastName` to `surname`
    - `firstName` to `givenNames`
- renamed `MyTenteraRecognizer` to `MalaysiaMyTenteraFrontRecognizer` and  methods in recognizer and its `Result`:
    - `ownerFullName` -> `fullName`
    - `ownerAddress` -> `fullAddress`
    - `ownerAddressStreet` -> `street`
    - `ownerAddressZipCode` -> `zipcode`
    - `ownerAddressCity` -> `city`
    - `ownerAddressState` -> `ownerState`
    - `ownerBirthDate` -> `birthDate`
    - `ownerSex` -> `sex`
    - `ownerReligion` -> `religion`
    - `nricNumber` -> `nric`
- renamded enum `com.microblink.uisettings.options.ShowOcrResultMode` to `com.microblink.uisettings.options.OcrResultDisplayMode`
- for all `UISettings` classes which support setting of OCR result display mode, renamed method `setShowOcrResultMode` to `setOcrResultDisplayMode`

### Bug fixes:
- fixed camera autofocus problems on Samsung S9/S9+ when optimisation for near scanning is enabled
- fixed autofocus problems in `Field by field` scanning on Huawei P20 pro, Huawei P20 and Huawei P20 lite

## 4.3.0

### New features:

- added support for reading front side of Ireland Driver's License  - use `IrelandDlFrontRecognizer`
- added support for reading front side of Colombia Driver's License - use `ColombiaDlFrontRecognizer`
- added support for reading front side of Italy Driver's License - use `ItalyDlFrontRecognizer`
- added standalone recognizer for reading front side of Austria Driver's License - use `AustriaDlFrontRecognizer`
- added support for reading front and back side of elite Payment / Debit cards - use `ElitePaymentCardFrontRecognizer`, `ElitePaymentCardBackRecognizer` and `ElitePaymentCardCombinedRecognizer`

### Improvements for existing features:
- improved `MrtdCombinedRecognizer`:
    - added option to allow unparsed and unverified MRZ results - use `MrtdCombinedRecognizer.setAllowUnparsedResults` and `MrtdCombinedRecognizer.setAllowUnverifiedResults`
- improved `MalaysiaDlFrontRecognizer`:
    - added support for reading Malaysia Dl for foreigners
- improved `UsdlRecogniezr`:
    - added support for reading dates on Nigerian Driver's licenses
- added support for setting full document image extension factors for almost all ID document recognizers, they implement interface `FullDocumentImageExtensionOptions`
- added support for setting the number of stable detections threshold on `DocumentFaceRecognizer` and recognizers which use it internally: `MrtdCombinedRecognizer` and `UsdlCombinedRecognizer` - use `setNumStableDetectionsThreshold(int)`. This can help to avoid returning of blurry images.
- improved `EudlRecognizer`:
    - better reading accuracy for UK Driver's license
- moved these recognizers to DeepOCR engine (improved reading accuracy): `SingaporeIdFrontRecognizer`, `SingaporeIdBackRecognizer`, `CroatiaIdFrontRecognizer`,  `CroatiaIdBackRecognizer`
- improved DeepOCR accuracy

### Minor API changes:
- renamed methods in `MalaysiaDlFrontRecognizer` and its `Result`:
    - `state` to `ownerState`
    - `zipCode` to `zipcode`
- renamed methods in `IndonesiaIdFrontRecognizer` and its `Result`:
    - `validUntil` to `dateOfExpiry`
    - `validUntilPermanent` to `dateOfExpiryPermanent`
- renamed methods in `SingaporeIdFrontRecognizer` and its `Result`:
    - `bloodType` to `bloodGroup`
- renamed methods in `SingaporeCombinedRecognizer` and its `Result`:
    - `bloodType` to `bloodGroup`

### Bug fixes:
- enabled wrapping of combined recogniezrs with `SuccessFrameGrabberRecognizer`
- fixed bug in `EudlRecognizer` which caused that sometimes face image is not returned, even if the recognition was successful
- various other bug fixes and improvements

## 4.2.0

### New features:

- added support for reading front side of United Arab Emirates Driver's License - use `UnitedArabEmiratesDlFrontRecognizer`
- added support for reading front side of Spain Driver's License - use `SpainDlFrontRecognizer`
- added support for reading front and back side of Cyprus ID - use `CyprusIdFrontRecognizer` and `CyprusIdBackRecognizer`
- added support for reading front and back side of Kuwait ID - use `KuwaitIdFrontRecognizer` and `KuwaitIdBackRecognizer`
- added support for reading front and back side of Payment / Debit cards - use `PaymentCardFrontRecognizer`, `PaymentCardBackRecognizer` and `PaymentCardCombinedRecognizer`

### Improvements for existing features:

- improved `SingaporeCombinedRecognizer`:
    - added support for reading sticker with new address from the back side
- `BarcodeScanActivity` by default does not show result dialog after scan

### Minor API changes:
- renamed methods in `CroatiaIdBackRecognizer` and its `Result`:
    - `address` to `residence`
    - `documentForNonResident` to `isDocumentForNonResident`
    - `issuingAuthority` to `issuedBy`
    - `getDateOfExpiryPermanent` to `isDateOfExpiryPermanent`
    - MRZ fields are available through `MrzResult` which can be obtained by using getter `CroatiaIdBackRecognizer.Result.getMrzResult()`
- renamed method in `SingaporeIdFrontRecognizer` and its `Result`:
    - `cardNumber` to `identityCardNumber`
- renamed method in `SingaporeCombinedRecognizer` and its `Result`:
    - `cardNumber` to `identityCardNumber`
    - `bloodGroup` to `bloodType`

### Bug fixes:

- fixed bug which caused that results from the previous scan are cleared when the scan activity is run again and entities which have produced results are not used in the new scan
- various other bug fixes and improvements

## 4.1.0

### New features:

- added support for reading partial dates on all MRTD documents:
    - affects all recognizers which extract data from Machine Readable Zone
- added support for reading front side of Swiss Driver's License - use `SwitzerlandDlFrontRecognizer`
- added support for reading front side of Singapore Driver's License - use `SingaporeDlFrontRecognizer`
- added support for reading front side of Singapore Changi employee ID - use `SingaporeChangiEmployeeIdRecognizer`
- added support for reading front and back side of Morocco ID - use `MoroccoIdFrontRecognizer` and `MoroccoIdBackRecognizer`

### Improvements for existing features:

- improved `HongKongIdFrontRecognizer`:
    - added support for reading residential status field
- improved `UnitedArabEmiratesIdFrontRecognizer`:
    - better name and nationality extraction
- improved `SingaporeIdBackRecognizer`:
    - added support for reading sticker with new address
- improved `MyKadBackRecognizer`:
    - added support for reading old NRIC field
- improved `NewZealandDlFrontRecognizer`:
    - better reading of all fields

### Minor API changes:
- in `CroatiaIdFrontRecognizer` `identityCardNumber` is renamed to `documentNumber`
- `BarcodeRecognizer` does not support Manatee library any more:
    - removed setter `setManateeLicenseKey`
- in `GermanyIdBackRecognizer.Result`, `AustriaPassportRecognizer.Result`, `AustriaIdBackRecognizer.Result` and `AustriaCombinedRecognizer.Result` getter `getHeight` returns height as `String` (instead of `int`)
- `HongKongIdFrontRecognizer.Result.getDateOfBirth()` returns `com.microblink.results.date.DateResult` instead of `com.microblink.results.date.Date`
- in `ColombiaIdBackRecognizer.Result` getter `getDateOfBirth()` is renamed to `getBirthDate()`
- in `SingaporeIdBackRecognizer.Result` getter `getBloodGroup()` is renamed to `getBloodType()`
- renamed getters in `NewZealandDlFrontRecognizer.Result` renamed getters:
    - `getIssueDate()` to `getDateOfIssue()`
    - `getExpiryDate()` to `getDateOfExpiry()`
    - `getDonorIndicator()` to `isDonorIndicator()`

### Bug fixes:
- various bug fixes and improvements

## 4.0.0
- new API, which is not backward compatible. Please check [README](README.md) and updated demo applications for more information, but the gist of it is:
    - `RecognizerView` has been renamed to `RecognizerRunnerView` and `Recognizer` singleton to `RecognizerRunner`
    - `SegmentScanActivity` has been renamed to `FieldByFieldScanActivity`
    - `RandomScanActivity` does not exist anymore
    - previously internal `Recognizer` objects are not internal anymore - instead of having opaque `RecognizerSettings` and `RecognizerResult` objects, you now have stateful `Recognizer` object that contains its `Result` within and mutates it while performing recognition.
        - similarly we now have stateful `Parser` and `Detector` objects
        - introduced new `Processor` object type
        - For more information, see [README](README.md) and updated demo applications
    - added `RecognizerRunnerFragment` with support for various scanning overlays in a manner similar to iOS API. This now allows you to use built-in UI, which was previously strictly available for built-in activities, in form of fragment anywhere within your activity. Full details are given in [README](README.md) and in updated demo applications.
- new licence format, which is not backward compatible. Full details are given in [README](README.md) and in updated applications, but the gist of it is:
    - licence can now be provided with either file, byte array or base64-encoded bytes

## 3.18.0

### Improvements for existing features:

- `com.microblink.image.Image` class now has getter `getImageQuality()`:
    - can be used to choose the best image from the same scan
    - it only makes sense to relatively compare image qualities from consecutive camera frames from the same scan on a certain device

### Bug fixes:

- fixed `UnitedArabEmiratesIDBackRecognizer`:
    - enabled reading of UAE back side where MRZ starts with `IR`

## 3.17.0

### New features:

- added support for reading front side of Sweden Driver's License - use `SwedenDLFrontRecognizerSettings`

### Improvements for existing features:

- improved `CroatianIDBackSideRecognizer`:
    - better reading of address field
- improved `GermanIDFrontSideRecognizer`:
    - added support for reading CAN number
- improved `USDLRecognizer`:
    - better parsing of the USDL data fields
- improved `HongKongIDFrontRecognizer`:
    - better reading of document number (check digit validation)
- improved `IKadRecognizer`:
    - added support for iKad IMM_55 document type (foreign students card)
- added option to extend full document image for German recognizers:
`GermanIDFrontSideRecognizer`, `GermanOldIDRecognizer`, `GermanIDBackSideRecognizer`, `GermanIDCombinedRecognizer` and `GermanPassportRecognizer` - use method `setFullDocumentImageExtensionFactors(ImageExtensionFactors)` from the corresponding recognizer settings
- when `DocumentFaceRecognizer` is activated at the same time with another more specific recognizer(s) (e.g. EUDLRecognizer), preference is given to the more specific recognizer which means that it will get a chance to extract additional data from the concrete document type

### Bug fixes:

- fixed reading of non-standard PDF417 barcodes


## 3.16.0

### New features:

- added support for reading front side of Egypt ID - use `EgyptIDFrontRecognizerSettings`
- added support for reading front and back side of Jordan ID - use `JordanIDFrontRecognizerSettings`,  `JordanIDBackRecognizerSettings` and `JordanIDCombinedRecognizerSettings`
- added support for reading front side of Malaysian drivers license - use `MalaysianDLFrontRecognizerSettings`

### Improvements for existing features:

- improved `USDLRecognizer`:
    - better parsing of the USDL barcode content
    - fixed extraction of expiry date from magnetic stripe USDL subtype
- improved `VinParser`:
    - better extraction of specific VIN numbers
- improved `MRTDRecognizer`:
    - added support for parsing Malaysian Passport IMM13P MRZ type, reading of special characters must be enabled by using `MRTDRecognizerSettings.setAllowSpecialCharacters`
    - enabled reading of MRZ with '-' characters (non-default setting), to enable this use method `MRTDRecognizerSettings.setAllowSpecialCharacters`

### Bug fixes:

- fixed rare crash in `MRTDRecognizer`

## 3.15.1

### Bug fixes:

- fixed returning of face image when using `UnitedArabEmiratesIDFrontRecognizer`:
    - fixed face image position

## 3.15.0

### New features:

- added support for reading front side of Hong Kong ID - use `HongKongIDFrontRecognizerSettings`
- added support for reading front and back side of Colombian ID - use `ColombiaIDFrontRecognizerSettings` and `ColombiaIDBackRecognizerSettings`
- added support for reading front and back side of United Arab Emirates ID - use `UnitedArabEmiratesIDFrontRecognizerSettings` and `UnitedArabEmiratesIDBackRecognizerSettings`
- added support for reading front side of New Zealand drivers license - use `NewZealandDLFrontRecognizerSettings`

### Improvements for existing features:

- improved `MRTDRecognizer`:
    - improved reading of Belgium ID MRZ OPT2 field
    - added support for reading Belgium MRZ with partial date of birth - `MRTDRecognizerSettings.setAllowUnverifiedResults()` must be set to `true`
    - added support for reading Kenya MRZ - `MRTDRecognizerSettings.setAllowUnverifiedResults()` must be set to `true`
- improved `MyKadFrontSideRecognizer` and `MyTenteraRecognizer`:
    - better reading of name field
    - better reading of address field
- when setting DPI for full document image in concrete recognizer settings that has method `setFullDocumentImageDPI`, exception is thrown if DPI value is not in the expected range `[100, 400]`

### Bug fixes:

- fixed `NullPointerException` in `RecognizerView` caused by race condition that happens when quickly restarting camera manager
-  fixed a crash in Templating API caused by using a `MultiDetector` with `DetectorRecognizer`

## 3.14.0

### New features:

- added support for reading back side of Malaysian MyKad - use `MyKadBackSideRecognizerSettings`
- added support for reading Malaysian MyTentera documents - use `MyTenteraRecognizerSettings`
- added support for reading Malaysian MyTentera documents with MyKad recognizer - use `MyKadFrontSideRecognizerSettings` and enable reading of army number
- added support for setting DPI for full document images returned by `MyKadFrontSideRecognizer`, `MyKadBackSideRecognizer`, `MyTenteraRecognizer` and `IKadRecognizer`:
    - use `setFullDocumentImageDPI` on the corresponding recognizer settings

### Minor API changes:

- renamed `MyKadRecognizerSettings` and `MyKadRecognitionResult` to `MyKadFrontSideRecognizerSettings` and `MyKadFrontSideRecognitionResult` and moved them to `com.microblink.recognizers.blinkid.malaysia.mykad.front` package
- moved `IKadRecognizerSettings` and `IKadRecognitionResult` to `com.microblink.recognizers.blinkid.malaysia.ikad` package

### Improvements for existing features:

- improved `AustralianDLFrontSideRecognizer`:
    - improved reading of names and addresses
    - added support for reading first names with more words
- improved `SingaporeIDFrontRecognizer`:
    - tuned ID card data extraction positions
- improved Malaysian `IKadRecognizer`:
    - better reading of date of expiry and employer fields

### Bug fixes:

- fixed crash in `DocumentFaceRecognizer`

## 3.13.0

### New features:

- added support for reading back side of new Australian Driver's licence for state Victoria - use `AustralianDLBackSideRecognizerSettings`
- added support for reading front side of Indonesian ID - use `IndonesianIDFrontRecognizerSettings`
- added support for Malaysian visa with document code TS - use `MRTDRecognizerSettings`
- introduced `MRTDSpecification` and method `setMRTDSpecifications` on `MRTDRecognizerSettings` and `MRTDDetectorSettings`:
    - detection is limited only to document type specified with `MRTDSpecification`
    - when `MRTDSpecifications` are set, results will be returned only for specified MRTD documents
    - `MRTDSpecification` can be created by using `MRTDSpecification.createFromPreset`, available presets are: `MRTD_SPECIFICATION_TD1`, `MRTD_SPECIFICATION_TD2` and `MRTD_SPECIFICATION_TD3`
- added support for setting DPI for full document images returned by `MRTDRecognizer`, `AustralianDLFrontSideRecognizer`, `AustralianDLBackSideRecognizer` and `EUDLRecognizer`:
    - use `setFullDocumentImageDPI` on the corresponding recognizer settings

### Improvements for existing features:

- improved reading of Malaysian MyKad address

## 3.12.0

### New features:

- added support for scanning front and back side of Polish ID - use `PolishIDFrontSideRecognizerSettings`, `PolishIDBackSideRecognizerSettings` and `PolishIDCombinedRecognizerSettings`
- new document specification presets in `DocumentSpecificationPreset` enum:  `DOCUMENT_SPECIFICATION_PRESET_ID1_VERTICAL_CARD` and  `DOCUMENT_SPECIFICATION_PRESET_ID2_VERTICAL_CARD` - use `DocumentSpecification.createFromPreset` method to create document specification for detector
- `EUDLRecognizer` can return face image from the driver's license
- warning for time limited license keys when using provided activities, custom UI integration or Direct API:
    - the goal is to prevent unintentional publishing of application to production with the demo license key that will expire
    - warning toast can be disabled by using `EXTRAS_SHOW_TIME_LIMITED_LICENSE_KEY_WARNING` intent extra, `RecognizerView.setLicenseKeyTimeLimitedWarningEnabled` method when custom UI integration is used and `Recognizer.setLicenseKeyTimeLimitedWarningEnabled` method when Direct API is used

### Minor API changes:

- `DocumentSpecification` does not have method `setPhysicalSizeInInches` any more
- `DocumentDetectorResult` does not contain information about screen orientation any more
- `QuadDetectorWithSizeResult` is renamed to `QuadWithSizeDetectorResult`

### Improvements for existing features:

- improved face detection in `DocumentFaceRecognizer`: stable detection is required to prevent returning of blurred images
- improved reading of Malaysian `MyKad` documents:
    - improved reading and parsing of address fields: previously recognizer was unable to read some documents because of the expected address format
- improved reading of Malaysian visas and work permits
- better reading of dates on Australian Driver's Licence

### Bug fixes:

- fixed crash in `GermanIDBackSideRecognitionResult` caused by ProGuard obfuscation, when it is not declared in ProGuard rules to keep the result constructor
- fixed crash in `MRTDCombinedRecognizer`: recognizer does not support glare detection

## 3.11.0

### New features:

- added support for scanning front and back side of Swiss ID - use `SwissIDFrontSideRecognizerSettings` and `SwissIDBackSideRecognizerSettings` ​
- added support for reading front side of new Australian Driver's licence for state Victoria - use `AustralianDLFrontSideRecognizerSettings`
- introduced `MRZFilter`:
    - use  `MRTDRecognizerSettings` to enable it on  `MRTDRecognizer`
    - determines whether document should be processed or it is filtered out, based on its MRZ (Machine Readable Zone)
 - added `QuadDetectorWithSizeResult` which inherits existing `QuadDetectorResult`:
    - it's subclasses are `DocumentDetectorResult` and `MRTDDetectorResult`
    - returns information about physical size (height) in inches of the detected location when physical size is known
- introduced `GlareDetector` which is by default used in all recognizers whose settings implement `GlareDetectorOptions`:
    - when glare is detected, OCR will not be performed on the affected document position to prevent errors in the extracted data
    - if the glare detector is used and obtaining of glare metadata is enabled in `MetadataSettings`, glare status will be reported to `MetadataListener`
    - glare detector can be disabled by using `setDetectGlare(boolean)` method on the recognizer settings

### Minor API changes:

- `BlinkOCRRecognizerSettings` is now deprecated and will be removed in `v4.0.0`
	- use `DetectorRecognizerSettings` to perform scanning of templated documents
	- use `BlinkInputRecognizerSettings` for segment scan or for full-screen OCR
	- until `v4.0.0`, `BlinkOCRRecognizerSettings` will behave as before, however you are encouraged to update your code not to use it anymore
- `DocumentClassifier` interface is moved from `com.microblink.recognizers.blinkocr` to `com.microblink.recognizers.detector` package and `DocumentClassifier.classifyDocument()` now accepts `DetectorRecognitionResult` as parameter for document classification
- `USDLRecognizerSettings`:
    - removed option to scan 1D Code39 and Code128 barcodes on driver's licenses that contain those barcodes alongside PDF417 barcode

### Improvements for existing features

- improved date parsing:
	- affects date parser and all recognizers which perform date parsing
- added support for reading mirrored QR codes:
	- affects all recognizers that perform QR code scanning
- improved `CroatianIDBackSideRecognizer`:
    - better extraction of fields on back side of the Croatian ID card
- improved `USDLRecognizer`:
    - added support for new USDL standard

### Bug fixes:
- fixed crash in QR code reading
- fixed returning valid data for MRZ based recognizers when not all fields outside of the MRZ have been scanned

## 3.10.1

### Bug fixes:

- fixed pareclization of `BlinkOCREngineOptions`:
	- when defining character whitelist chosen character font has been incorrectly passed to native code
	- affects code where character whitelist is defined through `BlinkOCREngineOptions` for used parsers (e.g. `Templating API`)

## 3.10.0

### New features:

- added support for scanning Austrian passports - use `AustrianPassportRecognizerSettings`
- added support for scanning Swiss passports -  use `SwissPassportRecognizerSettings`
- added support for scanning Mexican Voting Cards - use `MRTDRecognizerSettings`

### Minor API changes:

- `RegexParserSettings` and `RawParserSettings` now work with `AbstractOCREngineOptions`, which is a base class of `BlinkOCREngineOptions`
	- default engine options returned by method `getOcrEngineOptions` for both parser settings return instance of `BlinkOCREngineOptions`

### Improvements for existing features

- improved address parsing on Malaysian iKad documents
	- affects only iKad recognizer (represented by `IKadRecognizerSettings`)
- added support for scanning non-expiring Croatian ID documents
	- affects:
		- Croatian ID front recognizer (represented by `CroatianIDFrontSideRecognizerSettings`) - date of expiry is keyword **TRAJNO**
		- Croatian ID back recognizer (represented by `CroatianIDBackSideRecognizerSettings`) - date of expiry inside MRZ is `991231`
		- Croatian ID combined recognizer (represented by `CroatianIDCombinedRecognizerSettings`)

### Bug fixes:

- fixed occassional crash in MRTD detection algorithm
	- this affects both _MRTD Recognizer_ (represented by `MRTDRecognizerSettings`) and _MRTD Detector_ (represented by `MRTDDetectorSettings`)


## 3.9.0

- bugfix in Croatian ID scanning:
    - ensured that OIB number is not returned for old ID cards, where it does not exist
- added Czech and Slovak translations
- Date fields in recognition results are returned as `com.microblink.results.date.Date` class which represents immutable dates that are consisted of day, month and year
- improved `IbanParser`:
    - improved extraction of IBANs without prefix and introduced `setAlwaysReturnPrefix` option to always return prefix (country code)
    - added support for french IBANs
- enabled reading of Pdf417 barcodes having width/height bar aspect ratio less than 2:1
- Added `VinRecognizer` for scanning VIN (*Vehicle Identification Number*) barcodes
- Added unified `BarcodeRecognizer` for scanning various tipes of barcodes
    - `ZXingRecognizer`, `BarDecoderRecognizer` and `AztecRecognizer` are **deprecated**, `BarcodeRecognizer` should be used for all barcode types that are supported by these recognizers
- `OcrLine.getChars()` method returns `CharWithVariants` array. OCR char is defined by all its parameters (value, font, position, quality, etc.) and for each resulting char it is possible to have multiple variants. For example it is possible to have same char value with different font.
- Fixed bug in SegmentScanActivity:
    - scan results are no longer hidden on shake event
- improved `IKadRecognizer`:
    - added support for long addresses and employer names
    - fixed returning of full document images as metadata
- improved `Singapore ID Recognizers`:
    - tuned reading positions
    - more accurate reading of name and blood type fields
- improved `Slovak ID Recognizers`:
    - tuned reading positions of ID elements
    - improved reading precision of address, place of birth, last name and issuing authority
    - added options to disable/enable extraction of certain fields in recognizer settings
    - result getters `getPersonalIdentificationNumber()` and `getIssuingAuthority()` are renamed to `getPersonalNumber()` and `getIssuedBy()`
- Renamed `RomanianIDFrontSideRecognitionResult` element getters for Sex and Nationality outside of the MRZ to `getNonMRZNationality()` and `getNonMRZSex()`
- For `Austrian ID Recognizers` added options to disable/enable extraction of certain fields in recognizer settings

## 3.8.1

- improved `CombinedRecognizers`:
    - better handling of names containing dashes and extra long names
- improved `TopUpParser`:
    - added option to return USSD code without prefix
- by default `MRTDRecognizer` does not return results with incorrect check digits

## 3.8.0

- introduced ability to create minimum-size AAR
	- a separate static library distribution now exists which contains a script that you can configure with features you need and it creates a AAR file which only contains features you need - this includes minimum-size native binary and only required assets. The rest (resources and java classes) can be thrown-away by ProGuard.
- `LibBlinkID` is now fully ProGuard-compatible, i.e. you no longer need to exclude `com.microblink.**` classes in your ProGuard configuration
- removed support for Android 2.3 and Android 4.0 - minimum required Android version is now Android 4.1 (API level 16)
	- devices with Android 4.0 and earlier take [less than 2% of market share](https://developer.android.com/about/dashboards/index.html#Platform) and is very costly to support them
- removed `isItalic` and `isBold` getters from `OcrChar` class
    - they always returned `false`, since OCR engine cannot accurately detect that
- removed `setLineGroupingEnabled` and `isLineGroupingEnabled` from `BlinkOCREngineOptions` because disabling line grouping completely destroyed the OCR accuracy
- added `GermanIDCombinedRecognizer`: scans front and back side of the German ID

## 3.7.1

- prefixed custom attributes to avoid name collisions with attributes from other libraries:
    - `CameraViewGroup`: renamed animateRotation to `mb_animateRotation`, animationDuration to `mb_animationDuration`, rotatable to `mb_rotatable`
    - `BaseCameraView`:  renamed initialOrientation to `mb_initialOrientation`, aspectMode to `mb_aspectMode`
- improved `MRTDRecognizer`:
    - WSA (World Goverment of World Citizens) added as valid country code when parsing MRZ
- added `USDLCombinedRecognizer`: scans face image and USDL barcode

## 3.7.0

- updated German ID recognizers:
    - instead of `GermanIDMRZSideRecognizer`, which was used for scanning front side of the older ID cards and back side of the new ID cards, there are two specialised recognizers:  `GermanIDBackSideRecognizer`  and `GermanOldIDRecognizer`
    - improved scanning accuracy of the `GermanIDFrontSideRecognizer` (name and surname)
    - splitting address from back side of the new German ID (GermanIDBackSideRecognizer) to ZIP code, city, street and house number

- better support for German passports: `GermanPassportRecognizer` reads passport data from MRZ and fields outside of the MRZ

- splitting address in `MyKadRecognizer` to street, ZIP code, city and state

- improved Croatian ID recognizers:
    - multiple scans are used for better confidence

- `TopUpParser` improvements

- `DateParser` can parse dates with month names in English (either full or abbreviated), if this option is enabled

- added support for polish IBAN without PL prefix to `IBANParser`

- fixed returning of images inside TemplatingAPI for frames when document was not correctly detected

- introduced combined recognizers:
    - `AustrianIDCombinedRecognizer`: scans front and back side of the Austrian ID
    - `CroatianIDCombinedRecognizer`: scans front and back side of the Croatian ID
    - `CzechIDCombinedRecognizer`: scans front and back side of the Czech ID
    - `MRTDCombinedRecognizer`: scans face image from any type of the document and Machine Readable Zone
    - `SerbianIDCombinedRecognizer`: scans front and back side of the Serbian ID
    - `SingaporeIDCombinedRecognizer`: scans front and back side of the Singapore ID
    - `SlovakIDCombinedRecognizer`: scans front and back side of the Slovak ID
    - `SlovenianIDCombinedRecognizer`: scans front and back side of the Slovenian ID

    Combined recognizers can be used for scanning multiple parts/sides of the document in predefined order. They combine results from individual scans to boost accuracy and merge them into the final result.

- added `VerificationFlowActivity` which is designed for scanning multiple parts/sides of the document by using provided combined recognizers

- optimised native binary size
    - 15% size reduction for `arm64-v8a` ABI
    - 14% size reduction for `x86` ABI
    - 13% size reduction for `armeabi-v7a` and `x86_64 ABIs

## 3.6.0

- added `AztecRecognizer` for scanning Aztec barcodes
- improved `TopUpParser`:
    - added suport for 14 digits long sim numbers in addition to existing lengths (12, 19, 20)

## 3.5.0

- `MobileCouponsParser` is renamed to `TopUpParser`
- improved `TopUpParser`:
    - added option to enable all prefixes at the same time (generic prefix)
- fixed layouting of provided `SegmentScanActivity` and `RandomScanActivity` in multi-window mode
- added `SimNumberRecognizer` which scans SIM numbers from barcodes
- improved `MRTDRecognizer`:
    - better support for arab MRZ
- updated `CroatianIDFrontSideRecognizer`:
    - returning sex as written on front side of a document
- added support for scanning front side of Romanian ID cards

## 3.4.1

- improved multi-window camera support
- fix layouting of default activities in multi-window mode
- fixed problem with front side Singapore recognizer

## 3.4.0

- improved quality of german ID address recognition
- added support for extracting place of birth on old German IDs
- added support for scanning IBAN from Georgia in Segment Scan
- added support for cancelling ongoing DirectAPI recognition call
- added option to allow unverified results for `MRTDRecognizer`:
    - by using method `setAllowUnverifiedResults` in `MRTDRecognizerSettings`,  it is possible to allow obtaining of results with incorrect check digits
- Singapore ID recognizer is split in two recognizers - one for front and one for back side
- added Belgian account number check to IBAN parser
- added support for Android 7 multi-window mode
- fixed autofocus bug on Huawei Honor 8
- fixed black camera on Motorola Moto Z
- made camera focusing more stable on some devices
    - _stable_ means less "jumpy" when searching for focused image
- added support for receiving of `GlareMetadata` which informs user that there is too much glare for performing recognition of ID document

## 3.3.0

- removed `RecognizerView` method `setInitialScanningPaused`. For achieving the same functionality, method `pauseScanning` should be used.
- added support for scanning IBANs that contain spaces and dashes
- improved `MRTDRecognizer`: when obtaining of full document images is enabled with `setShowFullDocument` method, recognizer returns result only after the document image has been returned.

## 3.2.0

- fixed camera management on LG X Cam
- improved IBAN parser
- _LibRecognizer.aar_ renamed to _LibBlinkID.aar_
- added support for scanning Slovenian IDs
- fixed crash on ARMv7 devices when using `ImageListener`
- added support for drawing MRZ detection result

## 3.1.0
- US Driver's Licence:
    - fixed parsing of Virgin Islands DL
    - added support for Arkansas DL
    - added support for new South Carolina DL
- Malaysian ID:
    - added support for returning face image
    - fixed wrong parsing of birth date
- fixed rare NPE in SegmentScanActivity
- workaround for camera bug on some samsung devices

## 3.0.0
- added `MobileCouponsParser` for reading prepaid codes from mobile phone coupons
- `DateParser` returns result as java `Date` object and as original date `String`
- added method `getSpecificParsedResult` to `TemplatingRecognitionResult` (`BlinkOCRRecognitionResult`) which returns specific parser results, e.g. java `Date` for `DateParser`
- added support for scanning front and back side of Serbian ID cards
- added support for scanning Malaysian iKad documents
- migrated to libc++ native runtime and used clang from NDKr12b for building the native code
    - this enabled c++14 features which will help us yield much better performance in the future

## 2.9.0
- added support for scanning front and back side of Slovak ID cards
- added support for scanning front and back side of German ID cards
- added support for Austrian DL
- improved support for scanning Croatian ID cards
- fixed crashes on Nexus 6 (Android 7.0)

## 2.8.0
- enabled reading of longer ITF barcodes
- added support for Malaysian visa
- added support for Malaysian work permits

## 2.7.1
- added support for Alberta (Canada) DL

## 2.7.0
- added support for scanning front and back side of Austrian ID cards
- added support for scanning front and back side of Czech ID cards
- improved support for scanning Croatian ID cards
- Singapore ID scanning now works even when scanning ID which is positioned upside down
- in templating API recognition data does not need to be valid anymore in classification step
- added support for scanning MRZ of green cards
- support for parsing Vehicle Identification Numbers (VINs)
- support for parsing vehicle licence plates
- renamed BlinkOCRActivity to SegmentScanActivity
- added RandomScanActivity which is similar to SegmentScanActivity but it does not force the user to scan text segments in the predefined order
- improved autofocus support on SGS6 and SGS7
- fixed memory leak in RecognitionProcessCallback, leak was caused by Recognizer singleton holding reference to both Context and MetadataListener even after termination

## 2.6.0
- added support for templating API
	- templating API gives you possiblity to combine various detectors with parsers and thus create your own recognizer that can recognize any type of document
	- check updated `BlinkID` demo app for example of how templating API could be used to scan both front and rear side of croatian ID cards
- updated `BlinkOCRActivity`:
	- `ScanConfiguration` can now define following new features:
		- whether this configuration is required or optional
		- size of field displayed
- added support for scanning North Carolina driver license barcodes
- added support for following MRZ codes:
	- austrian ID
	- kuwait ID
	- pakistani ID
- added recognizers specialized for scanning front and back side of Croatian ID cards
	- although this can be achieved using templating API, we also added dedicated recognizers for convenience
- added support for scanning Singapore ID cards

## 2.5.0
- FailedDetectionMetadata, PointsDetectionMetadata and QuadDetectionMetadata have been replaced with DetectionMetadata which now holds a DetectorResult
    - DetectorResult is more flexible as it allows more different detection types to be added in future
- fixed several possible crashes in camera management
- fixed autofocus bug on LG devices when metering areas or non-default zoom level were set
- fixed autofocus bug on LG G4 (not related to bug above)
- fixed bug which caused document number to have zeros and Os misrecognized, even if OCR was 100% correct

## 2.4.0
- fixed ANR on Samsung Galaxy S3 Mini VE
- added RegexParser which can parse almost any regular expression from OCR result

## 2.3.1
- fixed bug in parsing dates from German's Driver's licenses

## 2.3.0
- reconfigureRecognizers method now throws an error if phone does not have autofocus and at least one of new recognizers require it
- raw resources are now packed as assets
- fixed bug with isScanningPaused which sometimes returned bogus value and caused scanning to work even if initial scanning was set to be paused
- support for scanning custom camera frames via DirectAPI
- added support for scanning german driver licences
    - this is achieved by renaming UKDL recognizer to EUDL recognizer which now supports
      both UK driver's licences and german driver's licences
    - this recognizer will be expanded in future with support for other DL's from European contries
- fixed bug on some devices causing it to never start scanning if device was not shaken
- increased OCR engine initialisation speed
- improved Frame Quality Estimation on low-end devices (fixed regression introduced in v1.7.0)
- added new options to BlinkOcrEngineOptions

## 2.2.0
- support detecting on activity flip event
- fix recognition of long names in MyKad ID cards
- fixed crash in RecognizerCompatibility on ARMv7 without NEON
- added RecognizerCompatibility to javadoc
- added Sony Xperia L to OpenGL blacklist
- fixed NPE in BarcodeDetailedData

## 2.1.0
- added Detector Recognizer which gives you ability to use one of provided detectors to obtain location of object in camera frame or in image
- improved performance of conversion of [Image](https://microblink.github.io/blinkid-android/com/microblink/image/Image.html) object into Bitmap
- added support for obtaining image of scanned MyKad document
- added support for obtaining image of scanned UKDL document
- added support for scanning MyKad document via DirectAPI
- fixed crash that could be caused by quickly restarting camera activity
- fixed bug in camera layout in certain aspect ratios of camera view
- fixed bug in segment scan when put on landscape activity
- added support for scanning MRTD with wrong checkdigit on date field
- fixed bug in handling setMeteringAreas
- setMeteringAreas now receives boolean indicating whether set areas should be rotated with device
- added support for specifying camera aspect mode from XML


## 2.0.0
- new API which is easier to understand, but is not backward compatible. Please check [README](README.md) and updated demo applications for more information.
- removed support for ARMv7 devices which do not support NEON SIMD
	- this enabled us to increase recognition speed at cost of not supporting old devices like those using NVIDIA Tegra 2
	- you can check [this article](https://microblink.zendesk.com/hc/en-us/articles/206113151-Removing-support-for-devices-without-NEON-SIMD-extensions) for more information about NEON and why we use it
- added official support for Android 6.0 and it's runtime camera permissions
	- if using provided activities, the logic behind asking user to give camera permission is handled internally
	- if integrating using custom UI, you are required to ask user to give you permission to use camera. To make this easier, we have provided a _CameraPermissionManager_ class which does all heavylifting code about managing states when asking user for camera permission. Refer to demo apps to see how it is used.
- BlinkID now depends on appcompat-v7 library, instead of full android-support library.
	- even older versions of BlinkID required only features from appcompat-v7 so we now decided to make appcompat-v7 as dependency because it is much smaller than full support library and is also default dependency of all new Android apps.
- added support for scanning front side of Malaysian MyKad documents
- completely rewritten JNI bridge between Java and native code
	- this caused almost 3x increase in recognition performance in our internal tests
- fixed camera orientation bug on Nexus 5X
- DirectAPI no longer recycles Bitmap after performing recognition of it
	- this now gives you the possibility to reuse the Bitmap after it has been recognised
- **IMPORTANT** - `onScanningDone` callback method does not automatically pause scanning loop anymore. As soon as `onScanningDone` method ends, scanning will be automatically resumed without resetting state
	- if you need to reset state, please call `resetRecognitionState` in your implementation of `onScanningDone`
	- if you need to have scanning paused after `onScanningDone` ends, please call `pauseScanning` in your implementation of `onScanningDone`. Do not forget to call `resumeScanning` to resume scanning after it has been paused.
- `pauseScanning` and `resumeScanning` calls are now counted, i.e. if you call `pauseScanning` twice, you will also need to call `resumeScanning` twice to actually resume scanning
	- this is practical if you show multiple onboarding views over camera and you want the scanning paused while each is shown and you do not know in which order they will be dismissed. Now you can simply call `pauseScanning` on showing the onboarding view and `resumeScanning` on dismissing it, regardless of how many onboarding views you have
	- if you want to show onboarding help first time your scan activity starts, you can call `setInitialScanningPaused(true)` which will ensure that first time camera is started, the scanning will not automatically start - you will need to call `resumeScanning` to start scanning after your onboarding view is dismissed
- added support for `x86_64` architecture

## 1.9.0
- fixed autofocus issue on devices that do not support continuous autofocus
- improved performance and quality of United Kingdom's Driver's Licence scanning
- improved OCR quality when scanning documents with machine readable zone
- support for defining camera video resolution preset
	- to define video resolution preset via Intent, use `BlinkOCRActivity.EXTRAS_CAMERA_VIDEO_PRESET`
	- to define video resolution preset on `RecognizerView`, use method `setVideoResolutionPreset`


## 1.8.0
- added support for scanning barcodes with _BlinkID_
- added support for performing raw OCR with _BlinkID_
- improved USDL barcode parsing
	- better handling of FullName, FullAddress, Height and Weight of cardholder
- fixed crash in USDL parser
- fixed race condition causing memory leak or rare crashes
- fixed `NullPointerException` in `BaseCameraView.dispatchTouchEvent`
- fixed bug that caused returning scan result from old video frame
- fixed `NullPointerException` in camera2 management
- fixed rare race condition in gesture recognizer
- fixed segmentation fault on recognizer reconfiguration operation
- fixed freeze when camera was being quickly turned on and off
- ensured `RecognizerView` lifecycle methods are called on UI thread
- ensure `onCameraPreviewStarted` is not called if camera is immediately closed after start before the call should have taken place
- ensure `onScanningDone` is not called after `RecognizerView` has been paused, even if it had result ready just before pausing
- added support for using _BlinkID_ as camera capture API. To do that, implement following:
	- when using `RecognizerView` do not call `setRecognitionSettings` or call it with `null` or empty array
	- implement `ImageListener` interface and set the listener with `setImageListener`
	- as a reminder - you can process video frames obtained that way using DirectAPI method `recognizeImageWithSettings`
- added `BlinkIDDemoCustomSegmentScan ` demo app that demonstrates advanced integration of SegmentScan feature within custom scan activity. It also demonstrates how to perform generic OCR of full camera frame, how to draw OCR results on screen and how to obtain OcrResult object for further processing.
- all demo apps now use Maven integration method because it is much easier than importing AAR manually

## 1.7.1
- fixed NullPointerException when RecognizerSettings array element was `null`
- fixed black rectangle bug on some devices

## 1.7.0
- utilize Camera2 API on devices that support it to achieve much better frame grabbing performance
- new algorithm for determining which video frame is of good enough quality to be processed - it uses much less memory than last one and works in separate thread so it does not impact image recognition thread
	- this results in much better and faster scanning performance
- support for [defining camera metering areas](https://microblink.github.io/blinkid-android/com/microblink/view/BaseCameraView.html#setMeteringAreas(com.microblink.geometry.Rectangle[])) that will camera use to perform focus, exposure and white balance measurement.
- support for obtaining raw MRZ text even if our internal parser fails to parse it
	- in order to do that, you must [enable returning of unparsed MRTD results](https://microblink.github.io/blinkid-android/com/microblink/recognizers/ocr/mrtd/MRTDRecognizerSettings.html#setAllowUnparsedResults(boolean))
	- when obtaining [MRTD recognition result](https://microblink.github.io/blinkid-android/com/microblink/recognizers/ocr/mrtd/MRTDRecognitionResult.html), you should check if [MRZ was parsed](https://microblink.github.io/blinkid-android/com/microblink/recognizers/ocr/mrtd/MRTDRecognitionResult.html#isMRZParsed())
		- if MRZ was parsed, you can obtain parsed data as ususal
		- if MRZ was not parsed, you can obtain [raw OCR result](https://microblink.github.io/blinkid-android/com/microblink/recognizers/ocr/mrtd/MRTDRecognitionResult.html#getOcrResult()) and parse it yourself
- added support for Visa MRZ format in internal MRZ parser
- support for obtaining image of [MRZ zone](https://microblink.github.io/blinkid-android/com/microblink/recognizers/ocr/mrtd/MRTDRecognizerSettings.html#setShowMRZ(boolean)) and [full document image](https://microblink.github.io/blinkid-android/com/microblink/recognizers/ocr/mrtd/MRTDRecognizerSettings.html#setShowFullDocument(boolean)) via [ImageListener](https://microblink.github.io/blinkid-android/com/microblink/image/ImageListener.html)
- bitmaps can now be processed while [RecognizerView](https://microblink.github.io/blinkid-android/com/microblink/view/recognition/RecognizerView.html) is active using method [recognizeBitmap](https://microblink.github.io/blinkid-android/com/microblink/view/recognition/RecognizerView.html#recognizeBitmap(android.graphics.Bitmap, com.microblink.hardware.orientation.Orientation, com.microblink.view.recognition.ScanResultListener))
- removed method `resumeScanningWithoutStateReset` - method [resumeScanning](https://microblink.github.io/blinkid-android/com/microblink/view/recognition/RecognizerView.html#resumeScanning(boolean)) of [RecognizerView](https://microblink.github.io/blinkid-android/com/microblink/view/recognition/RecognizerView.html) now receives `boolean` indicating whether internal state should be reset
- by default, null quiet zone is now set to `true` in US Driver's License recognizer

## 1.6.0
- removed support for ARMv5 and ARMv6 processors (armeabi) because of their very poor scanning performance
- optimized MRZ text extraction algorithm - 0-O confusion now does not cause extremely long processing that resulted in freezing the device
- updated documentation with use cases for which BlinkID is applicable
- fixed crash in DirectAPI when recognizer was terminated in the middle of recognition process

## 1.5.0
- support for defining camera aspect mode via intent
- removed autoscale setting from USDL recognizer - autoscale is now always used
- added MRZ detector to MRTD recognizer - Machine Readable Zone location can now be detected almost everywhere on the image

## 1.4.0
- added support for defining region for scanning MRZ
- added support for scanning UK Driver's Licence, check [README](Readme.md) for details
- improved parsing of MRZ (Machine Readable Zones)
- support for defining camera zoom level
- fixed NPE when null SuccessCallback was given

## 1.3.1
- Improved parsing of some problematic US Driver's Licence barcodes

## 1.3.0
- Added support for non-standard issuing authorities

## 1.2.0
- added support for US Driver's Licence barcode scanning
- introduced new licence key format which is now mandatory (generate your free licence key on [https://microblink.com/login](https://microblink.com/login) or contact us at [http://help.microblink.com](http://help.microblink.com)

## 1.1.0
- added support for obtaining complete unparsed MRZ lines
- fixed crash on Motorola Lex755 and Sony Xperia Z

## 1.0.1
- Support for two-letter country codes

## 1.0.0
- Support for MRZ formats in passports and ID cards
- Support for integration via Maven
- Documentation update

## 0.6.0
- Scanning support for MRZ formats
