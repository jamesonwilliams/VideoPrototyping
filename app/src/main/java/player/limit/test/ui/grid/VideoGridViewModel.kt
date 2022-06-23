package player.limit.test.ui.grid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import player.limit.test.repo.Video
import player.limit.test.repo.VideoRepository

class VideoGridViewModel(params: Params): ViewModel() {
    private val repository = params.repository
    val videos: StateFlow<List<VideoUiModel>>
        get() = _videos
    private val _videos = MutableStateFlow<List<VideoUiModel>>(emptyList())

    fun fetchVideos() {
        viewModelScope.launch {
            repository.getVideos(howMany = 6).collect { newVideos ->
                _videos.value = newVideos.map { it.toVideoUiModel() }
            }
        }
    }

    private fun Video.toVideoUiModel(): VideoUiModel {
        val format = formats.pickBestType()
        return VideoUiModel(
            id = id,
            description = "[${format.name}] $description",
            mediaUrl = formats.urls[format]!!,
            thumbnailUrl = thumbnail.url,
            dimensions = VideoUiModel.Dimensions(
                height = formats.dimensions.height,
                width = formats.dimensions.width,
            ),
            externalUrl = detailLink,
        )
    }

    private fun Video.Formats.pickBestType(): Video.Type {
        return if (Video.Type.MP4 in urls) {
            Video.Type.MP4
        } else {
            urls.keys.random()
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
