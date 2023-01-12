package com.vinh.data.repo

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vinh.data.services.ComicVineService
import com.vinh.data.source.ComicSource
import com.vinh.data.source.ComicSource2
import com.skydoves.sandwich.*
import com.vinh.data.Constants
import com.vinh.domain.model.entities.comic.Issues
import com.vinh.domain.model.Resource
import com.vinh.domain.response.BaseResponseComic
import com.vinh.domain.repo.ComicRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class ComicRepoImpl @Inject constructor(private val comicVineService: ComicVineService, private val comicSource2: ComicSource2) :
    ComicRepo {
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

    override suspend fun getData2(): Flow<Resource<BaseResponseComic<Issues>>> = comicSource2.fetchData()

    override suspend fun getData3(): LiveData<Resource<BaseResponseComic<Issues>>> = comicSource2.fetchData4()

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

    private val comicLiveData = comicVineService.getIssues4(20, 3, Constants.KEY, "json", "cover_date: desc")

    override suspend fun getData4(): LiveData<Resource<BaseResponseComic<Issues>>> = comicLiveData

    override fun refresh() = comicLiveData.refresh()

    override suspend fun getData5(): Response<BaseResponseComic<Issues>> = comicVineService.getIssues2(20, 1, Constants.KEY, "json", "cover_date: desc")

    override suspend fun getData6(): Flow<Resource<BaseResponseComic<Issues>>> =
        comicSource2.fetchData5()
}