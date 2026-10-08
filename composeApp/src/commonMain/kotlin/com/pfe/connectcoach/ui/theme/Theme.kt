package com.pfe.connectcoach.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {
    val Cream = Color(0xFFFBF8F1)
    val Sand = Color(0xFFF3ECDD)
    val Beige = Color(0xFFEDE4D0)
    val Track = Color(0xFFE6DFCC)
    val Forest = Color(0xFF1F3D2B)
    val Olive = Color(0xFF6B7A3A)
    val Sage = Color(0xFFA9BFA0)
    val SageLight = Color(0xFFDCE5D3)
    val Coral = Color(0xFFC9481F)
    val Muted = Color(0xFF5E6E5C)
    val Online = Color(0xFF4E8A3E)
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
