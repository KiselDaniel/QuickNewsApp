package com.dado.quicknews.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class CachedArticle(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
    // Consider adding a unique identifier if needed
)

