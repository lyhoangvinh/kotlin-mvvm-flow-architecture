package com.vinh.data.comon

import android.renderscript.Sampler
import androidx.paging.*
import com.vinh.data.source.VideoSource
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import okio.`-DeprecatedOkio`.source
import retrofit2.Response


suspend fun <T1, T2, T3, T4, R> zip(
    source1: Deferred<T1>,
    source2: Deferred<T2>,
    source3: Deferred<T3>,
    source4: Deferred<T4>,
    coroutineStart: CoroutineStart = CoroutineStart.DEFAULT,
    zipper: (T1, T2, T3, T4) -> R
): Deferred<R> =
    coroutineScope {
        async(start = coroutineStart) {
            zipper(source1.await(), source2.await(), source3.await(), source4.await())
        }
    }

/**
 * @function - map: Transform the items from Deferred by applying some function
 * @param mapper - function that will be used for deferred result
 */
suspend fun <T, R> Deferred<T>.map(mapper: (T) -> R): Deferred<R> =
    coroutineScope {
        async {
            mapper(this@map.await())
        }
    }


suspend fun <T1, T2, T3, T4, R> zipFlow(
    source1: suspend () -> T1,
    source2: suspend () -> T2,
    source3: suspend () -> T3,
    source4: suspend () -> T4,
    zipper: (T1, T2, T3, T4) -> R
): Flow<R> =
    flowOf(source1).zip(flowOf(source2)) { data1, data2 ->
        Pair(data1, data2)
    }.zip(flowOf(source3)) { data12, data3 ->
        Triple(data12.first, data12.second, data3)
    }.zip(flowOf(source4)) { data123, data4 ->
        zipper.invoke(data123.first(), data123.second(), data123.third(), data4())
    }

fun Int?.isNullOr0() = this == null || this == 0