package com.example.monomi.core.network.service

import com.example.monomi.core.network.model.SearchImageResponse
import com.example.monomi.core.network.model.SearchVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoApiService {

    @GET("v2/search/image")
    suspend fun fetchSearchImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchImageResponse>

    @GET("v2/search/vclip")
    suspend fun fetchSearchVideos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchVideoResponse>
}