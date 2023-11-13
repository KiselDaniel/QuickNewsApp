package com.dado.quicknews

import com.dado.quicknews.data.datasource.NewsDataSourceI
import com.dado.quicknews.data.model.NewsArticleResponse
import com.dado.quicknews.ui.repository.NewsRepository
import com.dado.utils.ResourceState
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class FetchUnitTest {

    @Test
    fun test_fetchNewsHeadlines_success() = runBlocking {
        // Mock response
        val mockResponse = Response.success(NewsArticleResponse("ok", 10, listOf()))

        // Mock news data source
        val mockDataSource = mock<NewsDataSourceI>()
        whenever(mockDataSource.getNewsHeadline("us")).thenReturn(mockResponse)

        // Create news repository with mock data source
        val newsRepository = NewsRepository(mockDataSource)

        // Call the function to be tested
        val result = newsRepository.getNewsHeadline("us").toList()

        // Assertions
        assertTrue(result[0] is ResourceState.Loading)
        assertTrue(result[1] is ResourceState.Success)
        assertEquals(ResourceState.Success(mockResponse.body()!!), result[1])
    }
}