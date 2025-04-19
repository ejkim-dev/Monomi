package com.example.monomi.data.repository

import com.example.monomi.domain.model.SearchItem
import com.example.monomi.domain.repository.SearchRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class FakeSearchRepository : SearchRepository {

    private val previewPool = List(200) { index ->
        val isImage = index % 2 == 0
        SearchItem(
            id = "mock_$index",
            thumbnailUrl = "https://picsum.photos/200/200?random=$index",
            linkUrl = "https://example.com/$index",
            datetime = "2025-04-${((index % 30) + 1).toString()
                .padStart(2, '0')}T12:00:00",
            type = if (isImage) SearchItem.Type.IMAGE else SearchItem.Type.VIDEO
        )
    }

    override suspend fun search(query: String, page: Int, pageSize: Int): List<SearchItem> {
        delay(400)                     // fake 네트워크 시간
        val start = (page - 1) * pageSize
        val end = (start + pageSize).coerceAtMost(previewPool.size)
        return previewPool.subList(start, end)
            .shuffled(Random(page * 13))          // 페이지마다 다양하게 하기 위힘
    }
}