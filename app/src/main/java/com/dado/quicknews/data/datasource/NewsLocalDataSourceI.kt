package com.dado.quicknews.data.datasource

import com.dado.quicknews.data.database.CachedArticle

interface NewsLocalDataSourceI {
    suspend fun saveArticlesToDatabase(articles: List<CachedArticle>)
    suspend fun getArticles(): List<CachedArticle>
}