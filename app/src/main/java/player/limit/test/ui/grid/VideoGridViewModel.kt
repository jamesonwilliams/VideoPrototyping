package player.limit.test.ui.grid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import player.limit.test.repo.Video
import player.limit.test.repo.VideoRepository
import player.limit.test.ui.grid.VideoUiModel.DisplayMode.*

class VideoGridViewModel(params: Params): ViewModel() {
    private val repository = params.repository
    val videos: StateFlow<List<VideoUiModel>>
        get() = _videos
    private val _videos = MutableStateFlow<List<VideoUiModel>>(emptyList())

    fun fetchVideos() {
        viewModelScope.launch {
            repository.getVideos(howMany = 20).collect { newVideos ->
                withContext(Dispatchers.Main) {
                    _videos.value = newVideos.map { it.toVideoUiModel() }
                }
            }
        }
    }

    fun playVideo(id: String) {
        _videos.value = videos.value.map {
            if (it.id == id) {
                it.copy(displayMode = VIDEO)
            } else {
                it.copy(displayMode = STILL)
            }
        }
    }

    private fun Video.toVideoUiModel(): VideoUiModel {
        return VideoUiModel(
            id = id,
            externalUrl = detailLink,
            description = "[IMG] $description",
            dimensions = VideoUiModel.Dimensions(
                height = thumbnail.dimensions.height,
                width = thumbnail.dimensions.width,
            ),
            imageUrl = thumbnail.url,
            mediaUrl = formats.urls[formats.pickBestType()]!!,
            displayMode = STILL,
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
            return modelClass.getConstructor(params::class.java).newInstance(params)
        }
    }

    data class Params(
        val repository: VideoRepository,
    )
}
