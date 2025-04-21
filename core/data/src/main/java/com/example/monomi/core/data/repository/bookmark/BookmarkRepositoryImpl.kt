package com.example.monomi.core.data.repository.bookmark

import com.example.monomi.core.model.SearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
) : BookmarkRepository {

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