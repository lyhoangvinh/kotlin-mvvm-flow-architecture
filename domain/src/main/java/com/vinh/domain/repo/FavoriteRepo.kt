package com.vinh.domain.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vinh.domain.model.entities.FavoriteIntent
import com.vinh.domain.model.entities.FavoriteItem
import com.vinh.domain.model.entities.avgle.Favorite
import com.vinh.domain.model.entities.avgle.Video
import kotlinx.coroutines.flow.Flow

interface FavoriteRepo {
    suspend fun getFavorites() : List<Favorite>
    suspend fun addFavorite(video: Video): Long
    suspend fun deleteFavorite(id: String): Int
    suspend fun deleteFavorites(ids: List<String>): Int
    fun getCount(): LiveData<Int>
    fun getPaging(ids: List<String>): Flow<PagingData<FavoriteItem>>
    fun getFlow(ids: List<String>): Flow<FavoriteIntent>
}