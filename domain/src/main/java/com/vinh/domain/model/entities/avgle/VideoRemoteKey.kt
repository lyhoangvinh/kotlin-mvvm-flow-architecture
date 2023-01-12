package com.vinh.domain.model.entities.avgle

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_remote_keys")
data class VideoRemoteKey(
    @PrimaryKey
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val executor: String, // technically mutable but fine for a demo
    val nextPageKey: Int?
)
