package com.example.monomi.ui.util

import com.example.monomi.core.model.SearchItem

object PreviewUtil {
    fun mockSearchItem(isBookmarked: Boolean) = SearchItem(
        id = "test",
        title = "title",
        thumbnailUrl = "",
        dateTime = "2025-01-01T00:00:00.000Z",
        type = SearchItem.Type.IMAGE,
        isBookmarked = isBookmarked
    )
}