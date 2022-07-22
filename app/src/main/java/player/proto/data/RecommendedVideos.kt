package player.proto.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedVideos(
    val `data`: Data,
    val debug: Debug
) {
    @Serializable
    data class Data(
        val recommendedVideoFeed: RecommendedVideoFeed
    ) {
        @Serializable
        data class RecommendedVideoFeed(
            val posts: Posts,
        ) {
            @Serializable
            data class Posts(
                val edges: List<Edge>
            ) {
                @Serializable
                data class Edge(
                    val node: Node
                ) {
                    @Serializable
                    data class Node(
                        val id: String,
                        val media: Media,
                        val permalink: String,
                        val title: String,
                        val url: String
                    ) {
                        @Serializable
                        data class Media(
                            val packagedMedia: PackagedMedia,
                            val still: Still,
                            val streaming: Streaming
                        ) {
                            @Serializable
                            data class PackagedMedia(
                                val muxedMp4s: MuxedMp4s?,
                            ) {
                                @Serializable
                                data class MuxedMp4s(
                                    val recommended: Recommended
                                ) {
                                    @Serializable
                                    data class Recommended(
                                        val duration: Int,
                                        val url: String
                                    )
                                }
                            }

                            @Serializable
                            data class Still(
                                val content: Content
                            ) {
                                @Serializable
                                data class Content(
                                    val dimensions: Dimensions,
                                    val url: String
                                ) {
                                    @Serializable
                                    data class Dimensions(
                                        val height: Int,
                                        val width: Int
                                    )
                                }
                            }

                            @Serializable
                            data class Streaming(
                                val dashUrl: String,
                                val dimensions: Dimensions
                            ) {
                                @Serializable
                                data class Dimensions(
                                    val height: Int,
                                    val width: Int
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Serializable
    data class Debug(
        val trace: String
    )
}