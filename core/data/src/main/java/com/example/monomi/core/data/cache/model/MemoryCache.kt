package com.example.monomi.core.data.cache.model

// 캐시에 넣을 수 있는 타입을 모두 여기로
sealed interface CacheEntry { val timestamp: Long }

data class MemoryCache<T>(
    override val timestamp: Long,
    val items: List<T>
): CacheEntry