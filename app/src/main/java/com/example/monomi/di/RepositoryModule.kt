package com.example.monomi.di

import com.example.monomi.data.repository.FakeBookmarkRepository
import com.example.monomi.data.repository.FakeSearchRepository
import com.example.monomi.domain.repository.BookmarkRepository
import com.example.monomi.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchRepo(): SearchRepository = FakeSearchRepository()

    @Singleton
    @Provides
    fun provideBookmarkRepo(): BookmarkRepository = FakeBookmarkRepository()
}