package com.vinh.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vinh.data.services.AvgleService
import com.vinh.data.source.VideoSource
import com.vinh.domain.entities.avgle.Video
import com.vinh.domain.interactor.GetAllFavoriteVideoIds
import com.vinh.domain.interactor.GetAllVideos
import com.vinh.domain.repo.VideoRepo
import com.vinh.domain.response.BaseResponseAvgle
import com.vinh.domain.response.VideosResponseAvgle
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class VideoRepoImpl @Inject constructor(private val avgService: AvgleService) : VideoRepo {
   override suspend fun getAll(page: Int): Response<BaseResponseAvgle<VideosResponseAvgle>> = avgService.getAllVideos(page)
   override fun getPaging(videos: GetAllVideos, favoriteVideoIds: GetAllFavoriteVideoIds): Flow<PagingData<Video>> {
      return Pager(
         config = PagingConfig(
            pageSize = 50,
            enablePlaceholders = true,
            prefetchDistance = 50,
            initialLoadSize = 50),
         pagingSourceFactory = { VideoSource(videos, favoriteVideoIds) }
      ).flow
   }
}