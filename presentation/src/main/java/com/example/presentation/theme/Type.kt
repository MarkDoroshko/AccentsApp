package com.example.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.presentation.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val Lora = FontFamily(
    Font(GoogleFont("Lora"), provider, FontWeight.Normal),
    Font(GoogleFont("Lora"), provider, FontWeight.Medium),
    Font(GoogleFont("Lora"), provider, FontWeight.SemiBold),
    Font(GoogleFont("Lora"), provider, FontWeight.Bold)
)

private val Nunito = FontFamily(
    Font(GoogleFont("Nunito"), provider, FontWeight.Normal),
    Font(GoogleFont("Nunito"), provider, FontWeight.SemiBold),
    Font(GoogleFont("Nunito"), provider, FontWeight.Bold),
    Font(GoogleFont("Nunito"), provider, FontWeight.ExtraBold)
)

object AppText {
    val Display = TextStyle(fontFamily = Lora, fontWeight = FontWeight.Bold, fontSize = 30.sp, lineHeight = 34.sp, letterSpacing = (-0.6).sp)
    val Word = TextStyle(fontFamily = Lora, fontWeight = FontWeight.SemiBold, fontSize = 56.sp, lineHeight = 62.sp, letterSpacing = (-1).sp)
    val Option = TextStyle(fontFamily = Lora, fontWeight = FontWeight.SemiBold, fontSize = 30.sp, lineHeight = 34.sp, letterSpacing = (-0.5).sp)
    val Title = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, letterSpacing = (-0.4).sp)
    val Subtitle = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, letterSpacing = (-0.2).sp)
    val Body = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal, fontSize = 15.sp, lineHeight = 22.sp)
    val Caption = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    val Button = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, letterSpacing = 0.2.sp)
    val Mono = TextStyle(fontFamily = Lora, fontWeight = FontWeight.Bold, fontSize = 64.sp, letterSpacing = (-1).sp)
}

val Typography = Typography(
    displayLarge = AppText.Display,
    headlineLarge = AppText.Title,
    titleLarge = AppText.Title,
    titleMedium = AppText.Subtitle,
    bodyLarge = AppText.Body,
    bodyMedium = AppText.Body,
    labelLarge = AppText.Button,
    labelMedium = AppText.Caption
)
