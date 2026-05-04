package com.example.presentation.sound

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

enum class SoundType { CLICK, OK, ERR }

@Singleton
class SoundEffects @Inject constructor() {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun play(type: SoundType) {
        scope.launch { runCatching { generate(type) } }
    }

    private fun generate(type: SoundType) {
        val sampleRate = 44100
        val durationSec = 0.22
        val totalSamples = (sampleRate * durationSec).toInt()
        val buffer = ShortArray(totalSamples)

        val (startFreq, endFreq) = when (type) {
            SoundType.OK -> 660.0 to 990.0
            SoundType.ERR -> 220.0 to 140.0
            SoundType.CLICK -> 520.0 to 520.0
        }

        for (i in 0 until totalSamples) {
            val t = i.toDouble() / sampleRate
            val progress = t / durationSec
            val freq = startFreq + (endFreq - startFreq) * progress
            val envelope = when {
                progress < 0.05 -> progress / 0.05
                else -> exp(-3.0 * (progress - 0.05))
            }
            val amplitude = (sin(2 * PI * freq * t) * envelope * 0.18 * Short.MAX_VALUE)
            buffer[i] = amplitude.toInt().toShort()
        }

        val track = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setSampleRate(sampleRate)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(buffer.size * 2)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()

        track.write(buffer, 0, buffer.size)
        track.play()
        scope.launch {
            kotlinx.coroutines.delay(((durationSec + 0.05) * 1000).toLong())
            runCatching {
                track.stop()
                track.release()
            }
        }
    }
}
