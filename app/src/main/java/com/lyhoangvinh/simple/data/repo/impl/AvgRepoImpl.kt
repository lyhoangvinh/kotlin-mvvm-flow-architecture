package com.lyhoangvinh.simple.data.repo.impl

import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.entities.avgle.MergedData
import com.lyhoangvinh.simple.data.repo.AvgRepo
import com.lyhoangvinh.simple.data.source.AvgleSource
import com.lyhoangvinh.simple.ui.base.adapter.ItemData
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AvgRepoImpl @Inject constructor(private val source: AvgleSource): AvgRepo {
    override suspend fun getHomeData(): Flow<Resource<List<ItemViewModel>>> =
        source.fetchDataHome()

    override suspend fun getHomeData2(): Flow<Resource<List<ItemData>>> =
        source.fetchDataHome2()
}