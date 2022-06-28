package player.proto.ui.landing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import player.proto.databinding.ListItemBinding

class ListItemViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ListItemModel) {
        with (binding) {
            itemName.text = model.message
            with (root) {
                setOnClickListener {
                    findNavController().navigate(model.navigationTarget)
                }
            }
        }
    }

    fun unbind() {
        with (binding) {
            itemName.text = null
            root.setOnClickListener {}
        }
    }

    companion object {
        fun create(parent: ViewGroup): ListItemViewHolder {
            with (ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
                return ListItemViewHolder(this)
            }
        }
    }
}
