package player.proto.ui.grid

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class GridAdapter(
    private val onVideoClicked: (VideoUiModel) -> Unit
) : ListAdapter<VideoUiModel, ViewHolder>(VideoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return VideoViewHolder.create(parent) {
            onVideoClicked.invoke(getItem(it.bindingAdapterPosition))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is VideoViewHolder -> holder.bind(video = getItem(position))
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        when (holder) {
            is VideoViewHolder -> holder.unbind()
        }
    }

    object VideoDiffCallback: DiffUtil.ItemCallback<VideoUiModel>() {
        override fun areItemsTheSame(oldItem: VideoUiModel, newItem: VideoUiModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: VideoUiModel, newItem: VideoUiModel) = oldItem == newItem
    }
}
