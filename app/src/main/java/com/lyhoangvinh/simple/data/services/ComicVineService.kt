package com.lyhoangvinh.simple.data.services
import com.lyhoangvinh.simple.data.entities.Resource
import com.lyhoangvinh.simple.data.entities.comic.Issues
import com.lyhoangvinh.simple.data.response.BaseResponseComic
import com.lyhoangvinh.simple.utils.livedata.ApiLiveData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicVineService {

    @GET("issues")
    suspend fun getIssues(
        @Query("limit") limit: Int, @Query("offset") offset: Int
        , @Query("api_key") api_key: String, @Query("format") format: String
        , @Query("sort") sort: String
    ):
            BaseResponseComic<Issues>

    @GET("issues")
    suspend fun getIssues2(
        @Query("limit") limit: Int, @Query("offset") offset: Int
        , @Query("api_key") api_key: String, @Query("format") format: String
        , @Query("sort") sort: String
    ): Response<BaseResponseComic<Issues>>

    @GET("issues")
    fun getIssues3(
        @Query("limit") limit: Int, @Query("offset") offset: Int
        , @Query("api_key") api_key: String, @Query("format") format: String
        , @Query("sort") sort: String
    ):
            Call<BaseResponseComic<Issues>>

    @GET("issues")
    fun getIssues4(
        @Query("limit") limit: Int, @Query("offset") offset: Int
        , @Query("api_key") api_key: String, @Query("format") format: String
        , @Query("sort") sort: String
    ):
            ApiLiveData<Resource<BaseResponseComic<Issues>>>


    @GET("issues")
    suspend fun getIssues5(
        @Query("limit") limit: Int, @Query("offset") offset: Int
        , @Query("api_key") api_key: String, @Query("format") format: String
        , @Query("sort") sort: String
    ): Resource<BaseResponseComic<Issues>>
}