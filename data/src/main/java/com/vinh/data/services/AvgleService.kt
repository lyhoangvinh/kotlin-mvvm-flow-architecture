package com.vinh.data.services

import com.vinh.domain.response.BaseResponseAvgle
import com.vinh.domain.response.CategoriesResponse
import com.vinh.domain.response.CollectionsResponseAvgle
import com.vinh.domain.response.VideosResponseAvgle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface AvgleService {

    @GET("categories")
    suspend fun getCategories(): Response<BaseResponseAvgle<CategoriesResponse>>

    @GET("collections/{page}")
    suspend fun getCollections(@Path("page") page: Int, @Query("limit") limit: Int): Response<BaseResponseAvgle<CollectionsResponseAvgle>>

    @GET("collections/{keyword}")
    suspend fun getVideoCollections(@Path("keyword") keyword: String): Response<BaseResponseAvgle<CollectionsResponseAvgle>>

    @GET("videos/{page}")
    suspend fun getVideosFromKeyword(@Path("page") page: Int, @Query("c") chId: String): Response<BaseResponseAvgle<VideosResponseAvgle>>

    @GET("videos/{page}")
    suspend fun getAllVideos(@Path("page") page: Int): Response<BaseResponseAvgle<VideosResponseAvgle>>

    @GET("search/{query}/{page}")
    suspend fun searchVideos(@Path("query") query: String, @Path("page") page: Int): Response<BaseResponseAvgle<VideosResponseAvgle>>

    @GET("jav/{query}/{page}")
    suspend fun searchJav(@Path("query") query: String, @Path("page") page: Int): Response<BaseResponseAvgle<VideosResponseAvgle>>
}