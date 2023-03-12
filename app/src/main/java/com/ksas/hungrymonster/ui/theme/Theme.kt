package com.ksas.hungrymonster.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = darkColors(
    primary = Grey1,
    primaryVariant = Color.Blue,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Yellow,
    onSecondary = Color.Black,
    onSurface = Black2,
    surface = Color.White,
    error = RedErrorLight,
    onError = RedErrorDark,
    background = Grey1,
    onBackground = Color.Black
)

private val DarkColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    secondaryVariant = Violet,
    onSecondary = Color.White,
    error = RedErrorDark,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun HungryMonsterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
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