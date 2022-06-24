package player.limit.test.ui.grid

import android.graphics.Rect
import android.os.PowerManager
import android.view.View

object ViewVisibility {
    fun getVisiblePercentage(view: View?): Float {
        if (view == null) {
            return 0f
        }

        val viewOffScreen = view.visibility != View.VISIBLE || view.parent == null
        val windowNotFocused = !view.hasWindowFocus()
        val isScreenOff = view
            .context
            ?.getSystemService(PowerManager::class.java)
            ?.let { !it.isInteractive }
            ?: true

        // don't report visibility if the user can't see the view
        if (viewOffScreen || windowNotFocused || isScreenOff) {
            return 0f
        }

        // or if there isn't a global visible rect
        val clipRect = Rect()
        if (!view.getGlobalVisibleRect(clipRect)) {
            return 0f
        }

        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val top = location[1]
        val bottom = top + view.height

        // If clipped rect is inside the total view rect
        // It means the view is bigger than possible visible area for this view
        if (clipRect.top > top && clipRect.bottom < bottom) {
            return 1f
        }

        val visibleArea = clipRect.height() * clipRect.width()
        val totalArea = view.height * view.width

        var percentage = visibleArea.toFloat() / totalArea.toFloat()
        if (percentage > 1.0) {
            percentage = 1f
        }
        return percentage
    }
}