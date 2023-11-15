package com.dado.quicknews.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dado.quicknews.data.model.Source

@Entity(tableName = "articles")
data class CachedArticle(
    @PrimaryKey(autoGenerate = true) var articleId: Long = 0,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?,
    var source: Source?
)

data class Source(
    var id: String?,
    var name: String?
)

