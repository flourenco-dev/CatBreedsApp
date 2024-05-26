package com.fabiolourenco.catbreedsapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColorScheme(
    primary = BlueOceanPrimary,
    primaryContainer = BlueOceanPrimaryVariant,
    secondary = BlueOceanSecondary,
    background = BlueOceanBackground,
    surface = BlueOceanSurface,
    onPrimary = BlueOceanOnPrimary,
    onSecondary = BlueOceanOnSecondary,
    onBackground = BlueOceanOnBackground,
    onSurface = BlueOceanOnSurface
)

private val DarkColorPalette = darkColorScheme(
    primary = BlueOceanDarkPrimary,
    primaryContainer = BlueOceanDarkPrimaryVariant,
    secondary = BlueOceanDarkSecondary,
    background = BlueOceanDarkBackground,
    surface = BlueOceanDarkSurface,
    onPrimary = BlueOceanDarkOnPrimary,
    onSecondary = BlueOceanDarkOnSecondary,
    onBackground = BlueOceanDarkOnBackground,
    onSurface = BlueOceanDarkOnSurface
)

@Composable
fun CatBreedsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}