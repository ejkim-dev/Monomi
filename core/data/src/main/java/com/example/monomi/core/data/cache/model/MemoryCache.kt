package com.example.monomi.core.data.cache.model

data class MemoryCache<T>(
    val timestamp: Long,
    val items: List<T>
)