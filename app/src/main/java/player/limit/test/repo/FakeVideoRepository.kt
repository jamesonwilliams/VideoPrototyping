package player.limit.test.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class FakeVideoRepository: VideoRepository {
    override suspend fun getVideos(): Flow<List<Video>> {
        return withContext(Dispatchers.IO) {
             flowOf(
                listOf(
                    Video(
                        id = 1,
                        mediaUrl = "https://packaged-media.redd.it/odvvyp5ndv691/muxed-medium.mp4?m=DASHPlaylist.mpd&v=1&e=1655873855&s=fb01e26933df51cb662f5fee76431d84dfbc295c",
                        description = "MP4 video",
                    ),
                    Video(
                        id = 2,
                        mediaUrl = "https://v.redd.it/yq48uwra2z691/DASHPlaylist.mpd?f=sd&v=1&a=1658462255%2CMGQ4NTRlYTdjNmU1YmU2MzgzMTEzYjBmNGM4NWIwMGIwN2QyMjdiOWRhNGQxNjFhMzAyMDI0ZjE0MWU3NDE0ZQ%3D%3D",
                        description = "DASH video",
                    ),
                    Video(
                        id = 3,
                        mediaUrl = "https://v.redd.it/bahcyzryfxk71/HLSPlaylist.m3u8?f=sd&v=1&a=1658461846%2CYjVlMTcxMjkzZTY0YTk1ZjU4NjI4OTQwOGZjYTZkMTU4NDliY2Q4YTZlYTc3MTAxYWEyODIzODA2MzA2NGEzZA%3D%3D",
                        description = "HLS video",
                    ),
                )
            )
        }
    }
}
