package player.limit.test.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import player.limit.test.R
import player.limit.test.data.*

class VideoResourceRepository(private val resourceLoader: ResourceLoader): VideoRepository {
    override suspend fun getVideos(howMany: Int): Flow<List<Video>> {
        return resourceLoader.readString(R.raw.recommended)
            .let { Json.decodeFromString<RecommendedVideos>(it) }
            .data
            .recommendedMediaFeed
            .elements
            .edges
            .shuffled()
            .subList(0, howMany)
            .map { it.toVideo() }
            .let { flowOf(it) }
    }

    private fun Edge.toVideo(): Video {
        return with (node) {
            Video(
                id = id,
                description = title,
                detailLink = url,
                duration = media.packagedMedia.muxedMp4s?.recommended?.duration,
                formats = media.toFormats(),
                thumbnail = media.still.source.toVideoThumbnail()
            )
        }
    }

    private fun Media.toFormats(): Video.Formats {
        return Video.Formats(
            urls = toUrls(),
            dimensions = Video.Dimensions(
                height = still.source.dimensions.height,
                width = still.source.dimensions.width,
            )
        )
    }

    private fun Media.toUrls(): Map<Video.Type, String> {
        return mutableMapOf<Video.Type, String>().apply {
            packagedMedia.muxedMp4s?.recommended?.url?.let { mp4Url ->
                put(Video.Type.MP4, mp4Url)
            }
            put(Video.Type.DASH, streaming.dashUrl)
        }.toMap()
    }

    private fun Source.toVideoThumbnail(): Video.Thumbnail {
        return Video.Thumbnail(
            url = url,
            dimensions = Video.Dimensions(
                height = dimensions.height,
                width = dimensions.width,
            )
        )
    }
}
