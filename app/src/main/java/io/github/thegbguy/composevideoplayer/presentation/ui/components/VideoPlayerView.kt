package io.github.thegbguy.composevideoplayer.presentation.ui.components

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlin.math.abs

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerView(
    videoUrl: String,
    isPlaying: Boolean,
    isActive: () -> Boolean,
    currentPosition: Long,
    onPositionChange: (Long) -> Unit,
    onPlaybackComplete: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isVideoActive by rememberUpdatedState(isActive)

    // Only create ExoPlayer when videoUrl changes
    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl.toUri()))
            prepare()
        }
    }

    // Handle lifecycle events to pause/resume video
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                    }
                }

                Lifecycle.Event.ON_RESUME -> {
                    if (isPlaying && isVideoActive() && !exoPlayer.isPlaying) {
                        exoPlayer.play()
                    }
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Control playback state based on isActive and isPlaying
    LaunchedEffect(isPlaying, isVideoActive) {
        if (isVideoActive() && isPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    // Seek to position if changed
    LaunchedEffect(currentPosition) {
        if (abs(exoPlayer.currentPosition - currentPosition) > 500) {
            exoPlayer.seekTo(currentPosition)
        }
    }

    // Listen for position changes
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPositionDiscontinuity(reason: Int) {
                onPositionChange(exoPlayer.currentPosition)
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    onPlaybackComplete()
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            // Release ExoPlayer when this composable leaves composition
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                player = exoPlayer
            }
        },
        update = { playerView ->
            playerView.player = exoPlayer
        }
    )
}