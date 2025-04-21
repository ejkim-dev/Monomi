package com.example.monomi.core.data.repository.search

import com.example.monomi.core.model.ResultModel
import com.example.monomi.core.model.SearchItem
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class FakeSearchRepository @Inject constructor() : SearchRepository {

    private val previewPool = List(200) { index ->
        val isImage = index % 2 == 0
        SearchItem(
            id = "mock_$index",
            thumbnailUrl = "https://picsum.photos/200/200?random=$index",
            linkUrl = "https://example.com/$index",
            dateTime = "2025-04-${
                ((index % 30) + 1).toString()
                    .padStart(2, '0')
            }T12:00:00",
            type = if (isImage) SearchItem.Type.IMAGE else SearchItem.Type.VIDEO
        )
    }

    override suspend fun search(query: String, page: Int): ResultModel<List<SearchItem>> {
        delay(400)                     // fake 네트워크 시간
        val start = (page - 1) * 20
        val end = (start + 20).coerceAtMost(previewPool.size)
        val result = previewPool.subList(start, end)
            .shuffled(Random(page * 13))          // 페이지마다 다양하게 하기 위힘
        return ResultModel.Success(result)
    }
}