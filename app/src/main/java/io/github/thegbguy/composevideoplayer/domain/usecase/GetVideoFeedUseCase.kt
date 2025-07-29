package io.github.thegbguy.composevideoplayer.domain.usecase

import io.github.thegbguy.composevideoplayer.domain.model.VideoEntry
import io.github.thegbguy.composevideoplayer.domain.repository.VideoRepository

/**
 * Use case for retrieving the list of available videos.
 *
 * @property repository The video repository to fetch data from.
 */
class GetVideoFeedUseCase(private val repository: VideoRepository) {
    /**
     * Returns the list of available videos.
     */
    operator fun invoke(): List<VideoEntry> = repository.getVideoFeed()
}