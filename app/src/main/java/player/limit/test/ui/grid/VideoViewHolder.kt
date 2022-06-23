package player.limit.test.ui.grid

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
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

    internal class ErrorListener(private val context: Context): Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            Toast.makeText(context, error.errorCodeName, Toast.LENGTH_SHORT).show()
            super.onPlayerError(error)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onViewHolderClicked: (ViewHolder) -> Unit
        ): VideoViewHolder {
            with (GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
                video.player = ExoPlayer.Builder(video.context).build().apply {
                    addListener(ErrorListener(parent.context))
                }
                return VideoViewHolder(this).apply {
                    itemView.setOnClickListener {
                        onViewHolderClicked.invoke(this)
                    }
                }
            }
        }
    }
}
