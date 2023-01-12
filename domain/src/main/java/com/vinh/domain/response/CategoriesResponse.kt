package com.vinh.domain.response

 import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
 import com.vinh.domain.model.entities.avgle.Category

data class CategoriesResponse(

    @SerializedName("categories")
    @Expose
    var categories: List<Category>? = ArrayList()

)