package com.evothings.mhand.presentation.feature.favorites.viewmodel

import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object FavouritesContract {

    sealed interface Event: ViewEvent{
        data object LoadItems : Event
        data object Refresh : Event
        data class SelectCategory(val category: ProductCategory) : Event
        data class AddProductToCart(val id: Int) : Event
        data class RemoveFromFavourite(val id: Int) : Event
        data object ClearList : Event
    }

    sealed interface State: ViewState{
        data object Loading : State
        data object Loaded : State
        data object ListIsEmpty : State
        data object ServerError : State
    }

    sealed interface Effect: ViewEffect{
        data class ShowToast(val message: String) : Effect
    }
}