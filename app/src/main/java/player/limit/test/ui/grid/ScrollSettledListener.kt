package player.limit.test.ui.grid

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollSettledListener(
    private val layoutManager: LinearLayoutManager,
    private val onViewFocused: (RecyclerView.ViewHolder?) -> Unit,
): RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        var mostVisible: RecyclerView.ViewHolder? = null
        var highestVisibility = 0f
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            for (pos in layoutManager.findFirstVisibleItemPosition()..layoutManager.findLastVisibleItemPosition()) {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(pos)
                val visibility = ViewVisibility.getVisiblePercentage(viewHolder?.itemView)
                if (visibility > highestVisibility) {
                    mostVisible = viewHolder
                    highestVisibility = visibility
                }
            }
            onViewFocused.invoke(mostVisible)
        }
    }
}
