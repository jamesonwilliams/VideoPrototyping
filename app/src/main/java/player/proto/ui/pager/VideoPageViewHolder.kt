package player.proto.ui.pager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import player.proto.databinding.FragmentSingleVideoPageBinding

class VideoPageViewHolder(
    private val binding: FragmentSingleVideoPageBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(model: VideoUiModel) {
        binding.textOverlay.text = model.title
        with (binding.video) {
            player = ExoPlayer.Builder(context).build()
            useArtwork = true
            useController = false
            setShutterBackgroundColor(Color.TRANSPARENT)
            player?.run {
                setMediaItem(MediaItem.fromUri(model.url))
                prepare()
                repeatMode = REPEAT_MODE_ALL
                playWhenReady = false
                volume = 0f
            }
        }
    }

    fun play() {
        binding.video.player?.playWhenReady = true
    }

    fun pause() {
        binding.video.player?.playWhenReady = false
    }

    fun unbind() {
        with(binding) {
            textOverlay.text = null
            with (video) {
                player?.stop()
                player?.release()
                player = null
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): VideoPageViewHolder {
            with (FragmentSingleVideoPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
                return VideoPageViewHolder(this)
            }
        }
    }
}
