package player.limit.test.ui.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import player.limit.test.R
import player.limit.test.databinding.FragmentVideoGridBinding

class VideoGridFragment : Fragment() {

    companion object {
        fun newInstance() = VideoGridFragment()
    }

    private lateinit var viewModel: VideoGridViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentVideoGridBinding.inflate(layoutInflater, container, false)
        view.message.text = getString(R.string.message_created)
        return view.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VideoGridViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
