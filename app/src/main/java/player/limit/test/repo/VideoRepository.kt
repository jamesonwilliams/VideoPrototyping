package player.limit.test.repo

import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideos(): Flow<List<Video>>
}
