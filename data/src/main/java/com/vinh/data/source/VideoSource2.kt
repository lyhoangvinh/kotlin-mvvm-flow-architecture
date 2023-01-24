package com.vinh.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vinh.domain.model.entities.avgle.Video
import com.vinh.domain.interactor.GetAllFavoriteVideoIds
import com.vinh.domain.interactor.GetAllVideos

class VideoSource2(
    private val getVideos: GetAllVideos,
    private val getFavoriteVideoIds: GetAllFavoriteVideoIds
) : BasePagingData<Int, Video>(
    source = { page: Int ->
        val favoriteVideos = getFavoriteVideoIds()
        getVideos(page).run {
            val response = body()?.response
            val data = response?.listData()?.onEach {
                it.favorite = favoriteVideos.any { id -> id == it.vid }
            }.orEmpty()
            data
        }
    },
    prevKey = { page ->
        if (page == 0) null else page - 1
    },
    nextKey = { page, dataEmpty ->
        if (dataEmpty) null else page + 1
    },
    refreshKey = { state ->
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    },
    pageSize = 50,
    defaultKey = 0
)
