package com.example.monomi.core.data.repository.search

import com.example.monomi.core.model.ResultModel
import com.example.monomi.core.model.SearchItem


interface SearchRepository {
    suspend fun search(
        query: String,
        page: Int
    ): ResultModel<List<SearchItem>>
}