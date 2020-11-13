package com.vinh.domain.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vinh.data.entities.comic.Issues
import com.vinh.domain.model.Resource
import com.vinh.domain.response.BaseResponseComic
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ComicRepo {
    suspend fun getData() : Flow<PagingData<Issues>>
    suspend fun getData2(): Flow<Resource<BaseResponseComic<Issues>>>
    suspend fun getData3(): LiveData<Resource<BaseResponseComic<Issues>>>
    suspend fun getDataSandwich(): LiveData<Resource<List<Issues>>>
    suspend fun getData4(): LiveData<Resource<BaseResponseComic<Issues>>>
    fun refresh()

    suspend fun getData5(): Response<BaseResponseComic<Issues>>

    suspend fun getData6(): Flow<Resource<BaseResponseComic<Issues>>>
}