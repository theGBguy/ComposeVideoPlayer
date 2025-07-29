package io.github.thegbguy.composevideoplayer.viewmodel

import androidx.lifecycle.ViewModel
import io.github.thegbguy.composevideoplayer.model.PlaybackState
import io.github.thegbguy.composevideoplayer.model.VideoEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VideoPlayerViewModel : ViewModel() {
    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState

    val videoFeed = listOf(
        VideoEntry(
            1,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            "Big Buck Bunny"
        ),
        VideoEntry(
            2,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "Elephants Dream"
        ),
        VideoEntry(
            3,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            "For Bigger Blazes"
        ),
        VideoEntry(
            4,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            "For Bigger Escapes"
        ),
        VideoEntry(
            5,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            "For Bigger Fun"
        ),
        VideoEntry(
            6,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            "For Bigger Joyrides"
        ),
        VideoEntry(
            7,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            "For Bigger Meltdowns"
        ),
        VideoEntry(
            8,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            "Sintel"
        ),
        VideoEntry(
            9,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            "Subaru Outback On Street And Dirt"
        ),
        VideoEntry(
            10,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            "Tears Of Steel"
        ),
        VideoEntry(
            11,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            "We Are Going On Bullrun"
        ),
        VideoEntry(
            12,
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
            "What Car Can You Get For A Grand"
        )
    )

    private val _activeVideoIndex = MutableStateFlow(0)
    val activeVideoIndex: StateFlow<Int> = _activeVideoIndex

    fun setPlaying(isPlaying: Boolean) {
        _playbackState.value = _playbackState.value.copy(isPlaying = isPlaying)
    }

    fun setCurrentPosition(position: Long) {
        _playbackState.value = _playbackState.value.copy(currentPosition = position)
    }

    fun setVideoUrl(url: String) {
        _playbackState.value = _playbackState.value.copy(videoUrl = url)
    }

    fun setActiveVideo(index: Int) {
        _activeVideoIndex.value = index
        setVideoUrl(videoFeed[index].url)
        setCurrentPosition(0)
        setPlaying(true)
    }

    fun onPlaybackComplete(page: Int) {
        val lastIndex = videoFeed.size - 1
        if (page < lastIndex) {
            setActiveVideo(page + 1)
        }
    }
}