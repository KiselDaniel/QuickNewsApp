package com.dado.quicknews.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDaoI {
    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<CachedArticle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<CachedArticle>)

    @Query("DELETE FROM articles")
    suspend fun clearArticles()
}
