package player.proto.ui.grid

data class VideoUiModel(
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
