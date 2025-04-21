package com.example.monomi.core.data.repository.search

import com.example.monomi.core.data.cache.InMemoryDataSource
import com.example.monomi.core.data.cache.model.MemoryCache
import com.example.monomi.core.data.mapper.mapImageResponseToItems
import com.example.monomi.core.data.mapper.mapVideoResponseToItems
import com.example.monomi.core.model.ResultModel
import com.example.monomi.core.model.SearchItem
import com.example.monomi.core.network.Dispatcher
import com.example.monomi.core.network.MonomiAppDispatchers
import com.example.monomi.core.network.service.MonomiClient
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class SearchRepositoryImpl @Inject constructor(
    private val monomiClient: MonomiClient,
    private val cacheSource: InMemoryDataSource,
    @Dispatcher(MonomiAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : SearchRepository {

    override suspend fun search(
        query: String,
        page: Int
    ): ResultModel<List<SearchItem>> = withContext(ioDispatcher) {

        if (query.isBlank()) return@withContext ResultModel.Empty

        // 캐시데이터 우선 조회
        val cacheKey = createCacheKey(query, page)
        getCachedData(cacheKey)?.let {
            return@withContext ResultModel.Success(it)
        }

        when (val searchResult = fetchSearchResults(query, page)) {
            is ResultModel.Success -> {
                val items = searchResult.data
                cacheSource.saveData(cacheKey, MemoryCache(System.currentTimeMillis(), items))
                ResultModel.Success(items)
            }

            is ResultModel.Error -> searchResult
            else -> ResultModel.Empty
        }
    }

    private suspend fun fetchSearchResults(
        query: String,
        page: Int
    ): ResultModel<List<SearchItem>> {
        // 이미지와 비디오 검색 동시 요청
        val imgResult = monomiClient.fetchSearchImage(query, page)
        val vidResult = monomiClient.fetchSearchVideo(query, page)

        // 둘 다 에러인 경우 첫 번째 에러 반환
        if (imgResult is ResultModel.Error && vidResult is ResultModel.Error) {
            return imgResult
        }

        // 하나라도 성공하면, 해당 응답을 매핑
        val imageItems = (imgResult as? ResultModel.Success)?.let {
            mapImageResponseToItems(it.data)
        }.orEmpty()

        val videoItems = (vidResult as? ResultModel.Success)?.let {
            mapVideoResponseToItems(it.data)
        }.orEmpty()

        // 결과 병합 및 정렬
        val mergedItems = mergeAndSortItems(imageItems, videoItems)

        return if (mergedItems.isEmpty()) {
            ResultModel.Empty
        } else {
            ResultModel.Success(mergedItems)
        }
    }

    // 이미지와 비디오 결과를 병합하고 날짜순으로 정렬
    private fun mergeAndSortItems(
        imageItems: List<SearchItem>,
        videoItems: List<SearchItem>
    ): List<SearchItem> {
        return (imageItems + videoItems)
            .sortedByDescending { it.dateTime }
    }

    private fun getCachedData(cacheKey: String): List<SearchItem>? {

        val cached = cacheSource.loadData<MemoryCache<SearchItem>>(cacheKey) ?: return null
        val currentTime = System.currentTimeMillis()

        // 캐시 만료
        if (currentTime - cached.timestamp > CACHE_DURATION_MS) {
            Timber.d("========= CACHE EXPIRED : $cacheKey")
            cacheSource.clearData(cacheKey)
            return null
        }

        Timber.d("========= CACHE DATA : ${cached.items}")
        return cached.items
    }

    private fun createCacheKey(query: String, page: Int): String = "$query|$page"

    companion object {
        // 캐싱 5분
        private const val CACHE_DURATION_MS = /*5 * 60 * 1000L*/ 10000L
    }
}
