package com.example.monomi.core.network.service

import com.example.monomi.core.model.ResultModel
import com.example.monomi.core.network.model.SearchImageResponse
import com.example.monomi.core.network.model.SearchVideoResponse
import com.example.monomi.core.network.model.mapper.mapToResultModel
import javax.inject.Inject

class MonomiClient @Inject constructor(
    private val provideKakaoApiService: KakaoApiService
) {
    suspend fun fetchSearchImage(query: String, page: Int): ResultModel<SearchImageResponse> {
        return provideKakaoApiService.fetchSearchImages(
            query = query,
            page = page * PAGING_SIZE,
            size = PAGING_SIZE
        ).mapToResultModel()
    }

    suspend fun fetchSearchVideo(query: String, page: Int): ResultModel<SearchVideoResponse> {
        return provideKakaoApiService.fetchSearchVideos(
            query = query,
            page = page * PAGING_SIZE,
            size = PAGING_SIZE
        ).mapToResultModel()
    }

    companion object {
        private const val PAGING_SIZE = 20
    }
}