package player.limit.test.ui.grid

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.squareup.picasso.Picasso
import player.limit.test.databinding.GridItemBinding
import player.limit.test.util.PlayerLendingLibrary
import player.limit.test.util.invisible
import player.limit.test.util.visible

internal class VideoViewHolder private constructor(
    private val binding: GridItemBinding
): ViewHolder(binding.root) {
    fun bind(model: VideoUiModel) {
        Log.i("XXXX", "Binding ${hashCode()}} ${model.id}")
        itemView.tag = model.id
        with(binding) {
            playerView.player = null
            playerView.invisible()
            stillImage.invisible()
            with (model.dimensions) {
                root.setAspectRatio(width.toFloat() / height.toFloat())
            }
            textOverlay.text = model.description
            when (model.displayMode) {
                VideoUiModel.DisplayMode.STILL -> binding.showImage(model.dimensions, model.imageUrl)
                VideoUiModel.DisplayMode.VIDEO -> binding.playVideo(model.mediaUrl)
            }
        }
    }

    private fun GridItemBinding.showImage(dimensions: VideoUiModel.Dimensions, imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .resize(dimensions.width, dimensions.height)
            .into(stillImage)
        stillImage.visible()
    }

    private fun GridItemBinding.playVideo(mediaUrl: String) {
        PlayerLendingLibrary.requestPlayer(playerView.context, this@VideoViewHolder) {
            playerView.player?.stop()
            playerView.player = null
        }.let { player ->
            with (player) {
                setMediaItem(MediaItem.fromUri(mediaUrl))
                prepare()
                repeatMode = Player.REPEAT_MODE_ALL
                playWhenReady = true
                volume = 0f
            }
            with (playerView) {
                useArtwork = true
                useController = false
                setKeepContentOnPlayerReset(true)
                playerView.player = player
                visible()
            }
        }
    }

    fun unbind() {
        Log.i("XXXX", "Unbinding ${hashCode()} ${itemView.tag}")
        itemView.tag = null
        with (binding) {
            textOverlay.text = null
            stillImage.invisible()
            playerView.invisible()
            playerView.player?.stop()
            playerView.player = null
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onViewHolderClicked: (ViewHolder) -> Unit
        ): VideoViewHolder {
            with (GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
                return VideoViewHolder(this).apply {
                    itemView.setOnClickListener {
                        onViewHolderClicked.invoke(this)
                    }
                }
            }
        }
    }
}
