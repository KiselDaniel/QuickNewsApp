package com.dado.quicknews.data.datasource

import com.dado.quicknews.data.model.NewsArticleResponse
import retrofit2.Response

interface NewsDataSourceI {

    suspend fun getNewsHeadline(country: String) : Response<NewsArticleResponse>

}