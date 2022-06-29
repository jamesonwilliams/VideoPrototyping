package player.proto.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import player.proto.repo.Video
import player.proto.repo.VideoRepository

class FeedViewModel(params: Params): ViewModel() {
    private val repository = params.repository
    val feedItems: StateFlow<List<FeedItemUiModel>>
        get() = _videos
    private val _videos = MutableStateFlow<List<FeedItemUiModel>>(emptyList())

    fun fetchFeedItems() {
        viewModelScope.launch {
            repository.getVideos(howMany = 8).collect { newVideos ->
                _videos.value = newVideos.map { it.toVideoUiModel() }
            }
        }
    }

    private fun Video.toVideoUiModel(): FeedItemUiModel {
        val format = formats.pickBestType()
        return FeedItemUiModel(
            id = id,
            description = "[${format.name}] $description",
            mediaUrl = formats.urls[format]!!,
            thumbnailUrl = thumbnail.url,
            dimensions = FeedItemUiModel.Dimensions(
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
            return modelClass.getConstructor(params::class.java).newInstance(params)
        }
    }

    data class Params(
        val repository: VideoRepository,
    )
}
