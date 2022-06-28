package player.proto.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import player.proto.repo.Video
import player.proto.repo.VideoRepository

class VideoPagerViewModel(params: Params) : ViewModel() {
    private val repository = params.repository
    private val _videos = MutableStateFlow<List<VideoUiModel>>(emptyList())
    val videos: StateFlow<List<VideoUiModel>>
        get() = _videos

    fun fetchVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getVideos(20)
                .collect { videos ->
                    withContext(Dispatchers.Main) {
                        _videos.value = videos.map { it.toUiModel() }
                    }
                }
        }
    }

    private fun Video.toUiModel(): VideoUiModel {
        return VideoUiModel(
            id = id,
            url = formats.urls[Video.Type.MP4] ?: formats.urls[Video.Type.DASH]!!,
            title = description,
        )
    }

    class Factory(private val params: Params) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(params::class.java).newInstance(params)
        }
    }

    data class Params(
        val repository: VideoRepository,
    )
}
