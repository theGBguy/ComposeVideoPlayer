package io.github.thegbguy.composevideoplayer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.thegbguy.composevideoplayer.ui.components.VideoPlayerView
import io.github.thegbguy.composevideoplayer.ui.theme.LocalSpacing
import io.github.thegbguy.composevideoplayer.viewmodel.VideoPlayerViewModel
import kotlinx.coroutines.launch

@Composable
fun VideoPlayerScreen(
    modifier: Modifier = Modifier,
    viewModel: VideoPlayerViewModel = viewModel()
) {
    val state by viewModel.playbackState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val localSpacing = LocalSpacing.current
    val activeVideoIndex by viewModel.activeVideoIndex.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(
        initialPage = activeVideoIndex,
        pageCount = { viewModel.videoFeed.size }
    )

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setActiveVideo(pagerState.currentPage)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(localSpacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState
        ) { page ->
            val video = viewModel.videoFeed[page]
            VideoPlayerView(
                videoUrl = video.url,
                isPlaying = page == activeVideoIndex && state.isPlaying,
                currentPosition = if (page == activeVideoIndex) state.currentPosition else 0L,
                onPositionChange = { viewModel.setCurrentPosition(it) },
                onPlaybackComplete = {
                    scope.launch {
                        pagerState.scrollToPage(page + 1)
                    }
                }
            )
        }
    }
}