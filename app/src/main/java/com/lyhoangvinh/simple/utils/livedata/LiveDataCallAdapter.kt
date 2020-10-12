package com.lyhoangvinh.simple.utils.livedata

import androidx.lifecycle.LiveData
import com.lyhoangvinh.simple.data.entities.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class LiveDataCallAdapter<R>(private val responseType: Type): CallAdapter<R, LiveData<Resource<R>>> {
    override fun adapt(call: Call<R>): LiveData<Resource<R>> {
        return object : LiveData<Resource<R>>() {
            private var isSuccess = false

            override fun onActive() {
                super.onActive()
                if (!isSuccess) enqueue()
            }

            override fun onInactive() {
                super.onInactive()
                dequeue()
            }

            private fun dequeue() {
                if (call.isExecuted) call.cancel()
            }

            private fun enqueue() {
                postValue(Resource.loading())
//                call.request { response ->
//                    response.onSuccess {
//                        postValue(Resource.success(data))
//                        isSuccess = true
//                    }.onError {
//                        postValue(Resource.error(message()))
//                    }.onException {
//                        postValue(Resource.error(message()))
//                    }
//                }
                call.clone().enqueue(object : Callback<R> {
                    override fun onFailure(call: Call<R>, t: Throwable) {
                        //todo: ErrorEntity
                        postValue(Resource.error(t.message))
                    }

                    override fun onResponse(call: Call<R>, response: Response<R>) {
                         if (response.isSuccessful) {
                            val body = response.body()
                            if (body == null || response.code() == 204) {
                                postValue(Resource.error("Null Data", response.code()))
                            } else {
                                postValue(Resource.success(body))
                            }
                        } else {
                             postValue(Resource.error(response.errorBody()?.string()?:response.message(), response.code()))
                        }
                        isSuccess = true
                    }
                })
            }
        }
    }

    override fun responseType(): Type = responseType
}