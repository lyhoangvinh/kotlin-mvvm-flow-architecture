package com.vinh.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vinh.data.dao.FavoriteDao
import com.vinh.domain.entities.avgle.Favorite
import com.vinh.domain.entities.avgle.Video
import com.vinh.domain.repo.FavoriteRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepoImpl @Inject constructor(private val favoriteDao: FavoriteDao) : FavoriteRepo {
    override suspend fun getFavorite(): List<Favorite> = favoriteDao.getFavorite()
    override suspend fun addFavorite(video: Video): Long = favoriteDao.add(video.createFavorite(currentTimeStamp()))
    override suspend fun deleteFavorite(id: String): Int = favoriteDao.deleteFromId(id)
    override fun getCount(): LiveData<Int> = favoriteDao.getCount().distinctUntilChanged()
    override fun getPaging(): Flow<PagingData<Favorite>> = Pager(
        config = PagingConfig(
            pageSize = 1,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { favoriteDao.pagingSource() }
    ).flow

    private fun currentTimeStamp() = System.currentTimeMillis() / 1000
}