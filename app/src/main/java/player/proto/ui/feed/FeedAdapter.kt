package player.proto.ui.feed

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class FeedAdapter(
    private val onFeedItemClicked: (FeedItemUiModel) -> Unit
) : ListAdapter<FeedItemUiModel, FeedItemViewHolder>(ModelDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemViewHolder {
        return FeedItemViewHolder.create(parent) {
            onFeedItemClicked.invoke(getItem(it.bindingAdapterPosition))
        }
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        holder.bind(feedItem = getItem(position))
    }

    override fun onViewRecycled(holder: FeedItemViewHolder) {
        holder.unbind()
    }

    object ModelDiffCallback: DiffUtil.ItemCallback<FeedItemUiModel>() {
        override fun areItemsTheSame(old: FeedItemUiModel, new: FeedItemUiModel) = old.id == new.id
        override fun areContentsTheSame(old: FeedItemUiModel, new: FeedItemUiModel) = old == new
    }
}
