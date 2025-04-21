package com.example.monomi.core.data.di

import com.example.monomi.core.data.repository.bookmark.BookmarkRepository
import com.example.monomi.core.data.repository.bookmark.BookmarkRepositoryImpl
import com.example.monomi.core.data.repository.search.SearchRepository
import com.example.monomi.core.data.repository.search.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    fun bindsBookMarkRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl
    ): BookmarkRepository
}