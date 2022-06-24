package player.limit.test.ui.grid

data class VideoUiModel(
    val id: String,
    val description: String?,
    val dimensions: Dimensions,
    val imageUrl: String,
    val mediaUrl: String,
    val externalUrl: String,
    val displayMode: DisplayMode,
) {
    data class Dimensions(
        val height: Int,
        val width: Int,
    )

    enum class DisplayMode {
        VIDEO,
        STILL,
        ;
    }
}
