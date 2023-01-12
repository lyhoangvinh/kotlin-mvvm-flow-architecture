package com.vinh.domain.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vinh.data.entities.Entities
import com.vinh.domain.model.entities.avgle.Collection

data class CollectionsResponseAvgle(

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

    @SerializedName("collections")
    @Expose
    var collections: List<Collection>
 ) : Entities<Collection> {
    override fun listData(): List<Collection> = collections
}