package player.limit.test.ui.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import player.limit.test.databinding.FragmentVideoGridBinding
import player.limit.test.repo.FakeVideoRepository

class VideoGridFragment : Fragment() {
    private val viewModel: VideoGridViewModel by viewModels {
        VideoGridViewModel.Factory(
            params = VideoGridViewModel.Params(
                repository = FakeVideoRepository()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = GridAdapter()
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
