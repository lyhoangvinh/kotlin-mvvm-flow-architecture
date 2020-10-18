package com.lyhoangvinh.simple.data.source

import android.util.Log
import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.entities.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResource(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(
                    body
                )
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.e("BaseDataSource ",message)
        return Resource.error("Network call has failed for a following reason: $message")
    }

    protected suspend fun <T> resultFlow(call: suspend () -> Response<T>): Flow<Resource<T>> =
        flow {
            emit(Resource.loading())
            val responseStatus = getResource { call.invoke() }
            if (responseStatus.status == Status.SUCCESS) {
                emit(responseStatus)
            } else if (responseStatus.status == Status.ERROR) {
                emit(Resource.error(responseStatus.message.orEmpty()))
            }
        }
}

