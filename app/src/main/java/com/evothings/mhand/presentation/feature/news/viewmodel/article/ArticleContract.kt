package com.evothings.mhand.presentation.feature.news.viewmodel.article

import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object ArticleContract {

    sealed interface Event : ViewEvent {
        data class LoadArticle(val id: String) : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
        data object ServerError : State
    }

}