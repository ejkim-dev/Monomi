package com.example.monomi.core.data.repository.bookmark

import com.example.monomi.core.model.SearchItem
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun observeBookmarks(): Flow<List<SearchItem>>
    suspend fun toggle(item: SearchItem)            // 북마크 저장·해제 토글
    suspend fun isBookmarked(id: String): Boolean
}