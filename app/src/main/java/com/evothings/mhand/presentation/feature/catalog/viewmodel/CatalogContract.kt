package com.evothings.mhand.presentation.feature.catalog.viewmodel

import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.catalog.model.SearchHint
import com.evothings.mhand.core.viewmodel.ViewEffect
import com.evothings.mhand.core.viewmodel.ViewEvent
import com.evothings.mhand.core.viewmodel.ViewState

object CatalogContract {

    sealed interface Event : ViewEvent {
        data object LoadCategories : Event
        data class AddProductToCart(val id: Int) : Event
        data class ChangeQuery(val newQuery: String) : Event
        data object Search : Event
        data object PopState : Event
        data object ResetState : Event
        data class SelectCategory(val category: ProductCategory) : Event
        data class SelectSubcategoryInSearch(val subcategory: ProductCategory) : Event
        data class SelectSubcategory(val subcategory: ProductCategory) : Event
        data class RefreshSubcategoryPage(val category: ProductCategory) : Event
        data class ApplyFilters(val filters: Map<String, List<Int>>) : Event
        data class SelectSearchHint(val hint: SearchHint) : Event
        data object RefreshSearchPage : Event
        data class ToggleFavouriteOnProduct(val id: Int) : Event
        data class LoadProductInfoScreen(val id: Int) : Event
        data class UpdatePagingGridScrollPosition(val position: Int) : Event
        data object FinishOnboarding : Event
    }

    sealed interface State : ViewState {
        data object Onboarding : State
        data object Search : State
        data class Subcategories(val subcategories: List<ProductCategory>) : State
        data object QueryInput : State
        data object SearchResultsLoading : State
        data object SearchServerError : State
        data object SearchResults : State

        data object CategoryProductsListLoading : State
        data object CategoryProductsServerError : State
        data class SubcategoryProducts(
            val category: ProductCategory,
            val subcategories: List<ProductCategory>?,
        ) : State

        data class ProductInfo(val id: Int) : State
    }

    sealed interface Effect : ViewEffect {
        data class ShowToastMessage(val message: String) : Effect
    }

}