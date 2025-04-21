package com.example.monomi.core.network.source.network

import com.example.monomi.core.network.model.SearchImageResponse
import com.example.monomi.core.network.model.SearchVideoResponse
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class MonomiClient @Inject constructor(
    private val provideKakaoApi: KakaoApi
) {
    suspend fun fetchSearchImage(query: String, page: Int, size: Int): ApiResponse<SearchImageResponse> {
        return provideKakaoApi.fetchSearchImages(
            query = query,
            page = page * PAGING_SIZE,
            size = size
        )
    }

    suspend fun fetchSearchVideo(query: String, page: Int, size: Int): ApiResponse<SearchVideoResponse> {
        return provideKakaoApi.fetchSearchVideos(
            query = query,
            page = page * PAGING_SIZE,
            size = size
        )
    }

    companion object {
        private const val PAGING_SIZE = 20
    }
}