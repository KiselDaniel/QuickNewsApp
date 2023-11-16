package com.dado.quicknews

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.dado.quicknews.data.datasource.NewsDataSourceI
import com.dado.quicknews.data.datasource.NewsLocalDataSourceI
import com.dado.quicknews.data.model.NewsArticleResponse
import com.dado.quicknews.ui.repository.NewsRepository
import com.dado.utils.NetworkCheckerI
import com.dado.utils.ResourceState
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class FetchUnitTest {

    @Test
    fun test_fetchNewsHeadlines_offline() = runBlocking {
        // Mock response
        val mockResponse = Response.success(NewsArticleResponse("ok", 10, listOf()))

        // Mock news data source
        val mockDataSource = mock<NewsDataSourceI>()
        whenever(mockDataSource.getNewsHeadline("us")).thenReturn(mockResponse)

        // Mock local data source
        val mockLocalDataSource = mock<NewsLocalDataSourceI>()

        // Mock context
        val mockContext = mock<Context>()

        // Mock connectivity manager
        val mockConnectivityManager = mock<ConnectivityManager>()

        // Mock network capabilities
        val mockNetworkCapabilities = mock<NetworkCapabilities>().apply {
            whenever(hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(true)
        }

        // When getSystemService is called on the mock context with CONNECTIVITY_SERVICE, return the mock connectivity manager
        whenever(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockConnectivityManager)

        // When getNetworkCapabilities is called on the mock connectivity manager, return the mock network capabilities
        whenever(mockConnectivityManager.getNetworkCapabilities(any())).thenReturn(mockNetworkCapabilities)

        val mockNetworkChecker = mock<NetworkCheckerI>()

        // When isInternetConnected is called on the mock CoreUtility, return true
        whenever(mockNetworkChecker.isInternetConnected(any())).thenReturn(true)

        // Create news repository with mock data source
        val newsRepository = NewsRepository(mockContext, mockDataSource, mockLocalDataSource)

        // Call the function to be tested
        val result = newsRepository.getNewsHeadline("us").toList()

        println(result)

        // Assertions
        assertTrue(result[0] is ResourceState.Loading)
        assertTrue(result[1] is ResourceState.Offline)
    }
}