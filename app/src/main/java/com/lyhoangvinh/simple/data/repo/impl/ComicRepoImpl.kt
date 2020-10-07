package com.lyhoangvinh.simple.data.repo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.repo.ComicRepo
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.ComicSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ComicRepoImpl @Inject constructor(private val comicVineService: ComicVineService) : ComicRepo {
    override fun getData(): Flow<PagingData<Issues>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { ComicSource(comicVineService) }
        ).flow
    }
}