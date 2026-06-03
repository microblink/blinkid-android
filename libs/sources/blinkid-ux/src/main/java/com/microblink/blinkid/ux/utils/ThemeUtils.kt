package com.microblink.blinkid.ux.utils

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.FontRes
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontListFontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.ResourceFont
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.microblink.blinkid.ux.theme.UiTypography
import com.microblink.blinkid.ux.utils.ParcelableFont.Companion.write
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

/**
 * Parcelable version of [TextStyle] that can be used in [ParcelableUiTypography].
 * Customizations are limited to the most important [TextStyle] parameters.
 * Font family is represented as a list of [ParcelableFont] objects.
 *
 * @property fontFamily List of [ParcelableFont] objects representing the font family. Defaults to `null`.
 * @property fontWeight Font weight. Defaults to `null`.
 * @property fontSize Font size. Defaults to `null`.
 * @property lineHeight Line height. Defaults to `null`.
 */
@Parcelize
data class ParcelableTextStyle @JvmOverloads constructor(
    val fontFamily: List<ParcelableFont>? = null,
    val fontWeight: FontWeight? = null,
    val fontSize: TextUnit? = null,
    val lineHeight: TextUnit? = null
) : Parcelable {
    companion object : Parceler<ParcelableTextStyle> {
        override fun ParcelableTextStyle.write(
            parcel: Parcel,
            flags: Int
        ) {
            parcel.writeInt(fontFamily?.size ?: 0)
            fontFamily?.forEach {
                it.write(parcel, flags)
            }
            parcel.writeValue(fontWeight?.weight)
            parcel.writeValue(fontSize?.value)
            parcel.writeValue(lineHeight?.value)
        }

        override fun create(parcel: Parcel): ParcelableTextStyle {
            val fonts = mutableListOf<ParcelableFont>()
            val fontCount = parcel.readInt()
            repeat(fontCount) {
                fonts.add(ParcelableFont.create(parcel))
            }
            val weight = parcel.readValue(Int::class.java.classLoader)
            val size = parcel.readValue(Float::class.java.classLoader)
            val height = parcel.readValue(Float::class.java.classLoader)
            return ParcelableTextStyle(
                fontFamily = fonts,
                fontWeight = weight?.let { FontWeight(it as Int) },
                fontSize = size?.let { TextUnit(it as Float, TextUnitType.Sp) },
                lineHeight = height?.let { TextUnit(it as Float, TextUnitType.Sp) }
            )
        }
    }
}

/**
 * Parcelable version of [Font] that can be used in [ParcelableUiTypography].
 * Customizations are limited to the most important [Font] parameters.
 *
 * @property resId Resource ID of the font.
 * @property weight Font weight. Defaults to [FontWeight.Normal].
 * @property style Font style. Defaults to [FontStyle.Normal].
 */
@Parcelize
data class ParcelableFont @JvmOverloads constructor(
    @param:FontRes val resId: Int,
    val weight: FontWeight = FontWeight.Normal,
    val style: FontStyle = FontStyle.Normal,
) : Parcelable {
    companion object : Parceler<ParcelableFont> {
        override fun ParcelableFont.write(
            parcel: Parcel,
            flags: Int
        ) {
            parcel.writeInt(resId)
            parcel.writeInt(weight.weight)
            parcel.writeInt(style.value)
        }

        override fun create(parcel: Parcel): ParcelableFont {
            val resId = parcel.readInt()
            val weight = FontWeight(parcel.readInt())
            val styleOrdinal = parcel.readInt()
            val style = when (styleOrdinal) {
                0 -> FontStyle.Normal
                1 -> FontStyle.Italic
                else -> FontStyle.Normal
            }
            return ParcelableFont(resId, weight, style)
        }
    }
}

fun Font.toParcelableFont(resId: Int): ParcelableFont {
    return ParcelableFont(resId, this.weight, this.style)
}

fun ParcelableFont.toFont(): Font {
    return Font(resId = this.resId, weight = this.weight, style = this.style)
}

fun List<ParcelableFont>.toFontFamily(): FontFamily? {
    if (this.isEmpty()) return null
    return FontFamily(this.map { it.toFont() })
}

fun TextStyle.toParcelableTextStyle(): ParcelableTextStyle {
    val fontFamilyList: FontListFontFamily? = this.fontFamily as? FontListFontFamily
    return ParcelableTextStyle(
        fontFamily = fontFamilyList?.fonts?.map { (it as ResourceFont).toParcelableFont(it.resId) },
        fontWeight = this.fontWeight,
        fontSize = this.fontSize,
        lineHeight = this.lineHeight
    )
}

fun ParcelableTextStyle.toTextStyle(): TextStyle {
    val size = this.fontSize
    val height = this.lineHeight

    return TextStyle(
        fontSize = size ?: TextUnit.Unspecified,
        fontWeight = this.fontWeight,
        fontFamily = this.fontFamily?.toFontFamily(),
        lineHeight = height ?: TextUnit.Unspecified
    )
}

/**
 * Defines the typography used in the Verify activity UI.
 *
 * This `Typography` object specifies the text styles for various UI elements
 * in the verification flow, such as headlines, labels, body text, and titles.
 * It provides a consistent look and feel throughout the UI. Customizations
 * are limited to the most important [TextStyle] and [Font] parameters through
 * [ParcelableTextStyle] and [ParcelableFont] classes.
 */
@Parcelize
data class ParcelableUiTypography(
    val onboardingTitle: ParcelableTextStyle,
    val onboardingText: ParcelableTextStyle,
    val onboardingButton: ParcelableTextStyle,
    val helpDialogTitle: ParcelableTextStyle,
    val helpDialogText: ParcelableTextStyle,
    val helpDialogButton: ParcelableTextStyle,
    val errorDialogTitle: ParcelableTextStyle,
    val errorDialogText: ParcelableTextStyle,
    val errorDialogButton: ParcelableTextStyle,
    val scanningInstructions: ParcelableTextStyle,
    val helpTooltip: ParcelableTextStyle,
    val loadingScreen: ParcelableTextStyle
) : Parcelable {
    companion object {
        /**
         * Reuses the default typography from [com.microblink.blinkid.ux.theme.UiTypography] and converts it to [ParcelableUiTypography].
         *
         *  @param fontFamily Font family to be used in the SDK. By using this constructor,
         *  all of the original sizes, weights, and line heights of the SDK remain, while the
         *  font changes. To change other font parameters, create a new Typography object.
         */
        @JvmStatic
        fun Default(fontFamily: FontFamily?): ParcelableUiTypography =
            ParcelableUiTypography(
                onboardingText = UiTypography.Default(fontFamily).onboardingText.toParcelableTextStyle(),
                onboardingTitle = UiTypography.Default(fontFamily).onboardingTitle.toParcelableTextStyle(),
                onboardingButton = UiTypography.Default(fontFamily).onboardingButton.toParcelableTextStyle(),
                helpDialogTitle = UiTypography.Default(fontFamily).helpDialogTitle.toParcelableTextStyle(),
                helpDialogText = UiTypography.Default(fontFamily).helpDialogText.toParcelableTextStyle(),
                helpDialogButton = UiTypography.Default(fontFamily).helpDialogButton.toParcelableTextStyle(),
                errorDialogTitle = UiTypography.Default(fontFamily).errorDialogTitle.toParcelableTextStyle(),
                errorDialogText = UiTypography.Default(fontFamily).errorDialogText.toParcelableTextStyle(),
                errorDialogButton = UiTypography.Default(fontFamily).errorDialogButton.toParcelableTextStyle(),
                scanningInstructions = UiTypography.Default(fontFamily).scanningInstructions.toParcelableTextStyle(),
                helpTooltip = UiTypography.Default(fontFamily).helpTooltip.toParcelableTextStyle(),
                loadingScreen = UiTypography.Default(fontFamily).loadingScreen.toParcelableTextStyle()
            )
    }
}

fun ParcelableUiTypography.toUiTypography(): UiTypography {
    return UiTypography(
        onboardingText = this.onboardingText.toTextStyle(),
        onboardingTitle = this.onboardingTitle.toTextStyle(),
        onboardingButton = this.onboardingButton.toTextStyle(),
        helpDialogTitle = this.helpDialogTitle.toTextStyle(),
        helpDialogText = this.helpDialogText.toTextStyle(),
        helpDialogButton = this.helpDialogButton.toTextStyle(),
        errorDialogTitle = this.errorDialogTitle.toTextStyle(),
        errorDialogText = this.errorDialogText.toTextStyle(),
        errorDialogButton = this.errorDialogButton.toTextStyle(),
        scanningInstructions = this.scanningInstructions.toTextStyle(),
        helpTooltip = this.helpTooltip.toTextStyle(),
        loadingScreen = this.loadingScreen.toTextStyle()
    )
}