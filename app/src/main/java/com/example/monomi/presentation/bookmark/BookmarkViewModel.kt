package com.example.monomi.presentation.bookmark

import androidx.lifecycle.ViewModel
import com.example.monomi.domain.model.SearchItem
import com.example.monomi.domain.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    bookmarkRepo: BookmarkRepository
) : ViewModel() {
    val bookmarks: Flow<List<SearchItem>> = bookmarkRepo.observeBookmarks()
}