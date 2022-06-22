package player.limit.test.ui.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import player.limit.test.databinding.GridItemBinding

class GridAdapter : ListAdapter<VideoUiModel, ViewHolder>(VideoDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.video.player = ExoPlayer.Builder(binding.video.context).build()
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is VideoViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        when (holder) {
            is VideoViewHolder -> {
               holder.unbind()
            }
        }
    }

    internal class VideoViewHolder(private val binding: GridItemBinding): ViewHolder(binding.root) {
        fun bind(video: VideoUiModel) {
            binding.textOverlay.text = video.description
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

    object VideoDiffCallback: DiffUtil.ItemCallback<VideoUiModel>() {
        override fun areItemsTheSame(oldItem: VideoUiModel, newItem: VideoUiModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: VideoUiModel, newItem: VideoUiModel) = oldItem == newItem
    }
}
