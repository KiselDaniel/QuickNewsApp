package com.dado.quicknews.data.datasource

import com.dado.quicknews.data.database.ArticleDaoI
import com.dado.quicknews.data.database.CachedArticle
import javax.inject.Inject

class NewsLocalDataSource @Inject constructor(
    private val articleDao: ArticleDaoI
) : NewsLocalDataSourceI {
    override suspend fun saveArticlesToDatabase(articles: List<CachedArticle>) {
        articleDao.insertArticles(articles)
    }

    override suspend fun getArticles(): List<CachedArticle> {
        return articleDao.getAllArticles()
    }
}