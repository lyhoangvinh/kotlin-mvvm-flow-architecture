package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import kotlinx.coroutines.flow.Flow

interface ComicRepo {
    fun getData() : Flow<PagingData<Issues>>
    suspend fun getData2(): Resource<BaseResponseComic<Issues>>
    suspend fun getDataSandwich(): LiveData<Resource<List<Issues>>>
}