package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Hacker Cyberpunk Palette
val CyberBlack = Color(0xFF06090F)
val CyberDark = Color(0xFF0B101A)
val CyberSurface = Color(0xFF101726)
val CyberSurfaceVariant = Color(0xFF172236)
val CyberBorder = Color(0xFF1F3048)
val CyberBorderGlow = Color(0xFF2A4365)

// Matrix Neon Green
val MatrixGreen = Color(0xFF00FF66)
val MatrixGreenDim = Color(0xFF00AA44)
val MatrixGreenDark = Color(0xFF003816)
val MatrixGreenGlow = Color(0x3300FF66)

// Cyber Cyan
val CyberCyan = Color(0xFF00F0FF)
val CyberCyanDim = Color(0xFF0099AA)
val CyberCyanDark = Color(0xFF002E38)

// Amber Phosphor CRT
val AmberNeon = Color(0xFFFFB000)
val AmberDim = Color(0xFFAA7500)
val AmberDark = Color(0xFF382600)

// Neon Crimson / Red Alert
val AlertRed = Color(0xFFFF1744)
val AlertRedDim = Color(0xFFAA0E2E)
val AlertRedDark = Color(0xFF3B000C)

// Neon Purple / Synth
val NeonPurple = Color(0xFFBD00FF)
val NeonPurpleDim = Color(0xFF7A00AA)

// Text Colors
val TextCyberBright = Color(0xFFE6FFF2)
val TextCyberDim = Color(0xFF88A0B8)
val TextCyberMuted = Color(0xFF4D6580)
val TextTerminalGreen = Color(0xFF00FF66)

enum class CyberThemeMode(val displayName: String, val primaryColor: Color, val accentColor: Color) {
    MATRIX_GREEN("Matrix Green", MatrixGreen, CyberCyan),
    CYBER_CYAN("Cyber Cyan", CyberCyan, NeonPurple),
    AMBER_CRT("Amber CRT", AmberNeon, AlertRed),
    RED_ALERT("Red Alert", AlertRed, AmberNeon)
}
