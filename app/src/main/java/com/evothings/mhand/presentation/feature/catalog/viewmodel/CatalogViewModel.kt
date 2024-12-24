package com.evothings.mhand.presentation.feature.catalog.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.catalog.interactor.CatalogInteractor
import com.evothings.domain.feature.catalog.model.FilterValue
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.catalog.model.SearchHint
import com.evothings.domain.feature.catalog.model.SubcategoryHint
import com.evothings.domain.feature.catalog.model.TextHint
import com.evothings.domain.feature.onboarding.interactor.OnboardingInteractor
import com.evothings.domain.feature.product.interactor.ProductInteractor
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.onboarding.model.OnboardingCacheKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogInteractor: CatalogInteractor,
    private val productInteractor: ProductInteractor,
    private val onboardingInteractor: OnboardingInteractor
) : BaseViewModel<CatalogContract.Event, CatalogContract.State, CatalogContract.Effect>() {

    private val _categories: MutableStateFlow<List<ProductCategory>> = MutableStateFlow(emptyList())
    val categories = _categories.asStateFlow()

    private val _selectedCategory: MutableStateFlow<ProductCategory?> = MutableStateFlow(null)
    val selectedSubcategory = _selectedCategory.asStateFlow()

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _searchHints: MutableStateFlow<List<SearchHint>> = MutableStateFlow(listOf())
    val searchHint: StateFlow<List<SearchHint>> = _searchHints.asStateFlow()

    private val _filters: MutableStateFlow<Map<String, List<FilterValue>>> = MutableStateFlow(mapOf())
    val filters = _filters.asStateFlow()

    private val _appliedFilters: MutableStateFlow<Map<String, List<Int>>> = MutableStateFlow(mapOf())
    val appliedFilters = _appliedFilters.asStateFlow()

    private val statesBackstack: MutableStateFlow<List<CatalogContract.State>> =
        MutableStateFlow(listOf(CatalogContract.State.Search))

    private val _catalogState: MutableStateFlow<CatalogContract.State> =
        MutableStateFlow(CatalogContract.State.Search)
    val catalogState = _catalogState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val products = combine(_appliedFilters, _query, _selectedCategory, _catalogState) { filters, query, category, state ->
        val isAppropriateState = (
                state is CatalogContract.State.SearchResults
                        || state is CatalogContract.State.SubcategoryProducts
                )
        if (isAppropriateState) {
            getAndUpdateProductsCount(query, filters, category)
            catalogInteractor.getPagingProducts(query, category, filters, viewModelScope)
        } else flow {}
    }.flatMapLatest { v -> v }

    private val _productsGridScrollPosition = MutableStateFlow(0)
    val productsGridScrollPosition = _productsGridScrollPosition.asStateFlow()

    private val _productsCount = MutableStateFlow(0)
    val productsCount = _productsCount.asStateFlow()

    init {
        watchCatalogState()
        checkIsOnboardingEnabled()
    }

    private fun watchCatalogState() {
        viewModelScope.launch(dispatcher) {
            statesBackstack.collect { backstack ->
                _catalogState.emit(backstack.last())
            }
        }
    }

    private fun checkIsOnboardingEnabled() {
        viewModelScope.launch(dispatcher) {
            val isEnabled = onboardingInteractor.isOnboardingActive(OnboardingCacheKey.CATALOG)
            if (isEnabled) {
                setCatalogState(CatalogContract.State.Onboarding)
            }
        }
    }

    override fun setInitialState(): CatalogContract.State = CatalogContract.State.Search

    override fun handleEvent(event: CatalogContract.Event) = when(event) {
        is CatalogContract.Event.PopState -> popStateBackstack()
        is CatalogContract.Event.ResetState -> resetState()

        is CatalogContract.Event.LoadCategories -> loadCategories()
        is CatalogContract.Event.ChangeQuery -> changeQuery(event.newQuery)
        is CatalogContract.Event.Search -> search()
        is CatalogContract.Event.SelectSearchHint -> applySearchHint(event.hint)
        is CatalogContract.Event.RefreshSearchPage -> refreshSearchPage()
        is CatalogContract.Event.SelectSubcategoryInSearch ->
            loadProductsByCategory(event.subcategory)

        is CatalogContract.Event.SelectCategory -> loadSubcategories(event.category)
        is CatalogContract.Event.SelectSubcategory ->
            setSubcategory(event.subcategory)
        is CatalogContract.Event.RefreshSubcategoryPage -> refreshSubcategoryPage(event.category)
        is CatalogContract.Event.ApplyFilters -> applyFilters(event.filters)
        is CatalogContract.Event.LoadProductInfoScreen ->
            setCatalogState(CatalogContract.State.ProductInfo(event.id))

        is CatalogContract.Event.AddProductToCart -> addProductToCart(event.id)
        is CatalogContract.Event.ToggleFavouriteOnProduct -> toggleFavouriteOnProduct(event.id)

        is CatalogContract.Event.UpdatePagingGridScrollPosition ->
            updateProductsGridScrollPosition(event.position)

        is CatalogContract.Event.FinishOnboarding -> disableOnboarding()
    }

    private fun loadCategories() {
        viewModelScope.launch(dispatcher) {
            catalogInteractor.getAllCategories().fold(
                onSuccess = { categories ->
                    _categories.emit(categories)
                },
                onFailure = {
                    setEffect {
                        CatalogContract.Effect.ShowToastMessage(
                            "Ошибка загрузки категорий: $it"
                        )
                    }
                    setState(CatalogContract.State.SearchServerError)
                }
            )
        }
    }

    private var loadHintsJob: Job? = null

    private fun changeQuery(newQuery: String) {
        _query.update { newQuery }
        loadHintsJob = viewModelScope.launch(dispatcher) {
            catalogInteractor.getSearchHints(newQuery).fold(
                onSuccess = { hints ->
                    _searchHints.emit(hints)
                    if (catalogState.value !is CatalogContract.State.SearchResultsLoading) {
                        setCatalogState(CatalogContract.State.QueryInput)
                    }
                },
                onFailure = {
                    setEffect {
                        CatalogContract.Effect.ShowToastMessage(
                            "Не удалось загрузить поисковые подсказки: ${it.message}"
                        )
                    }
                    setCatalogState(CatalogContract.State.SearchServerError)
                }
            )
        }
    }

    private fun getAndUpdateProductsCount(
        query: String,
        filter: Map<String, List<Int>>,
        category: ProductCategory?
    ) {
        viewModelScope.launch(dispatcher) {
            val count = catalogInteractor.getProductsCount(query, category, filter)
            _productsCount.emit(count)
        }
    }

    private fun search() {
        viewModelScope.launch(dispatcher) {
            setCatalogState(CatalogContract.State.SearchResultsLoading)
            loadHintsJob?.cancel()
            loadAndSetFilters()
            setCatalogState(CatalogContract.State.SearchResults)
        }
    }

    private fun loadSubcategories(category: ProductCategory) {
        val subcategories = category.children
        if (subcategories.isNullOrEmpty()) {
            loadProductsByCategory(category)
        } else {
            setCatalogState(
                CatalogContract.State.Subcategories(subcategories)
            )
        }
    }

    private fun applySearchHint(hint: SearchHint) {
        when(hint) {
            is TextHint -> {
                _query.update { hint.text }
                search()
            }
            is SubcategoryHint -> {
                _query.update { "" }
                loadProductsByCategory(hint.categoryObject)
            }
        }
    }

    private fun loadProductsByCategory(category: ProductCategory) {
        viewModelScope.launch(dispatcher) {
            setCatalogState(CatalogContract.State.CategoryProductsListLoading)
            loadAndSetFilters()
            _selectedCategory.emit(category)
            setCatalogState(
                CatalogContract.State.SubcategoryProducts(
                    category = category,
                    subcategories = category.children
                )
            )
        }
    }

    private fun applyFilters(map: Map<String, List<Int>>) {
        _appliedFilters.update { map }
    }

    private fun setSubcategory(subcategory: ProductCategory) {
        _selectedCategory.update { subcategory }
    }

    private suspend fun loadAndSetFilters() {
        val filters = catalogInteractor.getFilters()
        _filters.emit(filters)
    }

    private fun updateProductsGridScrollPosition(newPosition: Int) {
        _productsGridScrollPosition.update { newPosition }
    }

    private fun addProductToCart(id: Int) {
        viewModelScope.launch(dispatcher) {
            productInteractor.toggleCart(id)
                .onFailure {
                    setEffect {
                        CatalogContract.Effect.ShowToastMessage(
                            "Не удалось добавить товар в корзину"
                        )
                    }
                }
        }
    }

    private fun toggleFavouriteOnProduct(id: Int) {
        viewModelScope.launch(dispatcher) {
            productInteractor.toggleFavourite(id)
                .onFailure {
                    setEffect {
                        CatalogContract.Effect.ShowToastMessage(
                            "Не удалось добавить товар в избранное"
                        )
                    }
                }
        }
    }

    private fun refreshSearchPage() {
        val indexToTake = statesBackstack.value.lastIndex - 1
        val previousState = statesBackstack.value[indexToTake]
        setCatalogState(CatalogContract.State.SearchResultsLoading)

        when(previousState) {
            is CatalogContract.State.Search -> loadCategories()
            is CatalogContract.State.SearchResultsLoading -> search()
            else -> setCatalogState(CatalogContract.State.Search)
        }
    }

    private fun refreshSubcategoryPage(category: ProductCategory) {
        setCatalogState(CatalogContract.State.CategoryProductsListLoading)
        loadProductsByCategory(category)
    }

    private fun disableOnboarding() {
        viewModelScope.launch(dispatcher) {
            onboardingInteractor.disableOnboarding(OnboardingCacheKey.CATALOG)
            setCatalogState(CatalogContract.State.Search)
        }
    }

    private fun setCatalogState(newState: CatalogContract.State) {
        statesBackstack.update { backstack ->
            val mutable = backstack.toMutableList()
            mutable.add(newState)
            mutable
        }
    }

    private fun resetState() {
        _query.update { "" }
        _selectedCategory.update { null }
        _appliedFilters.update { emptyMap() }
        statesBackstack.update {
            listOf(CatalogContract.State.Search)
        }
    }

    private fun popStateBackstack() {
        statesBackstack.update { backstack ->
            val mutable = backstack.toMutableList()
            mutable.dropLast(1)
        }
    }

}