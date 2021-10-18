package com.vinh.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vinh.domain.entities.avgle.Video
import com.vinh.domain.interactor.GetAllFavoriteVideoIds
import com.vinh.domain.interactor.GetAllVideos

class VideoSource(private val getVideos: GetAllVideos, private val getFavoriteVideoIds: GetAllFavoriteVideoIds) : PagingSource<Int, Video>() {

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        val page = params.key ?: 0
        return try {
            val favoriteVideos = getFavoriteVideoIds()
            getVideos(page).run {
                val response = body()?.response
                val data = response?.listData()?.onEach {
                    it.favorite = favoriteVideos.any { id -> id == it.vid }
                }.orEmpty()
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response?.hasMore == true) page + 1 else null
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}