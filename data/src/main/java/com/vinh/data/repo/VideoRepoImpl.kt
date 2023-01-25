package com.vinh.data.repo

import androidx.paging.*
import com.vinh.data.manager.DatabaseManager
import com.vinh.data.dao.VideosDao
import com.vinh.data.services.AvgleService
import com.vinh.data.source.VideoPageKeyedRemoteMediator
import com.vinh.data.source.VideoSource
import com.vinh.domain.interactor.*
import com.vinh.domain.model.entities.avgle.Video
import com.vinh.domain.repo.VideoRepo
import com.vinh.domain.response.BaseResponseAvgle
import com.vinh.domain.response.VideosResponseAvgle
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class VideoRepoImpl @Inject constructor(
    private val databaseManager: DatabaseManager,
    private val avgService: AvgleService,
    private val videosDao: VideosDao
) : VideoRepo {
    override suspend fun getAll(page: Int): Response<BaseResponseAvgle<VideosResponseAvgle>> =
        avgService.getAllVideos(page)

    override fun getPaging(
        videos: GetAllVideos,
        favoriteVideoIds: GetAllFavoriteVideoIds
    ): Flow<PagingData<Video>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = true,
                prefetchDistance = 50,
                initialLoadSize = 50
            ),
            pagingSourceFactory = { VideoSource(videos, favoriteVideoIds) }
        ).flow
    }

    override suspend fun deleteByExecutor(executor: String) = videosDao.deleteByExecutor(executor)

    override suspend fun insertList(videos: List<Video>) = videosDao.insertList(videos)

    override fun update(video: Video) = videosDao.update(video)

    @OptIn(ExperimentalPagingApi::class)
    override fun videoOfExecutor(executor: String,
                                 getAllVideos: GetAllVideos,
                                 getVideoRemoteKeyByExecutor: GetVideoRemoteKeyByExecutor,
                                 deleteVideoRemoteByExecutor: DeleteVideoRemoteByExecutor,
                                 deleteVideoByExecutor: DeleteVideoByExecutor,
                                 addVideoRemoteKey: AddVideoRemoteKey,
                                 addVideoList: AddVideoList): Flow<PagingData<Video>> =
        Pager(
            config = PagingConfig(10),
            remoteMediator = VideoPageKeyedRemoteMediator(
                executor = executor,
                databaseManager = databaseManager,
                getAllVideos = getAllVideos,
                getVideoRemoteKeyByExecutor = getVideoRemoteKeyByExecutor,
                deleteVideoRemoteByExecutor = deleteVideoRemoteByExecutor,
                deleteVideoByExecutor = deleteVideoByExecutor,
                addVideoRemoteKey = addVideoRemoteKey,
                addVideoList = addVideoList
            )
        ) {
            videosDao.pagingSourceByExecutor(executor)
        }.flow
}