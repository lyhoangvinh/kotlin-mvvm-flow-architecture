package com.vinh.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.asFlow
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vinh.data.utils.BaseSharedPreferenceLiveData
import com.vinh.data.utils.SingletonHolder
import kotlinx.coroutines.flow.Flow

/**
 * Created by LyHoangVinh on 11/5/2017.
 */

@Suppress("UNCHECKED_CAST")
class SharedPrefs private constructor(application: Context) {
    val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    companion object : SingletonHolder<SharedPrefs, Context>(::SharedPrefs) {
        private const val PREFS_NAME = "share_prefs" + "SharedPrefs"
    }

    operator fun <T> get(key: String, anonymousClass: Class<T>): T {
        return when (anonymousClass) {
            String::class.java -> mSharedPreferences.getString(key, "") as T
            Boolean::class.java -> mSharedPreferences.getBoolean(key, false) as T
            Float::class.java -> mSharedPreferences.getFloat(key, 0f) as T
            Int::class.java -> mSharedPreferences.getInt(key, 0) as T
            Long::class.java -> mSharedPreferences.getLong(key, 0) as T
            else -> Gson().fromJson(mSharedPreferences.getString(key, ""), anonymousClass)
        }
    }

    fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data as String)
            is Boolean -> editor.putBoolean(key, data as Boolean)
            is Float -> editor.putFloat(key, data as Float)
            is Int -> editor.putInt(key, data as Int)
            is Long -> editor.putLong(key, data as Long)
            else -> editor.putString(key, Gson().toJson(data))
        }
        editor.apply()
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }

    fun clearDataByKey(key: String) {
        mSharedPreferences.edit().remove(key).apply()
    }

    operator fun <T> get(
        key: String,
        anonymousClass: Class<T>,
        defValue: T
    ): Flow<T> = object : BaseSharedPreferenceLiveData<T>(this, key, defValue) {
        override fun getValueFromPreferences(key: String, defValue: T): T =
            get(key, anonymousClass) ?: defValue
    }.asFlow()

//    operator fun <T> get(
//        key: String,
//        typeOfT: TypeToken<T>,
//        defValue: T
//    ): Flow<T> = object : BaseSharedPreferenceLiveData<T>(
//        this, key, defValue
//    ) {
//        override fun getValueFromPreferences(key: String, defValue: T) =
//            get(key, typeOfT) ?: defValue
//    }.asFlow()
}
