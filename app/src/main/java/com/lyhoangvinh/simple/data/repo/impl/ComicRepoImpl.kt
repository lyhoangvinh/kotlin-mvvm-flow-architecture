package com.lyhoangvinh.simple.data.repo.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.network.Resource
import com.lyhoangvinh.simple.data.repo.ComicRepo
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.ComicSource
import com.lyhoangvinh.simple.data.source.ComicSource2
import com.lyhoangvinh.simple.utils.Constants
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicRepoImpl @Inject constructor(private val comicVineService: ComicVineService, private val comicSource2: ComicSource2) : ComicRepo {
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

    override suspend fun getData2(): Resource<BaseResponseComic<Issues>> = comicSource2.fetchData()

    override suspend fun getDataSanwit(): LiveData<Resource<List<Issues>>>  = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<Resource<List<Issues>>>(Resource.loading())
        delay(100L)
        comicVineService.getIssues3(20, 1, Constants.KEY, "json", "cover_date: desc").request { response ->
            response.onSuccess {
                liveData.postValue(Resource.success(data?.results.orEmpty()))
            }.onError {
                error(message())
            }.onException {
                error(message())
            }
        }
        liveData
    }
}