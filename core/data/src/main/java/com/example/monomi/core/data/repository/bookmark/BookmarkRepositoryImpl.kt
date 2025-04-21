package com.example.monomi.core.data.repository.bookmark

import com.example.monomi.core.database.BookmarkDao
import com.example.monomi.core.database.entity.mapper.asDomain
import com.example.monomi.core.database.entity.mapper.asEntity
import com.example.monomi.core.model.SearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {

    override fun observeBookmarks(): Flow<List<SearchItem>> {
        return bookmarkDao.getBookmarks().map { entities ->
            entities.map { it.asDomain() }
        }
    }

    override suspend fun toggle(item: SearchItem) {
        val isBookmarked = isBookmarked(item.id)
        if (isBookmarked) {
            bookmarkDao.deleteBookmark(item.id)
        } else {
            bookmarkDao.insertBookmark(item.asEntity())
        }
    }

    override suspend fun isBookmarked(id: String): Boolean =
        bookmarkDao.isBookmarked(id)
}