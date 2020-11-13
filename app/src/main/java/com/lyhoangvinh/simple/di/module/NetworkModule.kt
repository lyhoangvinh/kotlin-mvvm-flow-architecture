package com.lyhoangvinh.simple.di.module

import com.google.gson.Gson
import com.lyhoangvinh.simple.utils.Constants
import com.vinh.data.services.AvgleService
import com.vinh.data.services.ComicVineService
import com.lyhoangvinh.simple.di.qualifier.OkHttpNoAuth
import com.lyhoangvinh.simple.utils.extension.makeOkHttpClientBuilder
import com.lyhoangvinh.simple.utils.extension.makeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @OkHttpNoAuth
    @Singleton
    internal fun provideOkHttpClientNoAuth(): OkHttpClient = makeOkHttpClientBuilder().build()

    @Provides
    @Singleton
    internal fun provideComicVineService(gSon: Gson, @OkHttpNoAuth okHttpClient: OkHttpClient): ComicVineService =
        makeService(ComicVineService::class.java, gSon, okHttpClient, Constants.COMIC_ENDPOINT)

    @Provides
    @Singleton
    internal fun provideAvgleService(gson: Gson, @OkHttpNoAuth okHttpClient: OkHttpClient): AvgleService =
        makeService(AvgleService::class.java, gson, okHttpClient, Constants.AVGLE_ENDPOINT)
}
