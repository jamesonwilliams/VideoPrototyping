package player.proto.ui.feed

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import player.proto.data.ResourceLoader
import player.proto.databinding.FragmentFeedBinding
import player.proto.repo.VideoResourceRepository

class FeedFragment : Fragment() {
    private val viewModel: FeedViewModel by viewModels {
        FeedViewModel.Factory(
            params = FeedViewModel.Params(
                repository = VideoResourceRepository(ResourceLoader(resources))
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = FeedAdapter { selectedFeedItem ->
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(selectedFeedItem.externalUrl)
            })
        }
        val view = FragmentFeedBinding.inflate(layoutInflater, container, false)
        view.feed.layoutManager = LinearLayoutManager(view.root.context)
        view.feed.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.feedItems.collect { newItems ->
                adapter.submitList(newItems)
            }
        }
        viewModel.fetchFeedItems()

        return view.root
    }
}
