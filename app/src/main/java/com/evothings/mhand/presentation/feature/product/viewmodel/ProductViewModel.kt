package com.evothings.mhand.presentation.feature.product.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.product.interactor.ProductInteractor
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productInteractor: ProductInteractor
) : BaseViewModel<ProductContract.Event, ProductContract.State, ProductContract.Effect>() {

    private val _product: MutableStateFlow<Product> = MutableStateFlow(Product())
    val product = _product.asStateFlow()

    override fun setInitialState(): ProductContract.State = ProductContract.State.Loading

    override fun handleEvent(event: ProductContract.Event) =
        when(event) {
            is ProductContract.Event.LoadProduct -> loadProduct(event.id)
            is ProductContract.Event.AddToCart -> addToCart(event.id)
            is ProductContract.Event.ToggleFavourite -> toggleFavourite(event.id)
            is ProductContract.Event.Reload -> {
                setState(ProductContract.State.Loading)
                loadProduct(event.id)
            }
        }

    private fun loadProduct(id: Int) {
        viewModelScope.launch(dispatcher) {
            setState(ProductContract.State.Loading)
            productInteractor.getProductById(id, force = true).fold(
                onSuccess = { product ->
                    _product.emit(product)
                    setState(ProductContract.State.Loaded)
                },
                onFailure = {
                    setState(ProductContract.State.ServerError)
                }
            )
        }
    }

    private fun addToCart(id: Int) {
        viewModelScope.launch(dispatcher) {
            productInteractor.toggleCart(id)
                .onFailure {
                    setEffect {
                        ProductContract.Effect.ShowToast("Не удалось добавить товар в корзину")
                    }
                }
        }
    }

    private fun toggleFavourite(id: Int) {
        viewModelScope.launch(dispatcher) {
            productInteractor.toggleFavourite(id)
                .onFailure {
                    setEffect {
                        ProductContract.Effect.ShowToast("Не удалось добавить товар в избранное")
                    }
                }
        }
    }

}