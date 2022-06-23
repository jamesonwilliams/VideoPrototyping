package player.limit.test.data

import kotlinx.serialization.Serializable

@Serializable
data class RecommendedVideos(
    val data: Data,
    val debug: Debug
)

@Serializable
data class Data(
    val recommendedMediaFeed: RecommendedMediaFeed
)

@Serializable
data class Debug(
    val trace: String
)

@Serializable
data class RecommendedMediaFeed(
    val elements: Elements
)

@Serializable
data class Elements(
    val dist: Int,
    val edges: List<Edge>,
    val pageInfo: PageInfo
)

@Serializable
data class Edge(
    val node: Node
)

@Serializable
data class PageInfo(
    val endCursor: String,
    val hasNextPage: Boolean
)

@Serializable
data class Node(
    val id: String,
    val media: Media,
    val thumbnail: Thumbnail,
    val title: String,
    val url: String
)

@Serializable
data class Media(
    val packagedMedia: PackagedMedia?,
    val streaming: Streaming
)

@Serializable
data class Thumbnail(
    val dimensions: Dimensions,
    val url: String
)

@Serializable
data class PackagedMedia(
    val muxedMp4s: MuxedMp4s? = null,
)

@Serializable
data class Streaming(
    val dashUrl: String,
    val dimensions: Dimensions,
    val hlsUrl: String,
    val scrubberMediaUrl: String
)

@Serializable
data class MuxedMp4s(
    val recommended: Recommended
)

@Serializable
data class Recommended(
    val duration: Int,
    val url: String
)

@Serializable
data class Dimensions(
    val height: Int,
    val width: Int
)