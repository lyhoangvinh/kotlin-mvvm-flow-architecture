package com.vinh.domain.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vinh.domain.entities.avgle.Favorite
import com.vinh.domain.entities.avgle.Video
import kotlinx.coroutines.flow.Flow

interface FavoriteRepo {
    suspend fun getFavorite() : List<Favorite>
    suspend fun addFavorite(video: Video): Long
    suspend fun deleteFavorite(id: String): Int
    fun getCount(): LiveData<Int>
    fun getPaging(): Flow<PagingData<Favorite>>
}