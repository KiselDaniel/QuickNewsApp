package com.dado.quicknews.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dado.quicknews.data.AppConstants.DEFAULT_COUNTRY
import com.dado.quicknews.ui.components.Loader
import com.dado.quicknews.ui.components.NewsArticlesPageList
import com.dado.quicknews.ui.viewmodel.NewsViewModel
import com.dado.utils.ResourceState

const val TAG = "HomeScreen"

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {

    val newsResponse = newsViewModel.news.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            newsViewModel.getNewsForCountry(DEFAULT_COUNTRY)
        }
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        100
    }

    Box(
        modifier = Modifier.pullRefresh(pullRefreshState)
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
            pageSize = PageSize.Fill,
            pageSpacing = 8.dp
        ) { page: Int ->

            when (val responseState = newsResponse.value) {
                is ResourceState.Loading -> {
                    Log.d(TAG, "Inside_Loading state")
                    Loader()
                }

                is ResourceState.Success -> {
                    val response = responseState.data
                    Log.d(TAG, "Inside_Success state ${response.status} = ${response.totalResults}")

                    if (response.articles.isNotEmpty()) {
                        NewsArticlesPageList(page, article = response.articles[page])
                    }
                }

                is ResourceState.Error -> {
                    val error = responseState.error
                    Log.d(TAG, "Inside_Error state with message: $error")
                }

                is ResourceState.Offline -> {
                    val error = responseState.error
                    Log.d(TAG, "Inside_Offline state with message: $error")
                }
            }
        }

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = newsResponse.value is ResourceState.Loading,
            state = pullRefreshState
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}