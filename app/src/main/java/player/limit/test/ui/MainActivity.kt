package player.limit.test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import player.limit.test.R
import player.limit.test.databinding.ActivityMainBinding
import player.limit.test.ui.grid.VideoGridFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, VideoGridFragment.newInstance())
                .commitNow()
        }
    }
}