package com.vinh.domain.repo

import com.vinh.domain.entities.avgle.Favorite

interface FavoriteRepo {
    suspend fun getFavorite() : List<Favorite>
    suspend fun addFavorite(id: String): Long
    suspend fun deleteFavorite(id: String): Int
}