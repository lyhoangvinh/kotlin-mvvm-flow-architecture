package com.vinh.data.utils.livedata

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory: CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
//        val isLiveData = rawType == LiveData::class.java
        val isRefreshableLiveData = rawType == ApiLiveData::class.java
        return if (isRefreshableLiveData) {
            val observableType = getParameterUpperBound(0, returnType as ParameterizedType) as? ParameterizedType
                ?: throw IllegalArgumentException("resource must be parameterized")
            LiveDataCallAdapter<Any>(getParameterUpperBound(0, observableType))
        } else {
            null
        }
    }
}