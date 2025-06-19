package com.example.prophysiowearapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Typography
import androidx.compose.ui.graphics.Color

val wearColorPalette = Colors(
    primary = Purple200,
    secondary = Teal200,
    surface = Purple700,
    onSurface = Color.White
)

val wearTypography = Typography() // Aquí creas la tipografía predeterminada

@Composable
fun ProPhysioWearAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = wearColorPalette,
        typography = wearTypography,
        content = content
    )
}
