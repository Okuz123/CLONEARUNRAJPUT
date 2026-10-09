package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.LocalCyberThemeMode
import kotlin.random.Random

private val MATRIX_CHARS = "010101XYZΩλπ∑∆∇<>#/%{}[]=+-*&^!@~HACKMATRIXSARVAMKRUTRIMBASH".toCharArray()

data class RainDrop(
    var x: Float,
    var y: Float,
    var speed: Float,
    var length: Int,
    var chars: CharArray
)

@Composable
fun MatrixRainAnimation(
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false,
    onDismiss: () -> Unit = {}
) {
    val themeMode = LocalCyberThemeMode.current
    val primaryColor = themeMode.primaryColor

    val infiniteTransition = rememberInfiniteTransition(label = "matrix_rain")
    val animationTick by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "matrix_tick"
    )

    val drops = remember {
        List(28) {
            RainDrop(
                x = it * 38f + 10f,
                y = Random.nextFloat() * 1200f,
                speed = Random.nextFloat() * 12f + 8f,
                length = Random.nextInt(10, 24),
                chars = CharArray(24) { MATRIX_CHARS[Random.nextInt(MATRIX_CHARS.size)] }
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isFullScreen) CyberBlack.copy(alpha = 0.95f) else Color.Transparent)
            .then(if (isFullScreen) Modifier.clickable { onDismiss() } else Modifier)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasHeight = size.height
            val canvasWidth = size.width

            // Read animationTick to drive continuous recomposition
            val _tick = animationTick

            val paint = android.graphics.Paint().apply {
                isAntiAlias = true
                typeface = android.graphics.Typeface.MONOSPACE
                textSize = 28f
            }

            drops.forEach { drop ->
                drop.y += drop.speed
                if (drop.y - (drop.length * 28f) > canvasHeight) {
                    drop.y = -Random.nextFloat() * 200f
                    drop.speed = Random.nextFloat() * 14f + 8f
                }

                // Randomly mutate characters for digital glitch effect
                if (Random.nextInt(10) == 0) {
                    val indexToMutate = Random.nextInt(drop.chars.size)
                    drop.chars[indexToMutate] = MATRIX_CHARS[Random.nextInt(MATRIX_CHARS.size)]
                }

                for (i in 0 until drop.length) {
                    val charY = drop.y - (i * 28f)
                    if (charY in -20f..canvasHeight + 20f) {
                        val alpha = (1f - (i.toFloat() / drop.length)).coerceIn(0.1f, 1f)
                        val colorInt = if (i == 0) {
                            // Leading head character is bright white/cyan
                            android.graphics.Color.WHITE
                        } else {
                            val r = (primaryColor.red * 255).toInt()
                            val g = (primaryColor.green * 255).toInt()
                            val b = (primaryColor.blue * 255).toInt()
                            val a = (alpha * 240).toInt()
                            android.graphics.Color.argb(a, r, g, b)
                        }

                        paint.color = colorInt
                        val charToDraw = drop.chars[i % drop.chars.size].toString()
                        drawContext.canvas.nativeCanvas.drawText(
                            charToDraw,
                            drop.x % canvasWidth,
                            charY,
                            paint
                        )
                    }
                }
            }
        }

        if (isFullScreen) {
            Text(
                text = ">>> MATRIX STREAM ACTIVE [TAP TO EXIT] <<<",
                color = primaryColor,
                fontSize = 12.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            )
        }
    }
}
