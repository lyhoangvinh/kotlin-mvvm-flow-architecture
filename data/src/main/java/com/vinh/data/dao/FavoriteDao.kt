package com.vinh.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinh.domain.entities.avgle.Favorite

@Dao
interface FavoriteDao : BaseDao<Favorite> {
    @Query("SELECT * FROM Favorite")
    suspend fun getFavorite(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(favorite: Favorite): Long

    @Query("DELETE FROM Favorite where id=:id")
    suspend fun deleteFromId(id: String): Int
}