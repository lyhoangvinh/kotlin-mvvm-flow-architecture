package com.lyhoangvinh.simple.data.repo

import androidx.lifecycle.LiveData
import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.entities.avgle.MergedData
import com.lyhoangvinh.simple.ui.base.adapter.ItemData
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import kotlinx.coroutines.flow.Flow

interface AvgRepo {
    suspend fun getHomeData(): Flow<Resource<List<ItemViewModel>>>
    suspend fun getHomeData2(): Flow<Resource<List<ItemData>>>
    suspend fun homeLiveData(): LiveData<Resource<List<ItemViewModel>>>
}