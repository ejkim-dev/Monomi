package com.example.monomi.core.data.cache

import com.example.monomi.core.data.cache.model.CacheEntry
import java.util.concurrent.ConcurrentHashMap

object InMemoryDataSource {
    // CacheEntry 계층만 저장하므로 Any·KClass 추적이 필요 없음
    // 동시성 문제 고려
    private val store =  ConcurrentHashMap<String, CacheEntry>()

    // 이제 CacheEntry 계층만 저장하므로 Any·KClass 추적이 필요 없음
    fun saveData(key: String, value: CacheEntry) {
        store.put(key, value)
    }

    // 캐시 조회 – 요청한 CacheEntry의 서브타입이 아니면 null
    @Suppress("UNCHECKED_CAST")
    fun <T : CacheEntry> loadData(key: String, defaultValue: CacheEntry? = null): T? =
        (store[key] as? T) ?: (defaultValue as? T)


    fun clearData(key: String) = store.remove(key)
    fun clearAll() = store.clear()
}