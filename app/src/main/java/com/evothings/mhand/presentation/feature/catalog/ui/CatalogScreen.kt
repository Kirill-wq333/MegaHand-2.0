package com.evothings.mhand.presentation.feature.catalog.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.evothings.domain.feature.catalog.model.FilterValue
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.catalog.model.SearchHint
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.presentation.feature.catalog.ui.components.categories.CategoriesGrid
import com.evothings.mhand.presentation.feature.catalog.ui.components.AllClothesScreen
import com.evothings.mhand.presentation.feature.catalog.ui.components.SearchBar
import com.evothings.mhand.presentation.feature.catalog.ui.components.SearchResultContent
import com.evothings.mhand.presentation.feature.catalog.ui.components.SearchScreen
import com.evothings.mhand.presentation.feature.catalog.ui.components.SubCategory
import com.evothings.mhand.presentation.feature.catalog.viewmodel.CatalogContract
import com.evothings.mhand.presentation.feature.catalog.viewmodel.CatalogViewModel
import com.evothings.mhand.presentation.feature.onboarding.ui.screen.CatalogOnboarding
import com.evothings.mhand.presentation.feature.product.ui.ProductInfoScreen
import com.evothings.mhand.presentation.feature.product.viewmodel.ProductViewModel
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
import com.evothings.mhand.presentation.feature.shared.pullToRefresh.PullRefreshLayout
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen

data class CatalogUiState(
    val query: String,
    val products: LazyPagingItems<Product>,
    val productsTotal: Int,
    val gridScrollPosition: Int,
    val categories: List<ProductCategory>,
    val selectedSubcategory: ProductCategory?,
    val searchHints: List<SearchHint>,
    val filters: Map<String, List<FilterValue>>,
    val appliedFilters: Map<String, List<Int>>
)

interface CatalogCallback : ProductCardCallback {
    fun onChangeQuery(newQuery: String)
    fun search()
    fun popState()
    fun resetState()
    fun selectCategory(category: ProductCategory)
    fun selectSubcategorySearch(subcategory: ProductCategory)
    fun applyFilters(filters: Map<String, List<Int>>)
    fun selectSubcategory(newSubcategory: ProductCategory)
    fun onSelectSearchHint(hint: SearchHint)
    fun refreshSearch()
    fun refreshSubcategoryProducts(category: ProductCategory)
    fun finishOnboarding()
    fun updatePagingGridScrollPosition(position: Int)
}

@Composable
fun CatalogScreen(
    vm: CatalogViewModel,
){
    val focusManager = LocalFocusManager.current

    val state by vm.catalogState.collectAsStateWithLifecycle()

    val products = vm.products.collectAsLazyPagingItems()
    val productsCount by vm.productsCount.collectAsState()
    val pagingGridScrollPosition by vm.productsGridScrollPosition.collectAsState()

    val categories by vm.categories.collectAsState()
    val selectedSubcategory by vm.selectedSubcategory.collectAsState()
    val query by vm.query.collectAsState()
    val searchHints by vm.searchHint.collectAsState()
    val filters by vm.filters.collectAsState()
    val appliedFilters by vm.appliedFilters.collectAsState()

    LaunchedEffect(Unit) {
        if (state is CatalogContract.State.Search) {
            vm.handleEvent(CatalogContract.Event.LoadCategories)
        }
    }

    val callback = object : CatalogCallback {

        override fun addToCart(id: Int) =
            vm.handleEvent(CatalogContract.Event.AddProductToCart(id))

        override fun onChangeQuery(newQuery: String) =
            vm.handleEvent(CatalogContract.Event.ChangeQuery(newQuery))

        override fun search() {
            focusManager.clearFocus()
            products.refresh()
            vm.handleEvent(CatalogContract.Event.Search)
        }

        override fun popState() {
            focusManager.clearFocus()
            vm.handleEvent(CatalogContract.Event.PopState)
        }

        override fun resetState() {
            focusManager.clearFocus()
            vm.handleEvent(CatalogContract.Event.ResetState)
        }

        override fun updatePagingGridScrollPosition(position: Int) {
            vm.handleEvent(CatalogContract.Event.UpdatePagingGridScrollPosition(position))
        }

        override fun selectCategory(category: ProductCategory) =
            vm.handleEvent(CatalogContract.Event.SelectCategory(category))

        override fun selectSubcategorySearch(subcategory: ProductCategory) =
            vm.handleEvent(CatalogContract.Event.SelectSubcategoryInSearch(subcategory))

        override fun refreshSubcategoryProducts(category: ProductCategory) =
            vm.handleEvent(CatalogContract.Event.RefreshSubcategoryPage(category))

        override fun selectSubcategory(newSubcategory: ProductCategory) =
            vm.handleEvent(CatalogContract.Event.SelectSubcategory(newSubcategory))

        override fun applyFilters(filters: Map<String, List<Int>>) {
            products.refresh()
            vm.handleEvent(CatalogContract.Event.ApplyFilters(filters))
        }

        override fun onSelectSearchHint(hint: SearchHint) {
            focusManager.clearFocus()
            vm.handleEvent(CatalogContract.Event.SelectSearchHint(hint))
        }

        override fun refreshSearch() =
            vm.handleEvent(CatalogContract.Event.RefreshSearchPage)

        override fun openProductDetailScreen(id: Int) =
            vm.handleEvent(CatalogContract.Event.LoadProductInfoScreen(id))

        override fun toggleFavourite(id: Int) =
            vm.handleEvent(CatalogContract.Event.ToggleFavouriteOnProduct(id))

        override fun finishOnboarding() =
            vm.handleEvent(CatalogContract.Event.FinishOnboarding)

    }

    if (state is CatalogContract.State.Onboarding) {
        CatalogOnboarding(
            onFinish = callback::finishOnboarding
        )
        return
    }

    Content(
        state = state,
        uiState = CatalogUiState(
            query = query,
            searchHints = searchHints,
            products = products,
            gridScrollPosition = pagingGridScrollPosition,
            filters = filters,
            categories = categories,
            productsTotal = productsCount,
            selectedSubcategory = selectedSubcategory,
            appliedFilters = appliedFilters
        ),
        callback = callback
    )

}

@Composable
private fun Content(
    state: CatalogContract.State,
    uiState: CatalogUiState,
    callback: CatalogCallback
){

    BackHandler(
        enabled = true,
        onBack = {
            if (state is CatalogContract.State.ProductInfo) {
                callback.popState()
            } else {
                callback.resetState()
            }
        }
    )

    when(state) {
        is CatalogContract.State.CategoryProductsListLoading ->
            LoadingScreen()
        is CatalogContract.State.CategoryProductsServerError ->
            ServerErrorScreen(
                onRefresh = callback::refreshSearch
            )
        is CatalogContract.State.SubcategoryProducts -> {
            AllClothesScreen(
                state = state,
                uiState = uiState,
                callback = callback
            )
        }
        is CatalogContract.State.ProductInfo -> {
            ProductInfoScreen(
                id = state.id,
                vm = hiltViewModel<ProductViewModel>(),
                onBack = callback::popState
            )
        }
        else -> {
            Search(
                state = state,
                uiState = uiState,
                callback = callback,
            )
        }
    }

}

@Composable
fun Search(
    state: CatalogContract.State,
    uiState: CatalogUiState,
    callback: CatalogCallback
){
    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier.padding(12.dp),
                query = uiState.query,
                enableBackButton = (state !is CatalogContract.State.Search),
                onChangeQuery = callback::onChangeQuery,
                onSearch = callback::search,
                onBack = callback::resetState
            )
        }
    ) { scaffoldPadding ->
        AnimatedContent(
            targetState = state
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(horizontal = 12.dp),
            ) {
                when (it) {
                    is CatalogContract.State.Search -> {
                        CategoriesGrid(
                            categories = uiState.categories,
                            onClickCategory = callback::selectCategory
                        )
                    }
                    is CatalogContract.State.Subcategories -> {
                        SubCategory(
                            subcategories = it.subcategories,
                            onClick = callback::selectSubcategorySearch
                        )
                    }
                    is CatalogContract.State.QueryInput -> {
                        SearchScreen(
                            hints = uiState.searchHints,
                            onClick = callback::onSelectSearchHint
                        )
                    }
                    is CatalogContract.State.SearchResultsLoading -> {
                        LoadingScreen()
                    }
                    is CatalogContract.State.SearchServerError -> {
                        ServerErrorScreen(
                            onRefresh = callback::refreshSearch
                        )
                    }
                    is CatalogContract.State.SearchResults -> {
                        PullRefreshLayout(onRefresh = callback::refreshSearch) {
                            SearchResultContent(
                                uiState = uiState,
                                callback = callback
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}