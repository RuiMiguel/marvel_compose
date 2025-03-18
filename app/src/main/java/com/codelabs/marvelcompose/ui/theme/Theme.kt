package com.codelabs.marvelcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = Black,
    surface = Grey,
    background = Grey,
    onPrimary = White,
)

private val LightColorPalette = lightColorScheme(
    primary = White,
    surface = LightGrey,
    background = LightGrey,
    onPrimary = Black,
)

@Composable
fun SplashTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(primary = White, background = Red)

    MaterialTheme(
        colorScheme = colors,
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
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}