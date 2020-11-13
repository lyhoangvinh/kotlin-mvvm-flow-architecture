package com.vinh.domain.repo

import androidx.lifecycle.LiveData
import com.vinh.domain.itemviewmodel.ItemData
import com.vinh.domain.model.ItemViewModel
import com.vinh.domain.model.Resource

import kotlinx.coroutines.flow.Flow


interface AvgRepo {
    suspend fun getHomeData(): Flow<Resource<List<ItemViewModel>>>
    suspend fun getHomeData2(): Flow<Resource<List<ItemData>>>
    suspend fun homeLiveData(): LiveData<Resource<List<ItemViewModel>>>
}