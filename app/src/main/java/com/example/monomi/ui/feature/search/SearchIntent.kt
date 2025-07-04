package com.example.monomi.ui.feature.search

import com.example.monomi.core.model.SearchItem

sealed interface SearchIntent {
    data class TextChanged(val value: String) : SearchIntent
    data object Search : SearchIntent
    data object LoadNextPage : SearchIntent
    data class ToggleBookmark(val item: SearchItem) : SearchIntent
}

data class SearchUiState(
    val query: String = "",
    val items: List<SearchItem> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false
)