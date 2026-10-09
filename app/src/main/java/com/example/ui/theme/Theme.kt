package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalCyberThemeMode = compositionLocalOf { CyberThemeMode.MATRIX_GREEN }

@Composable
fun HackMatrixTheme(
    themeMode: CyberThemeMode = CyberThemeMode.MATRIX_GREEN,
    content: @Composable () -> Unit
) {
    val primary = themeMode.primaryColor
    val secondary = themeMode.accentColor

    val hackerColorScheme = darkColorScheme(
        primary = primary,
        onPrimary = CyberBlack,
        primaryContainer = CyberSurfaceVariant,
        onPrimaryContainer = primary,
        secondary = secondary,
        onSecondary = CyberBlack,
        secondaryContainer = CyberSurface,
        onSecondaryContainer = secondary,
        tertiary = NeonPurple,
        background = CyberBlack,
        onBackground = TextCyberBright,
        surface = CyberDark,
        onSurface = TextCyberBright,
        surfaceVariant = CyberSurface,
        onSurfaceVariant = TextCyberDim,
        outline = CyberBorder,
        outlineVariant = CyberBorderGlow,
        error = AlertRed,
        onError = CyberBlack
    )

    CompositionLocalProvider(LocalCyberThemeMode provides themeMode) {
        MaterialTheme(
            colorScheme = hackerColorScheme,
            typography = HackerTypography,
            content = content
        )
    }
}
