package com.lyhoangvinh.simple.utils.extension

import androidx.lifecycle.*
import com.google.gson.Gson
import com.lyhoangvinh.simple.BuildConfig
import com.lyhoangvinh.simple.utils.livedata.LiveDataCallAdapterFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

fun makeOkHttpClientBuilder(): OkHttpClient.Builder {
    val logging = HttpLoggingInterceptor()

    if (BuildConfig.DEBUG) {
        logging.level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
//        .addInterceptor(UnauthorisedInterceptor(context))
        .addInterceptor(logging)
        .followRedirects(true)
        .followSslRedirects(true)
        .retryOnConnectionFailure(true)
        .cache(null)
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
}

fun <T> makeService(
    serviceClass: Class<T>,
    gson: Gson,
    okHttpClient: OkHttpClient,
    url: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(ServiceResponseConverter(gson))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()
    return retrofit.create(serviceClass)
}


/**
 * call that supports String & Gson and always uses json as its request body
 */

class ServiceResponseConverter(
    private val gSon: Gson
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return try {
            if (type === String::class.java) {
                StringResponseConverter()
            } else GsonConverterFactory.create(gSon).responseBodyConverter(
                type!!,
                annotations,
                retrofit
            )
        } catch (ignored: OutOfMemoryError) {
            null
        }
    }

    private class StringResponseConverter : Converter<ResponseBody, String> {
        @Throws(IOException::class)
        override fun convert(value: ResponseBody): String {
            return value.string()
        }
    }
}

/**
 * Observe liveFavoriteData in a cleaner way.
 */
fun <T> LiveData<T>?.observe(viewLifecycleOwner: LifecycleOwner, callBack: (data: T) -> Unit) {
    this?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
        callBack.invoke(it)
    })
}

fun <T> MediatorLiveData<T>?.observe(viewLifecycleOwner: LifecycleOwner, callBack: (data: T) -> Unit) {
    this?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
        callBack.invoke(it)
    })
}

fun <T> LiveData<T>?.observe(callBack: (data: T?) -> Unit) {
    this?.observeForever {
        callBack.invoke(it)
    }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}


/**
 * @function - zip: Combine items from two Deferreds together via a specified function and return items based on the results of this function
 * @param source1 - first deferred, whose result will be used in zipper
 * @param source2 - second deferred, whose result will be used in zipper
 * @param zipper - function, that will be called with deferred's results
 */
suspend fun <T1, T2, R> zip(source1: Deferred<T1>, source2: Deferred<T2>, coroutineStart: CoroutineStart = CoroutineStart.DEFAULT, zipper: (T1, T2) -> R): Deferred<R> =
    coroutineScope {
        async(start = coroutineStart) {
            zipper(source1.await(), source2.await())
        }
    }

suspend fun <T1, T2, T3, T4, R> zip(source1: Deferred<T1>, source2: Deferred<T2>, source3: Deferred<T3>, source4: Deferred<T4>,coroutineStart: CoroutineStart = CoroutineStart.DEFAULT,
                                    zipper: (T1, T2, T3, T4) -> R): Deferred<R> =
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


suspend fun <T1, T2, T3, T4, R> zipFlow(source1: suspend () -> T1,
                                    source2: suspend () -> T2,
                                    source3: suspend () -> T3,
                                    source4: suspend () -> T4,
                                    zipper: (T1, T2, T3, T4) -> R): Flow<R> =
         flowOf(source1).zip(flowOf(source2)) { data1, data2 ->
             Pair(data1, data2)
         }.zip(flowOf(source3)) { data12, data3 ->
             Triple(data12.first, data12.second, data3)
         }.zip(flowOf(source4)) { data123, data4 ->
             zipper.invoke(data123.first(), data123.second(), data123.third(), data4())
         }

/**
 * ⚠Note: The code ordinal() will work only with 1st level sealed classes.
 * If you need something more sophisticated, make sure to adjust that method accordingly.
 * ⚠Note 2: Like many features using reflection, when using R8’s or Proguard’s obfuscation, you can encounter runtime crashes.
 * You need to make sure both the classes and the companion objects are available during runtime. One way to do it is with the @Keep annotation.
 */
inline fun <reified T : Any> T.ordinal(): Int {
    // we differenciate if it's top level sealed class here 👇
    if (T::class.isSealed) {
        return T::class.java.classes.indexOfFirst { sub -> sub == javaClass }
    }
    // and we now added this part 👇
    val kLass = if (T::class.isCompanion) {
        javaClass.declaringClass
    } else {
        javaClass
    }

    return kLass?.superclass?.classes?.indexOfFirst { it == kLass } ?: -1
}

inline fun <reified T : Any> T.ordinal2(): Int =
    T::class.java.classes.indexOfFirst { sub -> sub == javaClass }

