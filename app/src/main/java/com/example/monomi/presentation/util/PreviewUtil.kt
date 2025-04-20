package com.example.monomi.presentation.util

import com.example.monomi.core.model.SearchItem

object PreviewUtil {
    fun mockSearchItem(isBookmarked: Boolean) = SearchItem(
        id = "test",
        thumbnailUrl = "",
        linkUrl = "",
        dateTime = "2025-01-01T00:00:00.000Z",
        type = SearchItem.Type.IMAGE,
        isBookmarked = isBookmarked
    )
}