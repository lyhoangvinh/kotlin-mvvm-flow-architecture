package com.vinh.domain.repo

import androidx.paging.PagingData
import com.vinh.domain.entities.avgle.Video
import com.vinh.domain.interactor.GetAllFavoriteVideos
import com.vinh.domain.interactor.GetAllVideos
import com.vinh.domain.response.BaseResponseAvgle
import com.vinh.domain.response.VideosResponseAvgle
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface VideoRepo {
    suspend fun getAll(page: Int): Response<BaseResponseAvgle<VideosResponseAvgle>>
    fun getPaging(videos: GetAllVideos, favoriteVideos: GetAllFavoriteVideos): Flow<PagingData<Video>>
}