package io.github.thegbguy.composevideoplayer.domain.repository

import io.github.thegbguy.composevideoplayer.domain.model.VideoEntry

/**
 * Repository interface for accessing video data.
 */
interface VideoRepository {
    /**
     * Returns the list of available videos.
     */
    fun getVideoFeed(): List<VideoEntry>
}