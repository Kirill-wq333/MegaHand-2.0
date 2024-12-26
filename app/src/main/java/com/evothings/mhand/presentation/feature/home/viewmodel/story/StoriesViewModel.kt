package com.evothings.mhand.presentation.feature.home.viewmodel.story

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.home.interactor.HomeInteractor
import com.evothings.domain.feature.home.model.Story
import com.evothings.mhand.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor(
    private val homeInteractor: HomeInteractor
) : BaseViewModel<StoriesContract.Event, StoriesContract.State, Nothing>() {

    private val _storiesList: MutableStateFlow<List<Story>> = MutableStateFlow(listOf())
    val storiesList = _storiesList.asStateFlow()

    override fun setInitialState(): StoriesContract.State = StoriesContract.State.Loading

    override fun handleEvent(event: StoriesContract.Event) = when(event) {
        is StoriesContract.Event.LoadStories -> loadStories()
    }

    private fun loadStories() {
        viewModelScope.launch(dispatcher) {
            homeInteractor.getStories().fold(
                onSuccess = {
                    _storiesList.emit(it)
                    updateState { StoriesContract.State.Loaded }
                },
                onFailure = {
                    updateState { StoriesContract.State.NetworkFailure }
                }
            )
        }
    }

}