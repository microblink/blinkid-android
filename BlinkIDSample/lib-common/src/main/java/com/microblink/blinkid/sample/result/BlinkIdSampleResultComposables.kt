package com.microblink.blinkid.sample.result

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microblink.blinkid.core.result.SingleSideScanningResult
import com.microblink.blinkid.core.result.StringResult
import com.microblink.blinkid.core.result.barcode.BarcodeResult
import com.microblink.blinkid.core.result.mrz.MrzResult
import com.microblink.blinkid.core.result.viz.VizResult
import com.microblink.blinkid.core.session.BlinkIdScanningResult
import com.microblink.blinkid.sample.ui.theme.Cobalt
import com.microblink.blinkid.sample.ui.theme.Cobalt400
import com.microblink.blinkid.sample.ui.theme.Cobalt50
import com.microblink.blinkid.sample.ui.theme.Cobalt800
import com.microblink.blinkid.sample.ui.theme.DeepBlue
import com.microblink.blinkid.sample.ui.theme.ErrorRed
import com.microblink.blinkid.core.result.DateResult

@Composable
fun BlinkIdSampleResultScreen(
    result: BlinkIdScanningResult? = null,
    onNavigateUp: () -> Unit
) {
    result?.let { result ->
        val vehicleClassInfoList: List<SampleResult> = buildList {
            result.driverLicenseDetailedInfo?.vehicleClassesInfo?.forEachIndexed { num, it ->
                add(
                    SampleResult(
                        "Vehicle class info ${num}",
                        children = listOf(
                            SampleResult(
                                title = "Vehicle class",
                                value = it.vehicleClass?.values?.joinToString(" ")
                            ),
                            SampleResult(
                                title = "Licence type",
                                value = it.licenceType?.values?.joinToString(" ")
                            ),
                            SampleResult(
                                title = "Effective date",
                                value = it.effectiveDate?.toString()
                            ),
                            SampleResult(
                                title = "Expiry date",
                                value = it.expiryDate?.toString()
                            )
                        )
                    )
                )
            }
        }

        val dependentInfoList: List<SampleResult> = buildList {
            result.dependentsInfo?.forEachIndexed { num, it ->
                add(
                    SampleResult(
                        "Dependent info ${num}",
                        children = listOf(
                            SampleResult(
                                "Date of birth",
                                value = it.dateOfBirth?.toString()
                            ),
                            SampleResult(
                                "Sex",
                                value = it.sex?.values?.joinToString(
                                    " "
                                )
                            ),
                            SampleResult(
                                "Document number",
                                value = it.documentNumber?.values?.joinToString(
                                    " "
                                )
                            ),
                            SampleResult(
                                "Full name",
                                value = it.fullName?.values?.joinToString(
                                    " "
                                )
                            )
                        )
                    )
                )
            }
        }

        val dataMatchList: List<SampleResult> = buildList {
            result.dataMatchResult?.statePerField?.forEachIndexed { num, it ->
                add(
                    SampleResult(
                        title = it.fieldType.name,
                        value = it.state.name
                    )
                )
            }
        }

        val resultScreenList: List<SampleResultTab> = buildList {
            add(
                SampleResultTab(
                    "Full result",
                    listOf(
                        SampleResult(
                            title = "Document class info",
                            children = listOf(
                                SampleResult(
                                    title = "Country",
                                    value = result.documentClassInfo.country?.name
                                ),
                                SampleResult(
                                    title = "Region",
                                    value = result.documentClassInfo.region?.name
                                ),
                                SampleResult(
                                    title = "Type",
                                    value = result.documentClassInfo.type?.name
                                ),
                            )
                        ),
                        SampleResult(
                            "Data match result",
                            children = dataMatchList
                        ),
                        SampleResult(
                            "First name",
                            value = result.firstName?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Last name",
                            value = result.lastName?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Full name",
                            value = result.fullName?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Additional name information",
                            value = result.additionalNameInformation?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Localized name",
                            value = result.localizedName?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Fathers name",
                            value = result.fathersName?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Mothers name",
                            value = result.mothersName?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Address",
                            value = result.address?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Additional address information",
                            value = result.additionalAddressInformation?.values?.joinToString(
                                " "
                            )
                        ),
                        SampleResult(
                            "Additional optional address information",
                            value = result.additionalOptionalAddressInformation?.values?.joinToString(
                                " "
                            )
                        ),
                        SampleResult(
                            "Place of birth",
                            value = result.placeOfBirth?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Nationality",
                            value = result.nationality?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Race",
                            value = result.race?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Religion",
                            value = result.religion?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Profession",
                            value = result.profession?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Marital status",
                            value = result.maritalStatus?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Residential status",
                            value = result.residentialStatus?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Employer",
                            value = result.employer?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Sex",
                            value = result.sex?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Sponsor",
                            value = result.sponsor?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Blood type",
                            value = result.bloodType?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Document number",
                            value = result.documentNumber?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Personal ID number",
                            value = result.personalIdNumber?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Document additional number",
                            value = result.documentAdditionalNumber?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Document optional additional number",
                            value = result.documentOptionalAdditionalNumber?.values?.joinToString(
                                " "
                            )
                        ),
                        SampleResult(
                            "Additional personal ID number",
                            value = result.additionalPersonalIdNumber?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Issuing authority",
                            value = result.issuingAuthority?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Document subtype",
                            value = result.documentSubtype?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Remarks",
                            value = result.remarks?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Residence permit type",
                            value = result.residencePermitType?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Manufacturing year",
                            value = result.manufacturingYear?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Vehicle type",
                            value = result.vehicleType?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Eligibility category",
                            value = result.eligibilityCategory?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Specific document validity",
                            value = result.specificDocumentValidity?.values?.joinToString(" ")
                        ),
                        SampleResult(
                            "Visa type",
                            value = result.visaType?.values?.joinToString(" ")
                        ),
                        SampleDateStringResult("Date of birth", result.dateOfBirth),
                        SampleDateStringResult("Date of issue", result.dateOfIssue),
                        SampleDateStringResult("Date of expiry", result.dateOfExpiry),
                        SampleResult(
                            "Date of expiry permanent",
                            value = result.dateOfExpiryPermanent?.toString()
                        ),
                        SampleResult(
                            "Driver license detailed info",
                            children = listOf(
                                SampleResult(
                                    title = "Restrictions",
                                    value = result.driverLicenseDetailedInfo?.restrictions?.values?.joinToString(
                                        " "
                                    )
                                ),
                                SampleResult(
                                    title = "Endorsements",
                                    value = result.driverLicenseDetailedInfo?.endorsements?.values?.joinToString(
                                        " "
                                    )
                                ),
                                SampleResult(
                                    title = "Vehicle class",
                                    value = result.driverLicenseDetailedInfo?.vehicleClass?.values?.joinToString(
                                        " "
                                    )
                                ),
                                SampleResult(
                                    title = "Conditions",
                                    value = result.driverLicenseDetailedInfo?.conditions?.values?.joinToString(
                                        " "
                                    )
                                ),
                                SampleResult(
                                    "Vehicle classes info",
                                    children = vehicleClassInfoList
                                ),
                            ),
                        ), SampleResult(
                            "Dependents info",
                            children = dependentInfoList
                        )
                    )
                )
            )

            result.subResults.forEachIndexed { num, pageRes ->
                pageRes.viz?.let { viz ->
                    add(VizResultTab(num, viz))
                }
                pageRes.mrz?.let { mrz ->
                    add(MrzResultTab(num, mrz))
                }
                pageRes.barcode?.let { barcode ->
                    add(BarcodeResultTab(num, barcode))
                }
                // TODO: Serialization is quite slow here - fix it
                add(ImagesResultTab(num, result.subResults[num]))

            }
        }

        SampleResultScreen(
            resultTabs = resultScreenList,
            onNavigateUp = onNavigateUp
        )
    }
}

fun MrzResultTab(pageNum: Int, mrz: MrzResult): SampleResultTab {
    return SampleResultTab(
        "MRZ $pageNum",
        listOf(
            SampleResult(
                "MRZ String",
                value = mrz.rawMRZString
            ),
            SampleResult(
                "Document code",
                value = mrz.documentCode
            ),
            SampleResult(
                "Issuer",
                value = mrz.issuer
            ),
            SampleResult(
                "Document number",
                value = mrz.documentNumber
            ),
            SampleResult(
                "Opt1",
                value = mrz.opt1
            ),
            SampleResult(
                "Opt2",
                value = mrz.opt2
            ),
            SampleResult(
                "Gender",
                value = mrz.gender
            ),
            SampleResult(
                "Nationality",
                value = mrz.nationality
            ),
            SampleResult(
                "Primary ID",
                value = mrz.primaryID
            ),
            SampleResult(
                "Secondary ID",
                value = mrz.secondaryID
            ),
            SampleResult(
                "Issuer name",
                value = mrz.issuerName
            ),
            SampleResult(
                "Nationality name",
                value = mrz.nationalityName
            ),
            SampleResult(
                "Verified",
                value = mrz.verified.toString()
            ),
            SampleDateString(
                "Date of birth",
                date = mrz.dateOfBirth
            ),
            SampleDateString(
                "Date of expiry",
                date = mrz.dateOfExpiry
            ),
            SampleResult(
                "Document type",
                value = mrz.documentType.toString()
            )
        )
    )
}

fun VizResultTab(pageNum: Int, result: VizResult): SampleResultTab {
    return SampleResultTab(
        "Viz $pageNum",
        listOf(
            SampleResult(
                "First name",
                value = result.firstName?.values?.joinToString(" ")
            ),
            SampleResult(
                "Last name",
                value = result.lastName?.values?.joinToString(" ")
            ),
            SampleResult(
                "Full name",
                value = result.fullName?.values?.joinToString(" ")
            ),
            SampleResult(
                "Additional name information",
                value = result.additionalNameInformation?.values?.joinToString(" ")
            ),
            SampleResult(
                "Localized name",
                value = result.localizedName?.values?.joinToString(" ")
            ),
            SampleResult(
                "Fathers name",
                value = result.fathersName?.values?.joinToString(" ")
            ),
            SampleResult(
                "Mothers name",
                value = result.mothersName?.values?.joinToString(" ")
            ),
            SampleResult(
                "Address",
                value = result.address?.values?.joinToString(" ")
            ),
            SampleResult(
                "Additional address information",
                value = result.additionalAddressInformation?.values?.joinToString(
                    " "
                )
            ),
            SampleResult(
                "Additional optional address information",
                value = result.additionalOptionalAddressInformation?.values?.joinToString(
                    " "
                )
            ),
            SampleResult(
                "Place of birth",
                value = result.placeOfBirth?.values?.joinToString(" ")
            ),
            SampleResult(
                "Nationality",
                value = result.nationality?.values?.joinToString(" ")
            ),
            SampleResult(
                "Race",
                value = result.race?.values?.joinToString(" ")
            ),
            SampleResult(
                "Religion",
                value = result.religion?.values?.joinToString(" ")
            ),
            SampleResult(
                "Profession",
                value = result.profession?.values?.joinToString(" ")
            ),
            SampleResult(
                "Marital status",
                value = result.maritalStatus?.values?.joinToString(" ")
            ),
            SampleResult(
                "Residential status",
                value = result.residentialStatus?.values?.joinToString(" ")
            ),
            SampleResult(
                "Employer",
                value = result.employer?.values?.joinToString(" ")
            ),
            SampleResult(
                "Sex",
                value = result.sex?.values?.joinToString(" ")
            ),
            SampleResult(
                "Sponsor",
                value = result.sponsor?.values?.joinToString(" ")
            ),
            SampleResult(
                "Blood type",
                value = result.bloodType?.values?.joinToString(" ")
            ),
            SampleResult(
                "Document number",
                value = result.documentNumber?.values?.joinToString(" ")
            ),
            SampleResult(
                "Personal ID number",
                value = result.personalIdNumber?.values?.joinToString(" ")
            ),
            SampleResult(
                "Document additional number",
                value = result.documentAdditionalNumber?.values?.joinToString(" ")
            ),
            SampleResult(
                "Document optional additional number",
                value = result.documentOptionalAdditionalNumber?.values?.joinToString(
                    " "
                )
            ),
            SampleResult(
                "Additional personal ID number",
                value = result.additionalPersonalIdNumber?.values?.joinToString(" ")
            ),
            SampleResult(
                "Issuing authority",
                value = result.issuingAuthority?.values?.joinToString(" ")
            ),
            SampleResult(
                "Document subtype",
                value = result.documentSubtype?.values?.joinToString(" ")
            ),
            SampleResult(
                "Remarks",
                value = result.remarks?.values?.joinToString(" ")
            ),
            SampleResult(
                "Residence permit type",
                value = result.residencePermitType?.values?.joinToString(" ")
            ),
            SampleResult(
                "Manufacturing year",
                value = result.manufacturingYear?.values?.joinToString(" ")
            ),
            SampleResult(
                "Vehicle type",
                value = result.vehicleType?.values?.joinToString(" ")
            ),
            SampleResult(
                "Eligibility category",
                value = result.eligibilityCategory?.values?.joinToString(" ")
            ),
            SampleResult(
                "Specific document validity",
                value = result.specificDocumentValidity?.values?.joinToString(" ")
            ),
            SampleResult(
                "Visa type",
                value = result.visaType?.values?.joinToString(" ")
            ),
            SampleDateStringResult(
                "Date of birth",
                date = result.dateOfBirth
            ),
            SampleDateStringResult(
                "Date of issue",
                date = result.dateOfIssue
            ),
            SampleDateStringResult(
                "Date of expiry",
                date = result.dateOfExpiry
            ),
            SampleResult(
                "Date of expiry permanent",
                value = result.dateOfExpiryPermanent.toString()
            )
        )
    )
}

fun BarcodeResultTab(pageNum: Int, result: BarcodeResult): SampleResultTab {
    val barcodeElements = ""
    result.extendedElements.barcodeElements.forEach {
        barcodeElements.plus("${it.key.name}: ${it.value}\n")
    }

    return SampleResultTab(
        "Barcode $pageNum",
        listOf(
            SampleResult(
                "Barcode data",
                children = listOf(
                    SampleResult(
                        "String data",
                        value = result.barcodeData.stringData
                    ),
                    SampleResult(
                        "Raw data",
                        value = result.barcodeData.rawData.contentToString()
                    ),
                    SampleResult(
                        "Barcode type",
                        value = result.barcodeData.barcodeType.toString()
                    ),
                    SampleResult(
                        "Uncertain",
                        value = result.barcodeData.uncertain.toString()
                    )
                )
            ),
            SampleResult(
                "First name",
                value = result.firstName
            ),
            SampleResult(
                "Last name",
                value = result.lastName
            ),
            SampleResult(
                "Full name",
                value = result.fullName
            ),
            SampleResult(
                "Additional name information",
                value = result.additionalNameInformation
            ),
            SampleResult(
                "Address",
                value = result.address
            ),
            SampleResult(
                "Place of birth",
                value = result.placeOfBirth
            ),
            SampleResult(
                "Nationality",
                value = result.nationality
            ),
            SampleResult(
                "Race",
                value = result.race
            ),
            SampleResult(
                "Religion",
                value = result.religion
            ),
            SampleResult(
                "Profession",
                value = result.profession
            ),
            SampleResult(
                "Marital status",
                value = result.maritalStatus
            ),
            SampleResult(
                "Residential status",
                value = result.residentialStatus
            ),
            SampleResult(
                "Employer",
                value = result.employer
            ),
            SampleResult(
                "Sex",
                value = result.sex
            ),
            SampleDateString(
                "Date of birth",
                date = result.dateOfBirth
            ),
            SampleDateString(
                "Date of issue",
                date = result.dateOfIssue
            ),
            SampleDateString(
                "Date of expiry",
                date = result.dateOfExpiry
            ),
            SampleResult(
                "Document number",
                value = result.documentNumber
            ),
            SampleResult(
                "Personal ID number",
                value = result.personalIdNumber
            ),
            SampleResult(
                "Document additional number",
                value = result.documentAdditionalNumber
            ),
            SampleResult(
                "Issuing authority",
                value = result.issuingAuthority
            ), SampleResult(
                "Address detailed info",
                children = listOf(
                    SampleResult(
                        "Street",
                        value = result.addressDetailedInfo.street
                    ),
                    SampleResult(
                        "Postal code",
                        value = result.addressDetailedInfo.postalCode
                    ),
                    SampleResult(
                        "City",
                        value = result.addressDetailedInfo.city
                    ),
                    SampleResult(
                        "Jurisdiction",
                        value = result.addressDetailedInfo.jurisdiction
                    )
                )
            ), SampleResult(
                "Driver license detailed info",
                children = listOf(
                    SampleResult(
                        title = "Restrictions",
                        value = result.driverLicenseDetailedInfo.restrictions
                    ),
                    SampleResult(
                        title = "Endorsements",
                        value = result.driverLicenseDetailedInfo.endorsements
                    ),
                    SampleResult(
                        title = "Vehicle class",
                        value = result.driverLicenseDetailedInfo.vehicleClass
                    ),
                    SampleResult(
                        title = "Conditions",
                        value = result.driverLicenseDetailedInfo.conditions
                    )
                ),
                // TODO: add vehicle classes info
            ),
            SampleResult(
                "Barcode elements",
                value = barcodeElements
            )
        )
    )
}

fun SampleDateStringResult(title: String, date: DateResult<StringResult?>?): SampleResult {
    return SampleResult(
        title,
        children = listOf(
            SampleResult(
                "Date",
                value = date.toString()
            ),
            SampleResult(
                "Original string",
                value = date?.originalString?.values?.joinToString(
                    " "
                )
            ),
            SampleResult(
                "Filled by domain knowledge",
                value = date?.filledByDomainKnowledge.toString()
            ),
            SampleResult(
                "Successfully parsed",
                value = date?.successfullyParsed.toString()
            )
        )
    )
}

fun SampleDateString(title: String, date: DateResult<String>?): SampleResult {
    return SampleResult(
        title,
        children = listOf(
            SampleResult(
                "Date",
                value = date.toString()
            ),
            SampleResult(
                "Original string",
                value = date?.originalString
            ),
            SampleResult(
                "Filled by domain knowledge",
                value = date?.filledByDomainKnowledge.toString()
            ),
            SampleResult(
                "Successfully parsed",
                value = date?.successfullyParsed.toString()
            )
        )
    )
}

fun ImagesResultTab(pageNum: Int, result: SingleSideScanningResult): SampleResultTab {
    val imageTabs = mutableListOf<SampleResultImage>()

    // TODO: add all image details as well
    result.inputImage?.let {
        imageTabs.add(
            SampleResultImage(
                "Input image",
                image = it.bitmap
            )
        )
    }
    result.documentImage?.let {
        imageTabs.add(
            SampleResultImage(
                "Cropped document image",
                image = it.bitmap
            )
        )

    }
    result.faceImage?.let {
        imageTabs.add(
            SampleResultImage(
                "Face image",
                image = it.bitmap
            )
        )
    }
    result.signatureImage?.let {
        imageTabs.add(
            SampleResultImage(
                "Signature image",
                image = it.bitmap
            )
        )

    }
    return SampleResultTab(
        "Images $pageNum",
        imageTabs
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SampleResultScreen(
    resultTabs: List<SampleResultTab>,
    onNavigateUp: () -> Unit
) {
    BackHandler(true, onNavigateUp)
    var activeTab by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Blink ID Sample", style = TestMenuTitleText)
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Cobalt800,
                    titleContentColor = Color.White
                )
            )
        },
        content = { padding ->
            Surface(
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding())
                    .fillMaxSize(),
                color = Cobalt50
            ) {
                Column {
                    FlowRow(
                        Modifier
                            .fillMaxWidth()
                            .background(White)
                    ) {
                        resultTabs.forEachIndexed { num, it ->
                            it.title?.let {
                                Column(
                                    Modifier
                                        .padding(4.dp)
                                        .border(1.dp, DeepBlue, RoundedCornerShape(40.dp))
                                        .clip(RoundedCornerShape(40.dp))
                                        .background(if (activeTab == num) ErrorRed else White)
                                        .clickable { activeTab = num }
                                        .padding(10.dp)
                                ) {
                                    Text(it, color = Cobalt800, style = ResultValueText)
                                }
                            }
                        }
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Cobalt50)
                    ) {
                        resultTabs[activeTab].results.let {
                            if (it.isNotEmpty()) {
                                items(items = it, itemContent = {
                                    SampleResultRow(
                                        node = it,
                                        level = 0,
                                        onClick = {}
                                    )
                                })
                            }
                        }
                    }
                }
            }
        })
}


@Composable
fun SampleResultRow(
//    node: SampleResult,
    node: Result,
    level: Int,
    onClick: () -> Unit,
    showDivider: Boolean = true,
) {
    var expanded by rememberSaveable {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .animateContentSize(
                spring(
                    stiffness = Spring.StiffnessLow,
                    visibilityThreshold = IntSize.VisibilityThreshold,
                    dampingRatio = Spring.DampingRatioNoBouncy
                )
            )
    ) {
        if (node is SampleResult) {
            SampleResultRowText(node, level, expanded, showDivider) {
                expanded = !expanded
                onClick()
            }
            if (expanded) {
                node.children?.let {
                    it.forEach {
                        SampleResultRow(
                            node = it, level = level + 1,
                            onClick = onClick
                        )
                    }
                }
            }
        } else if (node is SampleResultImage) {
            SampleResultRowImage(node, level, expanded, showDivider) { }
        }
    }
}

@Composable
fun SampleResultRowText(
    result: SampleResult,
    level: Int,
    isExpanded: Boolean,
    showDivider: Boolean = true,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .background(if (level == 0) Cobalt50 else Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .padding(start = (10 * level).dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
//                onClick()
            },
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(0.85f)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = result.title.toString().uppercase(),
                style = ResultTitleText,
                color = if (level == 0) Cobalt800 else if (level == 1) Cobalt else Cobalt400
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                result.value?.let {
                    Text(
                        modifier = Modifier,
                        text = it,
                        style = ResultValueText,
                        color = Black
                    )
                }
                result.subValue?.let {
                    Text(
                        modifier = Modifier,
                        text = it,
                        style = ResultValueText,
                        color = Black
                    )
                }
            }
        }
        result.children?.let {
            val chevronRotation by animateFloatAsState(
                targetValue = if (isExpanded) 90f else 0f,
                label = ""
            )
            Column(
                modifier = Modifier
                    .weight(0.15f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 16.dp)
                        .rotate(chevronRotation),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null
                )
            }
        }
    }
    if (showDivider) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            color = DeepBlue,
            thickness = if (level == 0) 2.dp else 1.dp
        )
    }
}

@Composable
fun SampleResultRowImage(
    result: SampleResultImage,
    level: Int,
    isExpanded: Boolean,
    showDivider: Boolean = true,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .background(if (level == 0) Cobalt50 else Color.White)
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .padding(start = (10 * level).dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { },
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(0.85f)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = result.title.toString().uppercase(),
                style = ResultTitleText,
                color = if (level == 0) Cobalt800 else if (level == 1) Cobalt else Cobalt400
            )
            result.image?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(it.asImageBitmap(), result.title)
                }
            }
        }
        result.children?.let {
            val chevronRotation by animateFloatAsState(
                targetValue = if (isExpanded) 90f else 0f,
                label = ""
            )
            Column(
                modifier = Modifier
                    .weight(0.15f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 16.dp)
                        .rotate(chevronRotation),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null
                )
            }
        }
    }
    if (showDivider) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            color = DeepBlue,
            thickness = if (level == 0) 2.dp else 1.dp
        )
    }
}

sealed class Result {
    abstract val title: String?
    abstract val children: List<Any>?
    abstract val description: String?
}

data class SampleResult(
    override val title: String?,
    override val children: List<SampleResult>? = null,
    override val description: String? = null,
    val value: String? = null,
    val subValue: String? = null,
) : Result()

data class SampleResultImage(
    override val title: String?,
    override val children: List<SampleResult>? = null,
    override val description: String? = null,
    val image: Bitmap? = null
) : Result()

data class SampleResultTab(
    val title: String?,
    val results: List<Result>
)

val TestMenuTitleText = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    lineHeight = 24.sp,
)

val TestMenuDescriptionText = TextStyle(
    fontWeight = FontWeight.Thin,
    fontSize = 12.sp,
    lineHeight = 16.sp,
)

val ResultTitleText = TextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
    lineHeight = 6.sp
)

val ResultValueText = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    lineHeight = 24.sp,
)