package io.github.thegbguy.composevideoplayer.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.thegbguy.composevideoplayer.domain.model.PlaybackState
import io.github.thegbguy.composevideoplayer.domain.model.VideoEntry
import io.github.thegbguy.composevideoplayer.domain.usecase.GetVideoFeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel for managing video playback state and video feed.
 *
 * @constructor Injects the GetVideoFeedUseCase for retrieving the video feed and SavedStateHandle for state persistence.
 */
@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    getVideoFeedUseCase: GetVideoFeedUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _playbackState = MutableStateFlow(
        PlaybackState(
            isPlaying = savedStateHandle.getStateFlow("isPlaying", false).value,
            currentPosition = savedStateHandle.getStateFlow("currentPosition", 0L).value,
            videoUrl = savedStateHandle.getStateFlow("videoUrl", "").value
        )
    )
    val playbackState: StateFlow<PlaybackState> = _playbackState

    /**
     * The list of available videos, provided by the use case.
     */
    val videoFeed: List<VideoEntry> = getVideoFeedUseCase()

    private val _activeVideoIndex = MutableStateFlow(
        savedStateHandle.getStateFlow("activeVideoIndex", 0).value
    )
    val activeVideoIndex: StateFlow<Int> = _activeVideoIndex

    /**
     * Sets whether the video is playing and saves to SavedStateHandle.
     */
    fun setPlaying(isPlaying: Boolean) {
        _playbackState.value = _playbackState.value.copy(isPlaying = isPlaying)
        savedStateHandle["isPlaying"] = isPlaying
    }

    /**
     * Sets the current playback position and saves to SavedStateHandle.
     */
    fun setCurrentPosition(position: Long) {
        _playbackState.value = _playbackState.value.copy(currentPosition = position)
        savedStateHandle["currentPosition"] = position
    }

    /**
     * Sets the current video URL and saves to SavedStateHandle.
     */
    fun setVideoUrl(url: String) {
        _playbackState.value = _playbackState.value.copy(videoUrl = url)
        savedStateHandle["videoUrl"] = url
    }

    /**
     * Sets the active video by index and resets playback state.
     * Saves the active video index to SavedStateHandle.
     */
    fun setActiveVideo(index: Int) {
        _activeVideoIndex.value = index
        savedStateHandle["activeVideoIndex"] = index
        setVideoUrl(videoFeed[index].url)
        setCurrentPosition(0)
        setPlaying(true)
    }
}