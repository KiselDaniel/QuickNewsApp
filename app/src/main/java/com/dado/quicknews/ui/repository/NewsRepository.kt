package com.dado.quicknews.ui.repository

import com.dado.quicknews.data.datasource.NewsDataSourceI
import com.dado.quicknews.data.model.NewsArticleResponse
import com.dado.utils.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsDataSource: NewsDataSourceI
) {
//    suspend fun getNewsHeadline(country: String) : Response<NewsArticleResponse> {
//        return newsDataSource.getNewsHeadline(country)
//    }

    suspend fun getNewsHeadline(country: String) : Flow<ResourceState<NewsArticleResponse>> {
        return flow {
            emit(ResourceState.Loading())

            val response = newsDataSource.getNewsHeadline(country)

            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(ResourceState.Error("Error fetching the data!"))
            }
        }.catch { error ->
            emit(ResourceState.Error(error.localizedMessage ?: "Unknown error!"))
        }
    }

}