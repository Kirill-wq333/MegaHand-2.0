package com.evothings.mhand.presentation.feature.news.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.news.interactor.NewsInteractor
import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.domain.feature.news.model.NewsCategory
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.news.ui.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor
) : BaseViewModel<NewsContract.Event, NewsContract.State, Nothing>() {

    private val latestArticle = MutableStateFlow(NewsArticle())
    private val categories = MutableStateFlow<List<NewsCategory>>(listOf())

    private val news: MutableStateFlow<List<NewsArticle>> = MutableStateFlow(listOf())

    private val selectedCategoryIndex = MutableStateFlow(0)

    internal val uiState =
        combine(latestArticle, news, selectedCategoryIndex, categories) { mainNewsItem, news, chosenIndex, categories ->
            NewsUiState(
                primaryArticle = mainNewsItem,
                categories = categories,
                selectedCategoryIndex = chosenIndex,
                news = news,
            )
        }

    override fun setInitialState(): NewsContract.State = NewsContract.State.Loading

    override fun handleEvent(event: NewsContract.Event) = when(event) {
        is NewsContract.Event.LoadData -> loadNews(forceOnline = false)
        is NewsContract.Event.Reload -> loadNews(forceOnline = true)
        is NewsContract.Event.SelectCategory -> pickCategory(event.index)
    }

    private fun loadNews(forceOnline: Boolean) {
        viewModelScope.launch(dispatcher) {
            setState(NewsContract.State.Loading)
            newsInteractor.getNewsCategories().fold(
                onSuccess = { tagList ->
                    categories.emit(tagList)
                    val firstCategory = tagList.first()
                    loadNewsByCategory(forceOnline, firstCategory)
                    loadLatestArticle()
                    setState(NewsContract.State.Loaded)
                },
                onFailure = {
                    setState(NewsContract.State.ServerError)
                }
            )
        }
    }

    private fun pickCategory(index: Int) {
        viewModelScope.launch(dispatcher) {
            selectedCategoryIndex.emit(index)
            val selectedCategory = categories.value[index]
            loadNewsByCategory(false, selectedCategory)
        }
    }

    private suspend fun loadNewsByCategory(force: Boolean, category: NewsCategory) {
        newsInteractor.getNews(force, category.id)
            .onSuccess { newsList -> news.emit(newsList) }
    }

    private suspend fun loadLatestArticle() {
        newsInteractor.getNews(false, null)
            .onSuccess { latestArticle.emit(it.first()) }
    }

}