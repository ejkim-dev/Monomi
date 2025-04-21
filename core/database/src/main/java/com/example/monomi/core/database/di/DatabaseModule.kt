package com.example.monomi.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.monomi.core.database.BookmarkDao
import com.example.monomi.core.database.MonomiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MonomiDatabase {
        return Room.databaseBuilder(
            context,
            MonomiDatabase::class.java,
            "monomi.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(database: MonomiDatabase): BookmarkDao {
        return database.bookmarkDao()
    }
}