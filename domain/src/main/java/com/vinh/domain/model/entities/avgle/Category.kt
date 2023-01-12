package com.vinh.domain.model.entities.avgle

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CATEGORY")
data class Category(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @SerializedName("CHID")
    @Expose
    var CHID: String? = "",

    @SerializedName("name")
    @Expose
    var name: String? = "",

    @SerializedName("slug")
    @Expose
    var slug: String? = "",

    @SerializedName("total_videos")
    @Expose
    var totalVideos: Int? = 0,

    @SerializedName("category_url")
    @Expose
    var categoryUrl: String? = "",

    @SerializedName("cover_url")
    @Expose
    var coverUrl: String? = ""
)
