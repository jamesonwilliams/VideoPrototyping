package player.proto.ui.pager

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class VideoPagerAdapter :  ListAdapter<VideoUiModel, VideoPageViewHolder>(VideoPageDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VideoPageViewHolder.create(parent)
    override fun onBindViewHolder(holder: VideoPageViewHolder, position: Int) = holder.bind(getItem(position))
    override fun onViewRecycled(holder: VideoPageViewHolder) = holder.unbind()
    override fun onViewAttachedToWindow(holder: VideoPageViewHolder) = holder.play()
    override fun onViewDetachedFromWindow(holder: VideoPageViewHolder) = holder.pause()

    object VideoPageDiffUtil : DiffUtil.ItemCallback<VideoUiModel>() {
        override fun areItemsTheSame(old: VideoUiModel, new: VideoUiModel) = old.id == new.id
        override fun areContentsTheSame(old: VideoUiModel, new: VideoUiModel) = old == new
    }
}
