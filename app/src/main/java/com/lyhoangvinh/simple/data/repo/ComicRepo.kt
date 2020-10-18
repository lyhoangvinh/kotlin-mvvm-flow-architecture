package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.utils.livedata.RefreshableLiveData
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

}