package player.proto.ui.landing

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class LandingAdapter: ListAdapter<ListItemModel, ListItemViewHolder>(ListItemDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListItemViewHolder.create(parent)
    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) = holder.bind(getItem(position))
    override fun onViewRecycled(holder: ListItemViewHolder) = holder.unbind()

    object ListItemDiffUtil: DiffUtil.ItemCallback<ListItemModel>() {
        override fun areItemsTheSame(old: ListItemModel, new: ListItemModel) = old.id == new.id
        override fun areContentsTheSame(old: ListItemModel, new: ListItemModel): Boolean = old == new
    }
}
