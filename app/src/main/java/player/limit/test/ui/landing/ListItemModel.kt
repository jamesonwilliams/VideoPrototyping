package player.limit.test.ui.landing

import androidx.navigation.NavDirections

data class ListItemModel(
    val id: Int,
    val message: String,
    val navigationTarget: NavDirections,
)