package player.limit.test.repo

import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideos(howMany: Int): Flow<List<Video>>
}
