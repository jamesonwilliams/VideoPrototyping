package player.proto.ui.feed

data class FeedItemUiModel(
    val id: String,
    val description: String?,
    val mediaUrl: String,
    val thumbnailUrl: String,
    val dimensions: Dimensions,
    val externalUrl: String,
) {
    data class Dimensions(
        val height: Int,
        val width: Int,
    )
}
