package com.evothings.mhand.presentation.feature.favourites.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.auth.interactor.AuthInteractor
import com.evothings.domain.feature.catalog.model.ProductCategory
import com.evothings.domain.feature.favourites.interactor.FavouritesInteractor
import com.evothings.domain.feature.product.interactor.ProductInteractor
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val favouritesInteractor: FavouritesInteractor,
    private val productInteractor: ProductInteractor
) : BaseViewModel<FavouritesContract.Event, FavouritesContract.State, FavouritesContract.Effect>() {

    private val _products = MutableStateFlow<List<Product>>(listOf())
    val products = _products.asStateFlow()

    private val _categories = MutableStateFlow<List<ProductCategory>>(listOf())
    val categories = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ProductCategory())
    val selectedCategory = _selectedCategory.asStateFlow()

    override fun setInitialState(): FavouritesContract.State = FavouritesContract.State.Loading

    override fun handleEvent(event: FavouritesContract.Event) = when(event) {
        is FavouritesContract.Event.LoadItems -> loadFavouriteItems()
        is FavouritesContract.Event.Refresh -> refreshScreen()
        is FavouritesContract.Event.SelectCategory -> loadProductsByCategory(event.category)
        is FavouritesContract.Event.AddProductToCart -> addToCart(event.id)
        is FavouritesContract.Event.RemoveFromFavourite -> removeFromFavourites(event.id)
        is FavouritesContract.Event.ClearList -> flushFavourites()
    }

    private fun loadFavouriteItems() {
        viewModelScope.launch(dispatcher) {
            favouritesInteractor.getFavourites().fold(
                onSuccess = { favourites ->
                    if (favourites.isEmpty()) {
                        setState(FavouritesContract.State.ListIsEmpty)
                    } else {
                        if (authInteractor.checkIsAuthorized()) {
                            extractAndSetCategories(favourites)
                        } else {
                            _products.emit(favourites)
                        }
                        setState(FavouritesContract.State.Loaded)
                    }
                },
                onFailure = {
                    setEffect { FavouritesContract.Effect.ShowToast("Ошибка: ${it.message}") }
                    setState(FavouritesContract.State.ServerError)
                }
            )
        }
    }

    private suspend fun extractAndSetCategories(products: List<Product>) {
        val productIdList = products.map { it.id }
        val categories = favouritesInteractor.getProductCategories(productIdList).getOrDefault(emptyList())
        if (categories.isNotEmpty()) {
            val firstCategory = categories.first()
            _categories.emit(categories)
            _selectedCategory.emit(firstCategory)
            loadProductsByCategory(firstCategory)
        }
    }

    private fun loadProductsByCategory(category: ProductCategory) {
        viewModelScope.launch(dispatcher) {
            _selectedCategory.emit(category)
            favouritesInteractor.getFavourites(category.id).fold(
                onSuccess = { favouritesList ->
                    _products.emit(favouritesList)
                },
                onFailure = {
                    setState(FavouritesContract.State.ServerError)
                    setEffect { FavouritesContract.Effect.ShowToast("Ошибка: ${it.message}") }
                }
            )
        }
    }

    private fun refreshScreen() {
        val currentCategory = _selectedCategory.value
        if (currentCategory.id > 0) {
            loadProductsByCategory(currentCategory)
        } else {
            setState(FavouritesContract.State.Loading)
        }
    }

    private fun addToCart(id: Int) {
        viewModelScope.launch(dispatcher) {
            productInteractor.toggleCart(id)
                .onFailure {
                    setEffect { FavouritesContract.Effect.ShowToast("Ошибка: ${it.message}") }
                }
        }
    }

    private fun removeFromFavourites(id: Int) {
        viewModelScope.launch(dispatcher) {
            favouritesInteractor.removeFromFavourite(id).fold(
                onSuccess = {
                    val listWithoutProduct = _products.value.filter { it.id != id }
                    _products.emit(listWithoutProduct)
                    if (listWithoutProduct.isEmpty()) {
                        setState(FavouritesContract.State.ListIsEmpty)
                    }
                },
                onFailure = {
                    setEffect { FavouritesContract.Effect.ShowToast("Ошибка: $it") }
                }
            )
        }
    }

    private fun flushFavourites() {
        viewModelScope.launch(dispatcher) {
            setState(FavouritesContract.State.Loading)
            favouritesInteractor.clear().fold(
                onSuccess = {
                    _products.emit(emptyList())
                    setState(FavouritesContract.State.ListIsEmpty)
                },
                onFailure = {
                    setEffect { FavouritesContract.Effect.ShowToast("Ошибка: $it") }
                    setState(FavouritesContract.State.Loaded)
                }
            )
        }
    }

}