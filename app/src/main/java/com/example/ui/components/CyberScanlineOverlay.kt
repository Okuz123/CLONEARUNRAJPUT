package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CyberScanlineOverlay(
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    if (!enabled) return

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val step = 4.dp.toPx()
        val height = size.height
        val width = size.width
        var y = 0f

        while (y < height) {
            drawLine(
                color = Color.Black.copy(alpha = 0.18f),
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.2f
            )
            y += step
        }
    }
}
