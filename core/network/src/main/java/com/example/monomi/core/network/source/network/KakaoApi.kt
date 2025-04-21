package com.example.monomi.core.network.source.network

import com.example.monomi.core.network.model.SearchImageResponse
import com.example.monomi.core.network.model.SearchVideoResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoApi {

    @GET("v2/search/image")
    suspend fun fetchSearchImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ApiResponse<SearchImageResponse>

    @GET("v2/search/vclip")
    suspend fun fetchSearchVideos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ApiResponse<SearchVideoResponse>
}