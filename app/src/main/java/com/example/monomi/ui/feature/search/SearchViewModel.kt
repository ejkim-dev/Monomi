package com.example.monomi.ui.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monomi.core.data.repository.bookmark.BookmarkRepository
import com.example.monomi.core.data.repository.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepo: SearchRepository,
    private val bookmarkRepo: BookmarkRepository
) : ViewModel() {

    private val intentFlow = MutableSharedFlow<SearchIntent>()

    private var currentPage = 1
    private val pageSize = 30

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    private val _effect = Channel<SearchSideEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

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
                    currentPage = 1
                    _state.update { it.copy(query = intent.value, items = emptyList()) }
                    search()
                }
                SearchIntent.LoadNextPage -> search()
                is SearchIntent.ToggleBookmark -> {
                    val itemId = intent.item.id
                    val newBookmark = !intent.item.isBookmarked

                    // 저장소에 북마크 아이템 업데이트
                    bookmarkRepo.toggle(intent.item)

                    // 특정 아이템 북마크 상태 업데이트
                    _state.update { currentState ->
                        val currentItems = currentState.items
                        val updatedItems = currentItems.toMutableList()

                        for (i in updatedItems.indices) {
                            if (updatedItems[i].id == itemId) {

                                updatedItems[i] = updatedItems[i].copy(isBookmarked = newBookmark)
                                break
                            }
                        }

                        currentState.copy(items = updatedItems)
                    }

                    _effect.send(SearchSideEffect.ShowToast("보관함 ${if (intent.item.isBookmarked) "해제" else "저장"}"))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun search() = viewModelScope.launch {
        if (_state.value.isLoading || _state.value.endReached) return@launch
        _state.update { it.copy(isLoading = true) }

        val result = searchRepo.search(_state.value.query, currentPage, pageSize)
        _state.update {
            it.copy(
                items = it.items + result,
                isLoading = false,
                endReached = result.isEmpty()
            )
        }
        currentPage++
    }
}