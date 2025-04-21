package com.example.monomi.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monomi.core.data.repository.bookmark.BookmarkRepository
import com.example.monomi.core.data.repository.search.SearchRepository
import com.example.monomi.core.model.ResultModel
import com.example.monomi.core.model.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepo: SearchRepository,
    private val bookmarkRepo: BookmarkRepository
) : ViewModel() {

    private var currentPage = 0
    private val intentFlow = MutableSharedFlow<SearchIntent>()

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    init {
        observeIntent()
    }

    fun sendIntent(intent: SearchIntent) {
        viewModelScope.launch { intentFlow.emit(intent) }
    }

    private fun observeIntent() {
        intentFlow.onEach { intent ->
            when (intent) {
                is SearchIntent.TextChanged -> {
                    _state.value = SearchUiState(query = intent.value)
                }
                SearchIntent.LoadNextPage -> search()
                is SearchIntent.ToggleBookmark -> toggleBookmark(intent.item)
                SearchIntent.Search -> {
                    currentPage = 0
                    search()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun toggleBookmark(item: SearchItem) {
        viewModelScope.launch {
            bookmarkRepo.toggle(item)
            val newBookmark = !item.isBookmarked
            _state.update { state ->
                val updated = state.items.map {
                    if (it.id == item.id) it.copy(isBookmarked = newBookmark)
                    else it
                }
                state.copy(items = updated)
            }
        }
    }

    private fun search() {
        viewModelScope.launch {
            // 이미 로딩 중이거나 마지막이면 무시
            val currentState = _state.value
            if (currentState.isLoading || currentState.endReached) return@launch

            _state.update { it.copy(isLoading = true) }

            when (val result = searchRepo.search(currentState.query, currentPage)) {
                is ResultModel.Success -> {
                    // 북마크된 항목 ID 들
                    val bookmarkedIds = bookmarkRepo.getBookmarkedItemIds()

                    // 북마크된 결과인지 확인
                    val itemsWithBookmarkStatus = result.data.map { item ->
                        item.copy(isBookmarked = item.id in bookmarkedIds)
                    }

                    _state.update { state ->
                        val combined =
                            if (currentPage == 1) itemsWithBookmarkStatus else state.items + itemsWithBookmarkStatus
                        state.copy(
                            items = combined,
                            isLoading = false,
                            endReached = result.data.isEmpty()
                        )
                    }
                    currentPage++
                }
                // 빈 검색일 때는 데이터 없음
                is ResultModel.Empty -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            endReached = true
                        )
                    }
                }
                is ResultModel.Error -> {
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}
