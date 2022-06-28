package player.proto.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import player.proto.data.ResourceLoader
import player.proto.databinding.FragmentVideoPagerBinding
import player.proto.repo.VideoResourceRepository

class VideoPagerFragment : Fragment() {
    private val viewModel: VideoPagerViewModel by viewModels {
        VideoPagerViewModel.Factory(
            params = VideoPagerViewModel.Params(
                repository = VideoResourceRepository(
                    resourceLoader = ResourceLoader(resources = resources)
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = VideoPagerAdapter()
        val binding = FragmentVideoPagerBinding.inflate(inflater, container, false).apply {
            pager.adapter = adapter
            pager.orientation = ViewPager2.ORIENTATION_VERTICAL
        }
        lifecycleScope.launch {
            viewModel.videos.collect { uiModels ->
                adapter.submitList(uiModels)
            }
        }
        viewModel.fetchVideos()

        return binding.root
    }
}
