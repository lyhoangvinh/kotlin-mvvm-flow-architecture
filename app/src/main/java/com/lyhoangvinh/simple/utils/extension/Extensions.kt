package com.lyhoangvinh.simple.utils.extension

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.lyhoangvinh.simple.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
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

