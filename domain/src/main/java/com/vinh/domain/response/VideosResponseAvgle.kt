package com.vinh.domain.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vinh.data.entities.Entities
import com.vinh.domain.model.entities.avgle.Video

data class VideosResponseAvgle(

    @SerializedName("has_more")
    @Expose
    var hasMore: Boolean,

    @SerializedName("total_collections")
    @Expose
    var totalCollections: Int? = 0,

    @SerializedName("current_offset")
    @Expose
    var currentOffset: Int? = 0,

    @SerializedName("limit")
    @Expose
    var limit: Int? = 0,

    @SerializedName("videos")
    @Expose
    var videos: List<Video>

) : Entities<Video> {
    override fun listData(): List<Video> = videos
}