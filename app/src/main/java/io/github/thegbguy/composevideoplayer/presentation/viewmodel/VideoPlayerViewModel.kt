package io.github.thegbguy.composevideoplayer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import io.github.thegbguy.composevideoplayer.domain.model.PlaybackState
import io.github.thegbguy.composevideoplayer.domain.model.VideoEntry
import io.github.thegbguy.composevideoplayer.domain.usecase.GetVideoFeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel for managing video playback state and video feed.
 *
 * @constructor Injects the GetVideoFeedUseCase for retrieving the video feed.
 */
@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    getVideoFeedUseCase: GetVideoFeedUseCase
) : ViewModel() {
    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState

    /**
     * The list of available videos, provided by the use case.
     */
    val videoFeed: List<VideoEntry> = getVideoFeedUseCase()

    private val _activeVideoIndex = MutableStateFlow(0)
    val activeVideoIndex: StateFlow<Int> = _activeVideoIndex

    /**
     * Sets whether the video is playing.
     */
    fun setPlaying(isPlaying: Boolean) {
        _playbackState.value = _playbackState.value.copy(isPlaying = isPlaying)
    }

    /**
     * Sets the current playback position.
     */
    fun setCurrentPosition(position: Long) {
        _playbackState.value = _playbackState.value.copy(currentPosition = position)
    }

    /**
     * Sets the current video URL.
     */
    fun setVideoUrl(url: String) {
        _playbackState.value = _playbackState.value.copy(videoUrl = url)
    }

    /**
     * Sets the active video by index and resets playback state.
     */
    fun setActiveVideo(index: Int) {
        _activeVideoIndex.value = index
        setVideoUrl(videoFeed[index].url)
        setCurrentPosition(0)
        setPlaying(true)
    }
}