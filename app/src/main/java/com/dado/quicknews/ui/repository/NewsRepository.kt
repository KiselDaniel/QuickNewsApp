package com.dado.quicknews.ui.repository

import com.dado.quicknews.data.database.CachedArticle
import com.dado.quicknews.data.datasource.NewsDataSourceI
import com.dado.quicknews.data.datasource.NewsLocalDataSourceI
import com.dado.quicknews.data.model.Article
import com.dado.quicknews.data.model.NewsArticleResponse
import com.dado.quicknews.data.model.Source
import com.dado.utils.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsDataSource: NewsDataSourceI,
    private val newsLocalDataSource: NewsLocalDataSourceI
) {
    suspend fun getNewsHeadline(country: String): Flow<ResourceState<NewsArticleResponse>> {
        return flow {
            emit(ResourceState.Loading())

            val response = newsDataSource.getNewsHeadline(country)

            if (response.isSuccessful && response.body() != null) {
                // Cache the articles
                newsLocalDataSource.saveArticlesToDatabase(response.body()!!.articles
                    .map {
                        convertArticleToCachedArticle(it)
                    })
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(ResourceState.Error("Error fetching the data!"))
            }
        }.catch { error ->
            emit(ResourceState.Error(error.localizedMessage ?: "Unknown error!"))
        }
    }

    suspend fun getCachedNewsHeadline(): Flow<ResourceState<List<CachedArticle>>> {
        return flow {
            emit(ResourceState.Loading())
            val cachedArticles = newsLocalDataSource.getArticles()
            emit(ResourceState.Success(cachedArticles))
        }.catch { error ->
            emit(ResourceState.Error(error.localizedMessage ?: "Unknown error!"))
        }
    }

    private fun convertArticleToCachedArticle(article: Article): CachedArticle {
        return CachedArticle(
            author = article.author,
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content,
            source = Source(
                id = article.source?.id,
                name = article.source?.name
            )
        )
    }
}