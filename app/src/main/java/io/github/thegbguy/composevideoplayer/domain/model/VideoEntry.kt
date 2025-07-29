package io.github.thegbguy.composevideoplayer.domain.model

/**
 * Represents a video entry in the app.
 *
 * @property id Unique identifier for the video.
 * @property url The URL of the video.
 * @property description Description of the video.
 */
data class VideoEntry(
    val id: Int,
    val url: String,
    val description: String = ""
)