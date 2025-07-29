package io.github.thegbguy.composevideoplayer.model

data class VideoEntry(
    val id: Int,
    val url: String,
    val description: String = ""
)