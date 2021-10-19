package com.vinh.domain.entities.avgle

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    var id: String,
    var title: String?,
    var likes: Long?,
    var dislikes: Long?,
    var viewNumber: Long?,
    var embeddedUrl: String?,
    var previewUrl: String?,
    var previewVideoUrl: String?,
    var hd: Boolean?,
    var videoUrl: String?,
    var likeTimeStamp: Long?
)