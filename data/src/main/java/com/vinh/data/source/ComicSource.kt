package com.vinh.data.source

import androidx.paging.PagingSource
import com.vinh.data.Constants
import com.vinh.data.entities.comic.Issues
import com.vinh.data.services.ComicVineService

class ComicSource(private val comicVineService: ComicVineService) : PagingSource<Int, Issues>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Issues> {
        val page = params.key ?: 0
        return try {
            comicVineService.getIssues(20, page, Constants.KEY, "json", "cover_date: desc").run {
                val data = results
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == number_of_total_results) null else page + 1
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}