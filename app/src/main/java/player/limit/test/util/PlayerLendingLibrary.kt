package player.limit.test.util

import android.content.Context
import android.util.Log
import com.google.android.exoplayer2.ExoPlayer

object PlayerLendingLibrary {
    private var player: ExoPlayer? = null
    private var owner: String? = null
    private var cleanup: (() -> Unit)? = null

    @Synchronized
    fun requestPlayer(context: Context, requester: Any, onCleanup: () -> Unit): ExoPlayer {
        if (player == null) {
            player = ExoPlayer.Builder(context).build()
        }
        val requesterOwner = computeOwner(requester)
        if (owner != null && owner != requesterOwner) {
            cleanup?.invoke()
        }
        cleanup = onCleanup
        owner = requesterOwner
        return player!!.also {
            Log.i("XXXX", "Gave player to $requesterOwner")
        }
    }

    private fun computeOwner(any: Any): String {
        return "${any::class.simpleName}:${any.hashCode()}"
    }
}
