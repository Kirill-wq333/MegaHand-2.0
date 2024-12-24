package com.evothings.mhand.presentation.feature.news.viewmodel.article

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.news.interactor.NewsInteractor
import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.mhand.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val newsInteractor: NewsInteractor
) : BaseViewModel<ArticleContract.Event, ArticleContract.State, Nothing>() {

    private val _article: MutableStateFlow<NewsArticle?> = MutableStateFlow(null)
    val article = _article.asStateFlow()

    private val _similarArticles: MutableStateFlow<List<NewsArticle>> =
        MutableStateFlow(listOf())
    val similarArticles = _similarArticles.asStateFlow()

    override fun setInitialState(): ArticleContract.State = ArticleContract.State.Loading

    override fun handleEvent(event: ArticleContract.Event) = when(event) {
        is ArticleContract.Event.LoadArticle -> loadNews(event.id)
    }

    private fun loadNews(id: String) {
        viewModelScope.launch(dispatcher) {
            val newsList = newsInteractor.getNews(false, null).getOrDefault(listOf())
            newsInteractor.getArticle(id).fold(
                onSuccess = {
                    val similarArticles = newsList.shuffled().take(4)
                    _similarArticles.emit(similarArticles)
                    _article.emit(it)
                    updateState { ArticleContract.State.Loaded }
                },
                onFailure = {
                    updateState { ArticleContract.State.ServerError }
                }
            )
        }
    }

}