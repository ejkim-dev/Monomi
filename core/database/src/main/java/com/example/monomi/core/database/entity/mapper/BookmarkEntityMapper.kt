package com.example.monomi.core.database.entity.mapper

import com.example.monomi.core.database.entity.BookmarkEntity
import com.example.monomi.core.model.SearchItem

object BookmarkEntityMapper : EntityMapper<SearchItem, BookmarkEntity> {
    override fun asEntity(domain: SearchItem): BookmarkEntity {
        return BookmarkEntity(
            id = domain.id,
            title = domain.title,
            dateTime = domain.dateTime,
            thumbnailUrl = domain.thumbnailUrl,
            type = domain.type
        )
    }

    override fun asDomain(entity: BookmarkEntity): SearchItem {
        return SearchItem(
            id = entity.id,
            title = entity.title,
            dateTime = entity.dateTime,
            thumbnailUrl = entity.thumbnailUrl,
            type = entity.type,
            isBookmarked = true
        )
    }
}

fun SearchItem.asEntity(): BookmarkEntity = BookmarkEntityMapper.asEntity(this)
fun BookmarkEntity.asDomain(): SearchItem = BookmarkEntityMapper.asDomain(this)