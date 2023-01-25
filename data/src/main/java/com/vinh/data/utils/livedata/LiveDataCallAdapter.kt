package com.vinh.data.utils.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinh.domain.model.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class LiveDataCallAdapter<R>(private val responseType: Type): CallAdapter<R, ApiLiveData<Resource<R>>> {
    override fun adapt(call: Call<R>): ApiLiveData<Resource<R>> {
        return object : ApiLiveData<Resource<R>>() {
            private var isSuccess = false

            override fun onActive() {
                super.onActive()
                if (!isSuccess){
                    setSource{ enqueue() }
                }
            }

            override fun onInactive() {
                super.onInactive()
                dequeue()
            }

            private fun dequeue() {
                if (call.isExecuted) call.cancel()
            }

            private fun enqueue() : LiveData<Resource<R>>  {
                val liveData = MutableLiveData<Resource<R>>(Resource.loading())
                call.clone().enqueue(object : Callback<R> {
                    override fun onFailure(call: Call<R>, t: Throwable) {
                        //todo: ErrorEntity
                        liveData.postValue(Resource.error(t.message))
                    }

                    override fun onResponse(call: Call<R>, response: Response<R>) {
                         if (response.isSuccessful) {
                            val body = response.body()
                            if (body == null || response.code() == 204) {
                                liveData.postValue(Resource.error("Null Data", response.code()))
                            } else {
                                liveData.postValue(Resource.success(body))
                            }
                        } else {
                             liveData.postValue(Resource.error(response.errorBody()?.string()?:response.message(), response.code()))
                        }
                        isSuccess = true
                    }
                })
                //call.request { response ->
//                    response.onSuccess {
//                        postValue(Resource.success(data))
//                        isSuccess = true
//                    }.onError {
//                        postValue(Resource.error(message()))
//                    }.onException {
//                        postValue(Resource.error(message()))
//                    }
//                }
                return liveData
            }
        }
    }

    override fun responseType(): Type = responseType
}