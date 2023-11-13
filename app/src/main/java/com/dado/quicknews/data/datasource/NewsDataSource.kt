package com.dado.quicknews.data.datasource

import com.dado.quicknews.data.api.NetworkAPIServiceI
import com.dado.quicknews.data.model.NewsArticleResponse
import retrofit2.Response
import javax.inject.Inject

class NewsDataSource @Inject constructor(
    private val networkApiService: NetworkAPIServiceI
): NewsDataSourceI {

    override suspend fun getNewsHeadline(country: String): Response<NewsArticleResponse> {
        return networkApiService.getNewsHeadline(country)
    }
}