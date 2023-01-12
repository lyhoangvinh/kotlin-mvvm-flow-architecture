package com.vinh.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinh.domain.model.entities.avgle.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao : BaseDao<Favorite> {
    @Query("SELECT * FROM Favorite ORDER BY likeTimestamp DESC")
    suspend fun getFavorite(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(favorite: Favorite): Long

    @Query("DELETE FROM Favorite where id=:id")
    suspend fun deleteFromId(id: String): Int

    @Query("DELETE FROM Favorite where id IN (:ids)")
    suspend fun deleteFavorites(ids: List<String>): Int

    @Query("SELECT COUNT(*) FROM Favorite")
    fun getCount(): LiveData<Int>

    @Query("SELECT * FROM Favorite ORDER BY likeTimestamp DESC")
    fun pagingSource(): PagingSource<Int, Favorite>

    @Query("SELECT * FROM Favorite ORDER BY likeTimestamp DESC")
    fun getFlow(): Flow<List<Favorite>>
}