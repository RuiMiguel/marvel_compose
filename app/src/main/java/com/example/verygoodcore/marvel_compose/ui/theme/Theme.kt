package com.example.verygoodcore.marvel_compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = black,
    surface = grey,
    background = grey,
    onPrimary = white,
)

private val LightColorPalette = lightColors(
    primary = white,
    surface = lightGrey,
    background = lightGrey,
    onPrimary = black,
)

@Composable
fun SplashTheme(content: @Composable () -> Unit) {
    val colors = lightColors(primary = white, background = red)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun MainTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}