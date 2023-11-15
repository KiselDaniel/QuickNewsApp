package com.dado.quicknews.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dado.quicknews.data.database.utils.SourceTypeConverter

@Database(entities = [CachedArticle::class], version = 1, exportSchema = false)
@TypeConverters(SourceTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDaoI
}
