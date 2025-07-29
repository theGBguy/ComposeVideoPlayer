package io.github.thegbguy.composevideoplayer.domain.model

/**
 * Holds the playback state for the video player.
 *
 * @property isPlaying Whether the video is currently playing.
 * @property currentPosition The current playback position in milliseconds.
 * @property videoUrl The URL of the currently loaded video.
 */
data class PlaybackState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val videoUrl: String = ""
)