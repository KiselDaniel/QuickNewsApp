package com.dado.quicknews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CachedArticle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
