package io.github.thegbguy.composevideoplayer.model

data class PlaybackState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val videoUrl: String = ""
)