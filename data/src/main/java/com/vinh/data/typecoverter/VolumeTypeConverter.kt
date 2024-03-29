package com.vinh.data.typecoverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.vinh.domain.model.entities.comic.Volume

class VolumeTypeConverter {

    @TypeConverter
    fun stringToImageAll(data: String?): Volume? {
        return Gson().fromJson(data, Volume::class.java)
    }

    @TypeConverter
    fun imageAllToString(data: Volume?): String? {
        return Gson().toJson(data)
    }
}