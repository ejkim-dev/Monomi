package com.example.monomi.domain.repository

import com.example.monomi.domain.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun search(query: String, page: Int, pageSize: Int): List<SearchItem>
}

interface BookmarkRepository {
    fun observeBookmarks(): Flow<List<SearchItem>>
    suspend fun toggle(item: SearchItem)            // 북마크 저장·해제 토글
    suspend fun isBookmarked(id: String): Boolean
}