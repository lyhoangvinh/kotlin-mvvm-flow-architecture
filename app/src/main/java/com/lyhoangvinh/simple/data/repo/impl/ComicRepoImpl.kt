package com.lyhoangvinh.simple.data.repo.impl

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.entities.Status
import com.lyhoangvinh.simple.data.repo.ComicRepo
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.data.source.ComicSource
import com.lyhoangvinh.simple.data.source.ComicSource2
import com.lyhoangvinh.simple.utils.Constants
import com.lyhoangvinh.simple.utils.extension.resultLiveData
import com.lyhoangvinh.simple.utils.livedata.RefreshableLiveData
import com.skydoves.sandwich.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.Response
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class ComicRepoImpl @Inject constructor(private val comicVineService: ComicVineService, private val comicSource2: ComicSource2) : ComicRepo {
    override suspend fun getData(): Flow<PagingData<Issues>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { ComicSource(comicVineService) }
        ).flow
    }

    override suspend fun getData2(): Flow<Resource<BaseResponseComic<Issues>>> = resultLiveData{ comicSource2.fetchData() }.asFlow()

    override suspend fun getData3(): LiveData<Resource<BaseResponseComic<Issues>>> = resultLiveData{ comicSource2.fetchData() }

    override suspend fun getDataSandwich(): LiveData<Resource<List<Issues>>>  = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<Resource<List<Issues>>>(Resource.loading())
        comicVineService.getIssues3(20, 1, Constants.KEY, "json", "cover_date: desc").request { response ->
            response.onSuccess {
                liveData.postValue(Resource.success(data?.results.orEmpty()))
            }.onError {
                liveData.postValue(Resource.error(message()))
            }.onException {
                liveData.postValue(Resource.error(message()))
            }
        }
        liveData
    }

    private val comicLiveData = RefreshableLiveData { comicVineService.getIssues4(20, 3, Constants.KEY, "json", "cover_date: desc") }

    override suspend fun getData4(): LiveData<Resource<BaseResponseComic<Issues>>> = comicLiveData

    override fun refresh() = comicLiveData.refresh()

    override suspend fun getData5(): Response<BaseResponseComic<Issues>> = comicVineService.getIssues2(20, 1, Constants.KEY, "json", "cover_date: desc")
}