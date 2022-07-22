package player.proto.data

import android.content.res.Resources
import androidx.annotation.RawRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import player.proto.R

class ResourceLoader(private val resources: Resources) {
    suspend fun readString(@RawRes rawResourceId: Int): String {
        return withContext(Dispatchers.IO) {
            resources.openRawResource(R.raw.recommended)
                .bufferedReader()
                .use { it.readText() }
        }
    }
}
