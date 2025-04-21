package com.example.monomi.ui.feature.bookmark

import androidx.lifecycle.ViewModel
import com.example.monomi.core.data.repository.bookmark.BookmarkRepository
import com.example.monomi.core.model.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    bookmarkRepo: BookmarkRepository
) : ViewModel() {
    val bookmarks: Flow<List<SearchItem>> = bookmarkRepo.observeBookmarks()
}