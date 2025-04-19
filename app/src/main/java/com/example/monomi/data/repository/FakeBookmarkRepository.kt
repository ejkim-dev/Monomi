package com.example.monomi.data.repository

import com.example.monomi.domain.model.SearchItem
import com.example.monomi.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeBookmarkRepository : BookmarkRepository {

    private val bookmarks = MutableStateFlow<List<SearchItem>>(emptyList())

    override fun observeBookmarks(): Flow<List<SearchItem>> = bookmarks

    override suspend fun toggle(item: SearchItem) {
        bookmarks.update { list ->
            if (list.any { it.id == item.id }) list.filterNot { it.id == item.id }
            else list + item.copy(isBookmarked = true)
        }
    }

    override suspend fun isBookmarked(id: String): Boolean =
        bookmarks.value.any { it.id == id }
}