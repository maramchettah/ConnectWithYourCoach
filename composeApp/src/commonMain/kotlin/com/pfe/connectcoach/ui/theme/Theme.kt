package com.pfe.connectcoach.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {
    val Cream = Color(0xFFF6F1D1)      // screen background (warm yellow-cream)
    val Card = Color(0xFFFFFBE6)       // cards & bars
    val Sand = Color(0xFFEAE3A8)
    val Beige = Color(0xFFE8E2B0)
    val Track = Color(0xFFDCD89A)
    val Forest = Color(0xFF2A5446)     // deep teal-green
    val Olive = Color(0xFF7BA328)      // leaf green
    val Sage = Color(0xFF9CCB5E)
    val SageLight = Color(0xFFD3E9A0)
    val Coral = Color(0xFFF2611D)      // orange actions
    val CoralDark = Color(0xFFC24A0E)
    val Muted = Color(0xFF56693F)
    val Online = Color(0xFF3FA02B)
}

private val Scheme = lightColorScheme(
    primary = AppColors.Forest,
    onPrimary = AppColors.Cream,
    secondary = AppColors.Coral,
    background = AppColors.Cream,
    surface = Color.White,
    onSurface = AppColors.Forest,
    onBackground = AppColors.Forest,
)

@Composable
fun ConnectCoachTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, typography = Typography(), content = content)
}