package io.github.thegbguy.composevideoplayer.data.repository

import io.github.thegbguy.composevideoplayer.domain.model.VideoEntry
import io.github.thegbguy.composevideoplayer.domain.repository.VideoRepository

/**
 * Implementation of [VideoRepository] that provides a static list of videos.
 */
class VideoRepositoryImpl : VideoRepository {
    override fun getVideoFeed(): List<VideoEntry> = listOf(
        VideoEntry(1, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "Big Buck Bunny"),
        VideoEntry(2, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", "Elephants Dream"),
        VideoEntry(3, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4", "For Bigger Blazes"),
        VideoEntry(4, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4", "For Bigger Escapes"),
        VideoEntry(5, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4", "For Bigger Fun"),
        VideoEntry(6, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4", "For Bigger Joyrides"),
        VideoEntry(7, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4", "For Bigger Meltdowns"),
        VideoEntry(8, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4", "Sintel"),
        VideoEntry(9, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4", "Subaru Outback On Street And Dirt"),
        VideoEntry(10, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4", "Tears Of Steel"),
        VideoEntry(11, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4", "We Are Going On Bullrun"),
        VideoEntry(12, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4", "What Car Can You Get For A Grand")
    )
}