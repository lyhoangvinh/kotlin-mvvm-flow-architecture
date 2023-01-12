package com.vinh.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.vinh.domain.model.entities.avgle.VideoRemoteKey

@Dao
interface VideoRemoteKeyDao : BaseDao<VideoRemoteKey> {
    @Query("SELECT * FROM video_remote_keys WHERE executor=:executor")
    suspend fun remoteKeyByExecutor(executor: String): VideoRemoteKey

    @Query("DELETE FROM video_remote_keys WHERE executor = :executor")
    suspend fun deleteByExecutor(executor: String)
}