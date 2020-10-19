package com.lyhoangvinh.simple.data.repo

import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.ui.base.adapter.ItemViewModel
import kotlinx.coroutines.flow.Flow

interface AvgRepo {
    suspend fun getHomeData(): Flow<Resource<List<ItemViewModel>>>
}