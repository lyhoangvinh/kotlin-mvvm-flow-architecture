package com.lyhoangvinh.simple.data.source

import android.util.Log
import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import com.lyhoangvinh.simple.utils.Constants
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

    suspend fun fetchData3() = resultFlowMapper(call = {
        service.getIssues2(20, 3, Constants.KEY, "json", "cover_date: desc")
    }, mapCallResult = {
        if (it == null) {
            emptyList()
        } else {
            val item = mutableListOf<ItemViewModel>()
            //Todo: map item list
            item
        }
    }).flowOn(Dispatchers.IO)

    suspend fun fetchData4() = resultLiveData { service.getIssues2(20, 3, Constants.KEY, "json", "cover_date: desc") }

    suspend fun fetchData5() :
        Flow<Resource<BaseResponseComic<Issues>>>  =
            resultFlow2 {
                service.getIssues5(20, 3, Constants.KEY, "json", "cover_date: desc")
            }.flowOn(Dispatchers.IO)
}