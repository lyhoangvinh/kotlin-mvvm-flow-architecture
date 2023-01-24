package com.vinh.data.source

import androidx.paging.*
import kotlinx.coroutines.flow.Flow


abstract class BasePagingData<K : Any, T : Any>(
    private val source: suspend (K) -> List<T>,
    private val prevKey: (K) -> K?,
    private val nextKey: (K, Boolean) -> K?,
    private val refreshKey: (state: PagingState<K, T>) -> K?,
    private val pageSize: Int,
    private val enablePlaceholders: Boolean = true,
    private val defaultKey: K
) {

    operator fun invoke(): Flow<PagingData<T>> = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = enablePlaceholders
        ),
        pagingSourceFactory = {
            object : PagingSource<K, T>() {
                override fun getRefreshKey(state: PagingState<K, T>): K? = refreshKey(state)
                override suspend fun load(params: LoadParams<K>): LoadResult<K, T> {
                    val paramsKey = params.key ?: defaultKey
                    return try {
                        val data = source(paramsKey)
                        LoadResult.Page(
                            data = data,
                            prevKey = prevKey(paramsKey),
                            nextKey = nextKey(paramsKey, data.isEmpty())
                        )
                    } catch (e: Exception) {
                        return LoadResult.Error(e)
                    }
                }
            }
        }).flow
}