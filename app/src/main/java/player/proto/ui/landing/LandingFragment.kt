package player.proto.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import player.proto.databinding.FragmentLandingBinding

class LandingFragment : Fragment() {
    private val viewModel: LandingViewModel by viewModels { LandingViewModel.Factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = LandingAdapter()
        val binding = FragmentLandingBinding.inflate(inflater, container, false)
            .apply {
                list.adapter = adapter
                list.layoutManager = LinearLayoutManager(context)
            }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.entries.collect { listItems: List<ListItemModel> ->
                adapter.submitList(listItems)
            }
        }

        return binding.root
    }
}
