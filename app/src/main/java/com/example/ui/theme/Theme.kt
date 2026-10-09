package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BharatSaffronDark,
    onPrimary = BharatSurfaceLight,
    primaryContainer = BharatSaffronLight,
    onPrimaryContainer = BharatSaffronDark,
    secondary = BharatNavy,
    onSecondary = BharatSurfaceLight,
    secondaryContainer = BharatNavyLight,
    onSecondaryContainer = BharatNavy,
    tertiary = BharatGreen,
    onTertiary = BharatSurfaceLight,
    tertiaryContainer = BharatGreenLight,
    onTertiaryContainer = BharatGreen,
    background = BharatBgLight,
    onBackground = TextPrimaryLight,
    surface = BharatSurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFF0F2F5),
    onSurfaceVariant = TextSecondaryLight,
    outline = BharatCardBorder,
    outlineVariant = Color(0xFFE8EAF0)
)

private val DarkColorScheme = darkColorScheme(
    primary = BharatSaffron,
    onPrimary = BharatBgDark,
    primaryContainer = Color(0xFF4A2800),
    onPrimaryContainer = BharatSaffronLight,
    secondary = Color(0xFF64B5F6),
    onSecondary = BharatBgDark,
    secondaryContainer = Color(0xFF0D2D5B),
    onSecondaryContainer = Color(0xFFBBDEFB),
    tertiary = Color(0xFF81C784),
    onTertiary = BharatBgDark,
    tertiaryContainer = Color(0xFF1B4D20),
    onTertiaryContainer = Color(0xFFC8E6C9),
    background = BharatBgDark,
    onBackground = TextPrimaryDark,
    surface = BharatSurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = Color(0xFF282828),
    onSurfaceVariant = TextSecondaryDark,
    outline = BharatCardBorderDark,
    outlineVariant = Color(0xFF383838)
)

@Composable
fun BharatTechTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = BharatTypography,
        content = content
    )
}
