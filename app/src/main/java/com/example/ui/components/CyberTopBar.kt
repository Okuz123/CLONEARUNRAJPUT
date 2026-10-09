package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.CyberSoundSynthesizer
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberDark
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberThemeMode
import com.example.ui.theme.LocalCyberThemeMode

@Composable
fun CyberTopBar(
    soundEnabled: Boolean,
    onToggleSound: () -> Unit,
    scanlinesEnabled: Boolean,
    onToggleScanlines: () -> Unit,
    onLaunchMatrixRain: () -> Unit,
    currentTheme: CyberThemeMode,
    onSelectTheme: (CyberThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val themeMode = LocalCyberThemeMode.current
    val primaryColor = themeMode.primaryColor
    var showThemeMenu by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(CyberDark)
            .border(width = 1.dp, color = CyberBorder)
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .testTag("cyber_top_bar")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left HUD Identity
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(primaryColor.copy(alpha = pulseAlpha))
                )
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "HACK_MATRIX",
                            color = primaryColor,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = " [v3.1]",
                            color = primaryColor.copy(alpha = 0.6f),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp
                        )
                    }
                    Text(
                        text = "SEC_LEVEL: 0 // NODE: INDIA_HQ // SIM_ONLY",
                        color = primaryColor.copy(alpha = 0.7f),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 9.sp
                    )
                }
            }

            // Right HUD Action Controls
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Matrix digital rain trigger
                IconButton(
                    onClick = {
                        CyberSoundSynthesizer.playScanSweep()
                        onLaunchMatrixRain()
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("action_matrix_rain")
                ) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = "Matrix Digital Rain",
                        tint = primaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // CRT Scanlines toggle
                IconButton(
                    onClick = {
                        CyberSoundSynthesizer.playKeyClick()
                        onToggleScanlines()
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("action_toggle_scanlines")
                ) {
                    Icon(
                        imageVector = Icons.Default.GridOn,
                        contentDescription = "Toggle CRT Scanlines",
                        tint = if (scanlinesEnabled) primaryColor else primaryColor.copy(alpha = 0.35f),
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Cyber Sound Synth toggle
                IconButton(
                    onClick = {
                        onToggleSound()
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("action_toggle_sound")
                ) {
                    Icon(
                        imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                        contentDescription = "Toggle Audio Feedback",
                        tint = if (soundEnabled) primaryColor else primaryColor.copy(alpha = 0.35f),
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Theme palette switcher
                Box {
                    IconButton(
                        onClick = {
                            CyberSoundSynthesizer.playKeyClick()
                            showThemeMenu = true
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("action_theme_palette")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Theme Color Scheme",
                            tint = primaryColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = showThemeMenu,
                        onDismissRequest = { showThemeMenu = false },
                        modifier = Modifier
                            .background(CyberSurface)
                            .border(1.dp, CyberBorder, RoundedCornerShape(4.dp))
                    ) {
                        CyberThemeMode.entries.forEach { mode ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .clip(CircleShape)
                                                .background(mode.primaryColor)
                                        )
                                        Text(
                                            text = "  ${mode.displayName}",
                                            color = mode.primaryColor,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 12.sp,
                                            fontWeight = if (mode == currentTheme) FontWeight.Bold else FontWeight.Normal
                                        )
                                    }
                                },
                                onClick = {
                                    onSelectTheme(mode)
                                    CyberSoundSynthesizer.playSuccess()
                                    showThemeMenu = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
