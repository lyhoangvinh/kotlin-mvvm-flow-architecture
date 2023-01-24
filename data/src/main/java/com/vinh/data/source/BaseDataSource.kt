package com.vinh.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.*
import com.vinh.domain.model.ItemViewModel
import com.vinh.domain.model.Resource
import com.vinh.domain.model.Status
import kotlinx.coroutines.Dispatchers
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
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.e("BaseDataSource ", message)
        return Resource.error("Network call has failed for a following reason: $message")
    }

    /**
     * The database serves as the single source of truth.
     * Therefore UI can receive data updates from database only.
     * Function notify UI about:
     * [Status.SUCCESS] - with data from database
     * [Status.ERROR] - if error has occurred from any source
     * [Status.LOADING]
     */

    fun <T> resultLiveData(networkCall: suspend () -> Response<T>): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val responseStatus = getResource { networkCall.invoke() }
            if (responseStatus.status == Status.SUCCESS) {
                emit(responseStatus)
            } else if (responseStatus.status == Status.ERROR) {
                emit(Resource.error(responseStatus.message.orEmpty()))
            }
        }

    fun <T: Any, A> resultLiveData(databaseQuery: () -> LiveData<T>,
                              networkCall: suspend () -> Response<A>,
                              saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val source = databaseQuery.invoke().map { Resource.success(it) }
            emitSource(source)
            val responseStatus = getResource { networkCall.invoke() }
            if (responseStatus.status == Status.SUCCESS) {
                saveCallResult(responseStatus.data!!)
            } else if (responseStatus.status == Status.ERROR) {
                emit(Resource.error(responseStatus.message.orEmpty()))
                emitSource(source)
            }
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

    protected suspend fun <T>  resultFlow2(call: suspend () -> Resource<T>): Flow<Resource<T>> =
        flow {
            emit(Resource.loading())
            val responseStatus : Resource<T> = try {
                 call.invoke()
            } catch (e: Exception) {
                 error(e.message ?: e.toString())
            }
            if (responseStatus.status == Status.SUCCESS) {
                emit(responseStatus)
            } else if (responseStatus.status == Status.ERROR) {
                emit(Resource.error(responseStatus.message.orEmpty()))
            }
        }

    protected suspend fun <T> resultFlow(call: suspend () -> Response<T>,
                                         saveCallResult: suspend (T?) -> Unit)
            : Flow<Resource<T>> =
        flow {
            emit(Resource.loading())
            val responseStatus = getResource { call.invoke() }
            if (responseStatus.status == Status.SUCCESS) {
                saveCallResult(responseStatus.data)
                emit(responseStatus)
            } else if (responseStatus.status == Status.ERROR) {
                emit(Resource.error(responseStatus.message.orEmpty()))
            }
        }

    protected suspend fun <T> resultFlowMapper(call: suspend () -> Response<T>, mapCallResult: suspend (T?) -> List<ItemViewModel>): Flow<Resource<List<ItemViewModel>>> =
        flow {
            emit(Resource.loading())
            val responseStatus = getResource { call.invoke() }
            if (responseStatus.status == Status.SUCCESS) {
                emit(Resource.success(mapCallResult.invoke(responseStatus.data)))
            } else if (responseStatus.status == Status.ERROR) {
                emit(Resource.error(responseStatus.message.orEmpty()))
            }
        }

    fun <K : Any, T : Any> pagingData(
        source: suspend (K?) -> List<T>,
        prevKey: (K?) -> K?,
        nextKey: (K?) -> K?,
        refreshKey: (state: PagingState<K, T>) -> K?,
        pageSize: Int,
        enablePlaceholders: Boolean = true
    ): Flow<PagingData<T>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = enablePlaceholders
            ),
            pagingSourceFactory = {
                object : PagingSource<K, T>() {
                    override fun getRefreshKey(state: PagingState<K, T>): K? = refreshKey(state)
                    override suspend fun load(params: LoadParams<K>): LoadResult<K, T> {
                        val paramsKey = params.key
                        return try {
                            LoadResult.Page(
                                data = source(paramsKey),
                                prevKey = prevKey(paramsKey),
                                nextKey = nextKey(paramsKey)
                            )
                        } catch (e: Exception) {
                            return LoadResult.Error(e)
                        }
                    }
                }
            }).flow
    }
}


