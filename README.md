# Compose Video Player

A simple demo of single page video scrolling app like tiktok built with Jetpack Compose, Clean Architecture, and Hilt for dependency injection. The key focus of this project is to demonstrate playback persistence while the app moves to background and then comes to foreground. 

## Features

- Video playback using ExoPlayer and Jetpack Compose
- Clean Architecture: data, domain, and presentation layers
- Dependency injection with Hilt (KSP)
- App-wide theming and spacing conventions
- MVVM pattern with ViewModel and state management
- **Background/Foreground Playback Management**: Videos pause when app goes to background and resume when returning to foreground
- **State Persistence**: Playback state survives process death and configuration changes using SavedStateHandle
- **Smart Video Management**: Only the active video plays, background videos are paused automatically

## How Background/Foreground Playback Persistence Works

### Lifecycle Management
The app uses Android's lifecycle events to manage video playback:

1. **Lifecycle Observer**: The `VideoPlayerView` component observes the app's lifecycle using `LifecycleEventObserver`
2. **Background Detection**: When the app goes to background (`ON_PAUSE` event), the video automatically pauses
3. **Foreground Detection**: When the app returns to foreground (`ON_RESUME` event), the video resumes if it was playing before

### State Persistence with SavedStateHandle
The app uses `SavedStateHandle` to persist playback state across:
- Process death scenarios
- Configuration changes (screen rotation, language changes)
- App restarts

```kotlin
@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    getVideoFeedUseCase: GetVideoFeedUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    // State is automatically restored from SavedStateHandle
    private val _playbackState = MutableStateFlow(
        PlaybackState(
            isPlaying = savedStateHandle.getStateFlow("isPlaying", false).value,
            currentPosition = savedStateHandle.getStateFlow("currentPosition", 0L).value,
            videoUrl = savedStateHandle.getStateFlow("videoUrl", "").value
        )
    )
}
```

### Smart Video Management
The app ensures only the active video plays by:
- Using `isActive` parameter to control which video should play
- Pausing background videos when scrolling
- Using `rememberUpdatedState` for accurate state tracking during lifecycle events

```kotlin
@Composable
fun VideoPlayerView(
    videoUrl: String,
    isPlaying: Boolean,
    isActive: () -> Boolean,  // Function to get current active state
    currentPosition: Long,
    // ...
) {
    val isVideoActive by rememberUpdatedState(isActive)
    
    LaunchedEffect(isPlaying, isVideoActive) {
        if (isVideoActive() && isPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }
}
```

### Implementation Details

```kotlin
// In VideoPlayerView.kt
DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> {
                // Pause video when app goes to background
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                }
            }
            Lifecycle.Event.ON_RESUME -> {
                // Resume video if it was playing before going to background
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
```

### Key Benefits
- **Battery Optimization**: Videos don't continue playing in background, saving battery
- **User Experience**: Seamless transition between background/foreground states
- **State Synchronization**: UI state stays in sync with actual playback state
- **Memory Management**: Proper cleanup of lifecycle observers
- **Process Death Survival**: State is preserved even if Android kills the app
- **Smart Resource Management**: Only active videos consume resources

## Tech Stack

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [ExoPlayer](https://exoplayer.dev/)
- [Hilt (KSP)](https://dagger.dev/hilt/)
- [Kotlin](https://kotlinlang.org/)
- MVVM, Clean Architecture

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/thegbguy/composevideoplayer.git
   cd composevideoplayer
   ```

2. **Open in Android Studio.**

3. **Sync Gradle and build the project.**

4. **Run on an emulator or device.**