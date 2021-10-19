package com.vinh.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vinh.data.dao.FavoriteDao
import com.vinh.domain.entities.FavoriteIntent
import com.vinh.domain.entities.FavoriteItem
import com.vinh.domain.entities.avgle.Favorite
import com.vinh.domain.entities.avgle.Video
import com.vinh.domain.repo.FavoriteRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepoImpl @Inject constructor(private val favoriteDao: FavoriteDao) : FavoriteRepo {
    override suspend fun getFavorites(): List<Favorite> = favoriteDao.getFavorite()
    override suspend fun addFavorite(video: Video): Long = favoriteDao.add(video.createFavorite(currentTimeStamp()))
    override suspend fun deleteFavorite(id: String): Int = favoriteDao.deleteFromId(id)
    override suspend fun deleteFavorites(ids: List<String>):Int = favoriteDao.deleteFavorites(ids)
    override fun getCount(): LiveData<Int> = favoriteDao.getCount().distinctUntilChanged()
    override fun getFlow(ids: List<String>): Flow<FavoriteIntent> = favoriteDao.getFlow().map {
        FavoriteIntent(it.map { favorite -> FavoriteItem(favorite, ids.any { id -> id == favorite.id }) }, ids.isNotEmpty())
    }
    override fun getPaging(ids: List<String>): Flow<PagingData<FavoriteItem>> = Pager(
        config = PagingConfig(
            pageSize = 1,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { favoriteDao.pagingSource() }
    ).flow.map {
        it.map { favorite -> FavoriteItem(favorite, ids.any { id -> id == favorite.id }) }
    }

    private fun currentTimeStamp() = System.currentTimeMillis() / 1000
}