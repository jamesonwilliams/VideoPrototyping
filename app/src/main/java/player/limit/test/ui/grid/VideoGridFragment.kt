package player.limit.test.ui.grid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import player.limit.test.data.ResourceLoader
import player.limit.test.databinding.FragmentVideoGridBinding
import player.limit.test.repo.VideoResourceRepository

class VideoGridFragment : Fragment() {
    private val viewModel: VideoGridViewModel by viewModels {
        VideoGridViewModel.Factory(
            params = VideoGridViewModel.Params(
                repository = VideoResourceRepository(ResourceLoader(resources))
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = GridAdapter { selectedVideoElement ->
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(selectedVideoElement.externalUrl)
            })
        }
        val view = FragmentVideoGridBinding.inflate(layoutInflater, container, false)
        view.grid.layoutManager = StaggeredGridLayoutManager(
            /* spanCount = */ 2,
            /* orientation */ StaggeredGridLayoutManager.VERTICAL
        )
        view.grid.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videos.collect { update ->
                adapter.submitList(update)
            }
        }
        viewModel.fetchVideos()

        return view.root
    }
}
