package com.example.monomi.core.data.di

import com.example.monomi.core.data.cache.InMemoryDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
  @Provides
  @Singleton
  fun provideInMemoryDataSource(): InMemoryDataSource = InMemoryDataSource
}