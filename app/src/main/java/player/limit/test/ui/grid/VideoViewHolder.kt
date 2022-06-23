package player.limit.test.ui.grid

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import player.limit.test.databinding.GridItemBinding

internal class VideoViewHolder(private val binding: GridItemBinding): ViewHolder(binding.root) {
    fun bind(video: VideoUiModel) {
        binding.textOverlay.text = video.description
        binding.video.useArtwork = true
        binding.video.useController = false
        binding.video.player?.run {
            setMediaItem(MediaItem.fromUri(video.mediaUrl))
            prepare()
            repeatMode = Player.REPEAT_MODE_ALL
            playWhenReady = true
            volume = 0f
        }
    }

    fun unbind() {
        binding.textOverlay.text = null
        binding.video.player?.run {
            stop()
            release()
        }
    }
}