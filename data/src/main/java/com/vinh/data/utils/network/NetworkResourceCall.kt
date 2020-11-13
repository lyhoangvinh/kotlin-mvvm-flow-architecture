package com.lyhoangvinh.simple.utils.network

import com.vinh.domain.model.Resource
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NetworkResourceCall<S : Any>(
    private val delegate: Call<S>
) : Call<Resource<S>> {

    override fun enqueue(callback: Callback<Resource<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResourceCall,
                            Response.success(Resource.success(body))
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkResourceCall,
                            Response.success(Resource.error(null))
                        )
                    }
                } else {
                    if (error != null) {
                        callback.onResponse(
                            this@NetworkResourceCall,
                            Response.success(Resource.error(error.toString(), code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResourceCall,
                            Response.success(Resource.error(null))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                callback.onResponse(this@NetworkResourceCall, Response.success(Resource.error(throwable.message)))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResourceCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<Resource<S>> {
        throw UnsupportedOperationException("NetworkResourceCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
