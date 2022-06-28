package player.proto.ui.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.flowOf

class LandingViewModel : ViewModel() {
    val entries = flowOf(
        listOf(
            ListItemModel(
                id = 1,
                message = "Video Grid",
                navigationTarget = LandingFragmentDirections.actionLandingFragmentToVideoGridFragment(),
            ),
        )
    )

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor().newInstance()
        }
    }
}