package com.example.monomi.core.data.di

import com.example.monomi.core.data.repository.bookmark.BookmarkRepository
import com.example.monomi.core.data.repository.bookmark.FakeBookmarkRepository
import com.example.monomi.core.data.repository.search.FakeSearchRepository
import com.example.monomi.core.data.repository.search.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindsSearchRepository(
        searchRepositoryImpl: FakeSearchRepository
    ): SearchRepository

    @Binds
    @Singleton
    fun bindsBookMarkRepository(
        bookmarkRepositoryImpl: FakeBookmarkRepository
    ): BookmarkRepository
}