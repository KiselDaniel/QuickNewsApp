package com.dado.quicknews.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dado.quicknews.data.AppConstants.DEFAULT_COUNTRY
import com.dado.quicknews.data.model.NewsArticleResponse
import com.dado.quicknews.ui.repository.NewsRepository
import com.dado.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _news: MutableStateFlow<ResourceState<NewsArticleResponse>> = MutableStateFlow(ResourceState.Loading())
    val news : StateFlow<ResourceState<NewsArticleResponse>> = _news

    init {
        getNewsForCountry(DEFAULT_COUNTRY)
    }

    fun getNewsForCountry(country: String) {
        viewModelScope.launch (dispatcher) {
            newsRepository.getNewsHeadline(country)
                .collect { newsResponse ->
                    _news.value = newsResponse
                }
        }
    }

    companion object {
        const val TAG = "NewsViewModel"
    }
}