package com.vinh.domain.repo

import androidx.paging.PagingData
import com.vinh.domain.interactor.*
import com.vinh.domain.model.entities.avgle.Video
import com.vinh.domain.response.BaseResponseAvgle
import com.vinh.domain.response.VideosResponseAvgle
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface VideoRepo {
    fun update(video: Video)
    suspend fun getAll(page: Int): Response<BaseResponseAvgle<VideosResponseAvgle>>
    fun getPaging(videos: GetAllVideos, favoriteVideoIds: GetAllFavoriteVideoIds): Flow<PagingData<Video>>
    suspend fun deleteByExecutor(executor: String)
    suspend fun insertList(videos: List<Video>)
    fun videoOfExecutor(executor: String,
                        getAllVideos: GetAllVideos,
                        getVideoRemoteKeyByExecutor: GetVideoRemoteKeyByExecutor,
                        deleteVideoRemoteByExecutor: DeleteVideoRemoteByExecutor,
                        deleteVideoByExecutor: DeleteVideoByExecutor,
                        addVideoRemoteKey: AddVideoRemoteKey,
                        addVideoList: AddVideoList
    ): Flow<PagingData<Video>>
}