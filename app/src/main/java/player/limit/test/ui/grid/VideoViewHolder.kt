package player.limit.test.ui.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import player.limit.test.databinding.GridItemBinding

internal class VideoViewHolder private constructor(
    private val binding: GridItemBinding
): ViewHolder(binding.root) {
    fun bind(video: VideoUiModel) {
        binding.textOverlay.text = video.description
        with (binding.video) {
            useArtwork = true
            useController = false
            player?.run {
                setMediaItem(MediaItem.fromUri(video.mediaUrl))
                prepare()
                repeatMode = Player.REPEAT_MODE_ALL
                playWhenReady = true
                volume = 0f
            }
        }
    }

    fun unbind() {
        with (binding) {
            textOverlay.text = null
            video.player?.stop()
            video.player?.release()
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onViewHolderClicked: (ViewHolder) -> Unit
        ): VideoViewHolder {
            with (GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
                video.player = ExoPlayer.Builder(video.context).build()
                return VideoViewHolder(this).apply {
                    itemView.setOnClickListener {
                        onViewHolderClicked.invoke(this)
                    }
                }
            }
        }
    }
}
