package com.example.monomi.core.data.repository.bookmark

import android.util.Log
import com.example.monomi.core.model.SearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeBookmarkRepository @Inject constructor(
) : BookmarkRepository {

    private val bookmarks = MutableStateFlow<List<SearchItem>>(emptyList())

    override fun observeBookmarks(): Flow<List<SearchItem>> = bookmarks

    override suspend fun toggle(item: SearchItem) {
        bookmarks.update { list ->
            Log.d("######", "toggle: ${list.size}")
            if (list.any { it.id == item.id }) list.filterNot { it.id == item.id }
            else list + item.copy(isBookmarked = true)
        }
    }

    override suspend fun isBookmarked(id: String): Boolean =
        bookmarks.value.any { it.id == id }
}