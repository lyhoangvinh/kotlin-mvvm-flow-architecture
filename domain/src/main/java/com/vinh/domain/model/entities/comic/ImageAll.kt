package com.vinh.domain.model.entities.comic

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class ImageAll(
    @SerializedName("medium_url")
    @Expose
    var medium_url: String? = "",

    @SerializedName("thumb_url")
    @Expose
    var thumbUrl: String? = "",

    @SerializedName("super_url")
    @Expose
    var superUrl: String? = ""
)
