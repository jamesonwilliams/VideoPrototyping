package player.limit.test.ui.grid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.exoplayer2.ExoPlayer
import player.limit.test.databinding.GridItemBinding

class GridAdapter(private val onVideoClicked: (VideoUiModel) -> Unit) : ListAdapter<VideoUiModel, ViewHolder>(VideoDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.video.player = ExoPlayer.Builder(binding.video.context).build()
        return VideoViewHolder(binding).apply {
            itemView.setOnClickListener {
                onVideoClicked.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = getItem(position)
        when (holder) {
            is VideoViewHolder -> {
                holder.bind(video)
                holder.itemView.tag = video.id
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        when (holder) {
            is VideoViewHolder -> {
                holder.unbind()
                holder.itemView.tag = null
            }
        }
    }

    object VideoDiffCallback: DiffUtil.ItemCallback<VideoUiModel>() {
        override fun areItemsTheSame(oldItem: VideoUiModel, newItem: VideoUiModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: VideoUiModel, newItem: VideoUiModel) = oldItem == newItem
    }
}
