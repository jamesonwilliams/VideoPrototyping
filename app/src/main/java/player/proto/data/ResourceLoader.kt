package player.proto.data

import android.content.res.Resources
import androidx.annotation.RawRes
import kotlinx.coroutines.Dispatchers
import player.proto.R

class ResourceLoader(private val resources: Resources) {
    fun readString(@RawRes rawResourceId: Int): String {
        return with (Dispatchers.IO) {
            resources.openRawResource(R.raw.recommended)
                .bufferedReader()
                .use { it.readText() }
        }
    }
}
