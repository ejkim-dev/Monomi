package com.example.monomi.core.data.cache

import kotlin.reflect.KClass

object InMemoryDataSource {
    // 타입 정보 저장
    private val store = mutableMapOf<String, Pair<KClass<*>, Any>>()

    // 타입 정보와 함께 데이터 저장
    fun <T : Any> saveData(key: String, value: T) {
        store[key] = Pair(value::class, value)
    }

    inline fun <reified T : Any> loadData(key: String, defaultValue: T? = null): T? {
        return loadData(key, T::class, defaultValue)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> loadData(key: String, clazz: KClass<T>, defaultValue: T? = null): T? {
        val pair = store[key] ?: return defaultValue

        // 저장된 타입과 요청된 타입 확인
        return if (clazz.isInstance(pair.second)) {
            pair.second as T
        } else {
            defaultValue
        }
    }


    fun clearData(key: String) = store.remove(key)
    fun clearAll() = store.clear()
}