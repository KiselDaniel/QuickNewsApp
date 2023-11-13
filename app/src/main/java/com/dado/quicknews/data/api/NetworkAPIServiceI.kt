package com.dado.quicknews.data.api

import com.dado.quicknews.data.AppConstants.DEFAULT_API
import com.dado.quicknews.data.model.NewsArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkAPIServiceI {

    @GET("v2/top-headlines")
    suspend fun getNewsHeadline(
        @Query("Country") country: String,
        @Query("apiKey") apiKey: String = DEFAULT_API
    ) : Response<NewsArticleResponse>
}