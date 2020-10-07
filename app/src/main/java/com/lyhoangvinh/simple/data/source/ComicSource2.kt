package com.lyhoangvinh.simple.data.source

import com.lyhoangvinh.simple.data.services.ComicVineService
import com.lyhoangvinh.simple.utils.Constants
import javax.inject.Inject

class ComicSource2 @Inject constructor(private val service: ComicVineService) : BaseDataSource() {

    suspend fun fetchData() = getResource { service.getIssues2(20, 3, Constants.KEY, "json", "cover_date: desc") }
    
}