package player.limit.test.repo

data class Video(
    val id: String,
    val formats: Formats,
    val thumbnail: Thumbnail,
    val description: String,
    val detailLink: String,
    val duration: Int?,
) {
    data class Formats(
        val urls: Map<Type, String>,
        val dimensions: Dimensions,
    )

    data class Thumbnail(
        val url: String,
        val dimensions: Dimensions,
    )

    enum class Type {
        HLS,
        DASH,
        MP4,
        ;
    }

    data class Dimensions(
        val height: Int,
        val width: Int,
    )
}
