package com.vinh.data.repo

import androidx.lifecycle.LiveData
import com.vinh.data.source.AvgleSource
import com.vinh.domain.itemviewmodel.ItemData
import com.vinh.domain.model.ItemViewModel
import com.vinh.domain.model.Resource
import com.vinh.domain.repo.AvgRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AvgRepoImpl @Inject constructor(private val source: AvgleSource): AvgRepo {
    override suspend fun getHomeData(): Flow<Resource<List<ItemViewModel>>> =
        source.fetchDataHome7()

    override suspend fun getHomeData2(): Flow<Resource<List<ItemData>>> =
        source.fetchDataHome2()

    override suspend fun homeLiveData(): LiveData<Resource<List<ItemViewModel>>>
          = source.homeLiveData()

}