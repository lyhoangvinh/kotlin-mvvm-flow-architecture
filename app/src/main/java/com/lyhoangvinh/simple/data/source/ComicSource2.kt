package com.lyhoangvinh.simple.data.source

import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ComicSource2 @Inject constructor(private val service: ComicVineService) : BaseDataSource() {

    suspend fun fetchData(): Flow<Resource<BaseResponseComic<Issues>>>  =
        resultFlow { service.getIssues2(20, 3, Constants.KEY, "json", "cover_date: desc") }

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
    })
}