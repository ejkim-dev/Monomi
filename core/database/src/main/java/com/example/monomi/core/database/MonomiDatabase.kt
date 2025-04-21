package com.example.monomi.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.monomi.core.database.entity.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1)
abstract class MonomiDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}