/**
 * Copyright (c) Microblink. Modifications are allowed under the terms of the
 * license for files located in the UX/UI lib folder.
 */

package com.microblink.blinkid.ux.components

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.annotation.RawRes
import com.microblink.blinkid.ux.R

/**
 * Holds the shared [SoundPool] and its loading state for the scan beep.
 *
 * The scan sound must play immediately when a scan succeeds, so the sound is preloaded and its
 * state is cached here for the duration of the scanning session. All access is synchronized on
 * this object.
 *
 * @property soundPool The shared [SoundPool] instance, or `null` if not initialized.
 * @property soundSampleId Sample handle returned by [SoundPool.load], used for [SoundPool.play].
 * @property loadedResId Raw resource ID of the currently loaded sound, used to detect when a
 * different sound is requested and a reload is needed.
 * @property isLoaded Whether the asynchronous [SoundPool.load] has completed, meaning the sample
 * can be played immediately.
 */
private object ScanSoundPlayerState {
    var soundPool: SoundPool? = null
    var soundSampleId: Int = 0
    var loadedResId: Int = 0
    var isLoaded: Boolean = false
}

/**
 * Preloads the scan completion sound into a shared [SoundPool].
 *
 * Call this before scanning starts to reduce playback latency when a scan result is captured.
 * If the requested [soundResId] is already loaded, this function is a no-op.
 *
 * @param context Context used to access the raw sound resource.
 * @param soundResId Raw resource ID of the sound to preload.
 */
fun preloadScanBeep(context: Context, @RawRes soundResId: Int = R.raw.mb_blinkid_beep) {
    synchronized(ScanSoundPlayerState) {
        if (ScanSoundPlayerState.soundPool != null && ScanSoundPlayerState.loadedResId == soundResId) {
            return
        }
        releaseScanBeep()
        val appContext = context.applicationContext
        val soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()
        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0 && sampleId == ScanSoundPlayerState.soundSampleId) {
                ScanSoundPlayerState.isLoaded = true
            }
        }
        ScanSoundPlayerState.soundPool = soundPool
        ScanSoundPlayerState.loadedResId = soundResId
        ScanSoundPlayerState.soundSampleId = soundPool.load(appContext, soundResId, 1)
    }
}

/**
 * Plays the scan completion sound.
 *
 * If the sound has not been preloaded, it is loaded first and played as soon as loading
 * completes.
 *
 * @param context Context used to access the raw sound resource.
 * @param soundResId Raw resource ID of the sound to play.
 */
fun playScanBeep(context: Context, @RawRes soundResId: Int = R.raw.mb_blinkid_beep) {
    synchronized(ScanSoundPlayerState) {
        if (ScanSoundPlayerState.soundPool == null || ScanSoundPlayerState.loadedResId != soundResId) {
            preloadScanBeep(context, soundResId)
        }
        val soundPool = ScanSoundPlayerState.soundPool ?: return
        val playSound = {
            soundPool.play(ScanSoundPlayerState.soundSampleId, 1f, 1f, 1, 0, 1f)
        }
        if (ScanSoundPlayerState.isLoaded) {
            playSound()
        } else {
            soundPool.setOnLoadCompleteListener { _, sampleId, status ->
                if (status == 0 && sampleId == ScanSoundPlayerState.soundSampleId) {
                    ScanSoundPlayerState.isLoaded = true
                    playSound()
                }
            }
        }
    }
}

/**
 * Releases the shared scan beep resources.
 *
 * Call this when the scan UI is disposed or scanning is no longer active to free the underlying
 * [SoundPool] and reset the cached sound state.
 */
fun releaseScanBeep() {
    synchronized(ScanSoundPlayerState) {
        ScanSoundPlayerState.soundPool?.release()
        ScanSoundPlayerState.soundPool = null
        ScanSoundPlayerState.soundSampleId = 0
        ScanSoundPlayerState.loadedResId = 0
        ScanSoundPlayerState.isLoaded = false
    }
}
