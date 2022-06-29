package player.proto.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.flowOf
import player.proto.ui.landing.LandingFragmentDirections.Companion.actionLandingFragmentToFeedFragment
import player.proto.ui.landing.LandingFragmentDirections.Companion.actionLandingFragmentToVideoGridFragment
import player.proto.ui.landing.LandingFragmentDirections.Companion.actionLandingFragmentToVideoPagerFragment

class LandingViewModel : ViewModel() {
    val entries = flowOf(
        listOf(
            ListItemModel(
                id = 1,
                message = "Video Grid",
                navigationTarget = actionLandingFragmentToVideoGridFragment(),
            ),
            ListItemModel(
                id = 2,
                message = "Video Pager",
                navigationTarget = actionLandingFragmentToVideoPagerFragment(),
            ),
            ListItemModel(
                id = 3,
                message = "Video Feed",
                navigationTarget = actionLandingFragmentToFeedFragment(),
            )
        )
    )

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor().newInstance()
        }
    }
}