package com.vinh.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import com.vinh.data.dao.FavoriteDao
import com.vinh.domain.entities.avgle.Favorite
import com.vinh.domain.repo.FavoriteRepo
import javax.inject.Inject

class FavoriteRepoImpl @Inject constructor(private val favoriteDao: FavoriteDao) : FavoriteRepo {
    override suspend fun getFavorite(): List<Favorite> = favoriteDao.getFavorite()
    override suspend fun addFavorite(id: String): Long = favoriteDao.add(Favorite(id))
    override suspend fun deleteFavorite(id: String): Int = favoriteDao.deleteFromId(id)
    override fun getCount(): LiveData<Int> = favoriteDao.getCount().distinctUntilChanged()
}