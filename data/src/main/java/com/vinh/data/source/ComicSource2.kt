package com.vinh.data.source

import android.util.Log
import com.vinh.data.entities.comic.Issues
import com.vinh.domain.response.BaseResponseComic
import com.vinh.data.services.ComicVineService
import com.vinh.data.Constants
import com.vinh.domain.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ComicSource2 @Inject constructor(private val service: ComicVineService) : BaseDataSource() {

    suspend fun fetchData(): Flow<Resource<BaseResponseComic<Issues>>>  =
        resultFlow {
            val data = Thread.currentThread().name
            Log.d("LOGCXCC", data)
            service.getIssues2(20, 3, Constants.KEY, "json", "cover_date: desc")
        }.flowOn(Dispatchers.IO)

    suspend fun fetchData2() = getResource { service.getIssues2(20, 3, Constants.KEY, "json", "cover_date: desc") }

    suspend fun fetchData4() = resultLiveData { service.getIssues2(20, 3, Constants.KEY, "json", "cover_date: desc") }

    suspend fun fetchData5() :
        Flow<Resource<BaseResponseComic<Issues>>>  =
            resultFlow2 {
                service.getIssues5(20, 3, Constants.KEY, "json", "cover_date: desc")
            }.flowOn(Dispatchers.IO)
}