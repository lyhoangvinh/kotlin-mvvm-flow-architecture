package com.vinh.data.typecoverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.vinh.domain.model.entities.comic.ImageAll

class ImageTypeConverter {

    @TypeConverter
    fun stringToImageAll(data: String?): ImageAll? {
        return Gson().fromJson(data, ImageAll::class.java)
    }

    @TypeConverter
    fun imageAllToString(data: ImageAll?): String? {
        return Gson().toJson(data)
    }
}