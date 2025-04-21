package com.example.monomi.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.monomi.core.model.SearchItem

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String,
    val title: String,
    val dateTime: String,
    val thumbnailUrl: String,
    val type: SearchItem.Type,
    val bookmarkedAt: Long = System.currentTimeMillis()
): BaseEntity