package player.limit.test.ui.grid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import player.limit.test.repo.VideoRepository
import java.lang.IllegalStateException

class VideoGridViewModel(params: Params): ViewModel() {
    private val repository = params.repository
    val videos: StateFlow<List<VideoUiModel>>
        get() = _videos
    private val _videos = MutableStateFlow<List<VideoUiModel>>(emptyList())

    fun fetchVideos() {
        viewModelScope.launch {
            repository.getVideos().collect { newVideos ->
                _videos.value = newVideos.map { newVideo ->
                    VideoUiModel(
                        id = newVideo.id,
                        mediaUrl = newVideo.mediaUrl,
                        description = newVideo.description,
                    )
                }
            }
        }
    }

    class Factory(private val params: Params) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(params::class.java).newInstance(params);
        }
    }

    data class Params(
        val repository: VideoRepository,
    )
}
