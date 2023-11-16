package com.dado.quicknews.ui.repository

import CoreUtility
import android.content.Context
import com.dado.quicknews.data.database.CachedArticle
import com.dado.quicknews.data.datasource.NewsDataSourceI
import com.dado.quicknews.data.datasource.NewsLocalDataSourceI
import com.dado.quicknews.data.model.Article
import com.dado.quicknews.data.model.NewsArticleResponse
import com.dado.quicknews.data.model.Source
import com.dado.utils.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val context: Context,
    private val newsDataSource: NewsDataSourceI,
    private val newsLocalDataSource: NewsLocalDataSourceI
) {

    /**
     * Fetches the latest news headlines for a given country.
     *
     * - This method first checks if there is an internet connection.
     * - If there is, it fetches the latest news headlines from the remote data source and caches them in the local database.
     * - If the fetch is successful, it emits a [ResourceState.Success] with the fetched news articles.
     * - If the fetch fails, it emits a [ResourceState.Error] with an error message.
     *
     * - If there is no internet connection, it retrieves the cached news headlines from the local database and emits a [ResourceState.Offline] with a message indicating that there is no internet connection.
     *
     * @param country The country to fetch the news headlines for.
     * @return A [Flow] that emits the current state of the resource.
     */
    suspend fun getNewsHeadline(country: String): Flow<ResourceState<NewsArticleResponse>> {
        return flow {
            emit(ResourceState.Loading())

            if (CoreUtility.isInternetConnected(context)) {
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
            }  else {
                // Retrieve the cached news from the database if available else notify about offline state
                val cachedNewsHeadline = getCachedNewsHeadline().firstOrNull()
                if (cachedNewsHeadline is ResourceState.Success) {
                    val newsArticleResponse = NewsArticleResponse(
                        status = STATUS_OK,
                        totalResults = cachedNewsHeadline.data.size,
                        articles = cachedNewsHeadline.data.map { convertCachedArticleToArticle(it) }
                    )
                    emit(ResourceState.Success(newsArticleResponse))
                } else {
                    emit(ResourceState.Offline("No internet connection!"))
                }
            }
        }.catch { error ->
            emit(ResourceState.Error(error.localizedMessage ?: "Unknown error!"))
        }
    }

    /**
     * Get cached articles from the database when there is no internet connection
     */
    private suspend fun getCachedNewsHeadline(): Flow<ResourceState<List<CachedArticle>>> {
        return flow {
            val cachedArticles = newsLocalDataSource.getArticles()
            if (cachedArticles.isEmpty()) {
                emit(ResourceState.Error("No cached articles found!"))
            }
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

    private fun convertCachedArticleToArticle(cachedArticle: CachedArticle): Article {
        return Article(
            author = cachedArticle.author,
            title = cachedArticle.title,
            description = cachedArticle.description,
            url = cachedArticle.url,
            urlToImage = cachedArticle.urlToImage,
            publishedAt = cachedArticle.publishedAt,
            content = cachedArticle.content,
            source = Source(
                id = cachedArticle.source?.id,
                name = cachedArticle.source?.name
            )
        )
    }

    companion object {
        private const val TAG = "NewsRepository"
        private const val STATUS_OK = "ok"
    }
}
