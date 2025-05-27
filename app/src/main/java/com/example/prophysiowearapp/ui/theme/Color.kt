package com.example.prophysiowearapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Red400 = Color(0xFFCF6679)

val Blue = Color(0xFF2196F3)
val Orange = Color(0xFFFF9800)
val Red = Color(0xFFE91E63)
val Green = Color(0xFF4CAF50)

internal val wearColorPalette: Colors = Colors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    secondaryVariant = Teal200,
    error = Red400,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black,
    background = Color(0xFF101010),
    onBackground = Color.White,
    surface = Color(0xFF1C1C1C),
    onSurface = Color.White
)
