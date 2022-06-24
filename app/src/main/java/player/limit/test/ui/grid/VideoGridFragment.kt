package player.limit.test.ui.grid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
            openUrl(selectedVideoElement.externalUrl)
        }
        val view = FragmentVideoGridBinding.inflate(layoutInflater, container, false)
        val layoutManager = LinearLayoutManager(context)
        view.grid.layoutManager = layoutManager
        view.grid.adapter = adapter
        view.grid.addOnScrollListener(ScrollSettledListener(layoutManager) { focusedViewHolder ->
            focusedViewHolder?.itemView?.tag?.toString()?.let { id ->
                Log.i("XXXX", "selecting $id...")
                viewModel.playVideo(id)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.videos.collect { update ->
                Log.i("XXXX", "Update!")
                adapter.submitList(update)
            }
        }
        viewModel.fetchVideos()

        return view.root
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }
}
