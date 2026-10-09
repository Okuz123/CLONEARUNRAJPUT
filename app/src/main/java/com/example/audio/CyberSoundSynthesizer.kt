package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sin

object CyberSoundSynthesizer {
    var isSoundEnabled: Boolean = true

    private val audioScope = CoroutineScope(Dispatchers.Default)
    private const val SAMPLE_RATE = 22050

    fun playKeyClick() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playTone(frequency = 1200.0, durationMs = 12, volume = 0.18f, type = WaveType.CLICK)
        }
    }

    fun playCommandExec() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playTone(frequency = 880.0, durationMs = 45, volume = 0.25f, type = WaveType.SINE)
        }
    }

    fun playSuccess() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playTone(frequency = 660.0, durationMs = 35, volume = 0.25f, type = WaveType.SINE)
            playTone(frequency = 990.0, durationMs = 60, volume = 0.28f, type = WaveType.SINE)
        }
    }

    fun playError() {
        if (!isSoundEnabled) return
        audioScope.launch {
            playTone(frequency = 220.0, durationMs = 80, volume = 0.35f, type = WaveType.SQUARE)
        }
    }

    fun playScanSweep() {
        if (!isSoundEnabled) return
        audioScope.launch {
            val numSamples = (SAMPLE_RATE * 0.15).toInt()
            val buffer = ShortArray(numSamples)
            for (i in 0 until numSamples) {
                val progress = i.toDouble() / numSamples
                val currentFreq = 400.0 + (progress * 1600.0)
                val angle = 2.0 * Math.PI * i * (currentFreq / SAMPLE_RATE)
                val sample = (sin(angle) * 0.22 * Short.MAX_VALUE).toInt()
                buffer[i] = sample.toShort()
            }
            writeAndPlay(buffer)
        }
    }

    private enum class WaveType { SINE, CLICK, SQUARE }

    private fun playTone(frequency: Double, durationMs: Int, volume: Float, type: WaveType) {
        val numSamples = (SAMPLE_RATE * (durationMs / 1000.0)).toInt().coerceAtLeast(100)
        val buffer = ShortArray(numSamples)

        for (i in 0 until numSamples) {
            val angle = 2.0 * Math.PI * i / (SAMPLE_RATE / frequency)
            val envelope = when {
                i < numSamples * 0.15 -> i / (numSamples * 0.15)
                i > numSamples * 0.75 -> (numSamples - i) / (numSamples * 0.25)
                else -> 1.0
            }
            val sample = when (type) {
                WaveType.SINE -> (sin(angle) * volume * envelope * Short.MAX_VALUE).toInt()
                WaveType.CLICK -> ((if (i % 8 < 4) 1.0 else -1.0) * volume * envelope * Short.MAX_VALUE * 0.7).toInt()
                WaveType.SQUARE -> ((if (sin(angle) >= 0) 1.0 else -1.0) * volume * envelope * Short.MAX_VALUE * 0.6).toInt()
            }
            buffer[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
        }
        writeAndPlay(buffer)
    }

    private fun writeAndPlay(buffer: ShortArray) {
        try {
            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(SAMPLE_RATE)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(buffer, 0, buffer.size)
            audioTrack.play()
            // release after playing
            Thread.sleep((buffer.size.toDouble() / SAMPLE_RATE * 1000).toLong() + 20)
            audioTrack.stop()
            audioTrack.release()
        } catch (_: Exception) {
            // AudioTrack failure fallback safely ignored
        }
    }
}
