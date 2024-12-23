package com.evothings.mhand.presentation.feature.cart.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.auth.interactor.AuthInteractor
import com.evothings.domain.feature.cart.interactor.CartInteractor
import com.evothings.domain.feature.cart.model.Cart
import com.evothings.domain.feature.onboarding.interactor.OnboardingInteractor
import com.evothings.domain.feature.product.interactor.ProductInteractor
import com.evothings.domain.feature.product.model.Product
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.onboarding.model.OnboardingCacheKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val onboardingInteractor: OnboardingInteractor,
    private val productInteractor: ProductInteractor,
    private val cartInteractor: CartInteractor
) : BaseViewModel<CartContract.Event, CartContract.State, CartContract.Effect>() {

    private val _products: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())
    val products = _products.asStateFlow()

    private val _total = MutableStateFlow(0.0)
    val total = _total.asStateFlow()

    private val _discount = MutableStateFlow(0.0)
    val discount = _discount.asStateFlow()

    private val _cashbackPoints = MutableStateFlow(0.0)
    val cashbackPoints = _cashbackPoints.asStateFlow()

    private val _summary = MutableStateFlow(0.0)
    val summary = _summary.asStateFlow()

    val isAuthorized = mutableStateOf(true)

    override fun setInitialState(): CartContract.State = CartContract.State.Loading

    override fun handleEvent(event: CartContract.Event) = when(event) {
        is CartContract.Event.LoadCart -> loadCart()
        is CartContract.Event.Refresh -> refreshData()
        is CartContract.Event.DeleteFromCart -> removeFromCart(event.id)
        is CartContract.Event.CalculateCheckout -> calculateCheckout(event.selection)
        is CartContract.Event.ToggleFavourite -> toggleFavourite(event.id)
        is CartContract.Event.CreateOrder -> createOrder(event.selection)
        is CartContract.Event.FinishOnboarding -> disableOnboarding()
    }

    private fun loadCart() {
        viewModelScope.launch(dispatcher) {

            val isOnboardingActive = onboardingInteractor.isOnboardingActive(OnboardingCacheKey.CART)
            if (isOnboardingActive) {
                setState(CartContract.State.OnboardingActive)
                return@launch
            }

            val isUserAuthorized = authInteractor.checkIsAuthorized()
            isAuthorized.value = isUserAuthorized

            cartInteractor.getAllItems()
                .fold(
                    onSuccess = { cart ->
                        if (isUserAuthorized) {
                            setOrderAmountInfo(cart)
                        }
                        _products.emit(cart.products)
                        setState(
                            if (cart.products.isNotEmpty()) {
                                CartContract.State.Loaded
                            } else {
                                CartContract.State.NoItems
                            }
                        )
                    },
                    onFailure = {
                        setEffect { CartContract.Effect.ShowToast("Ошибка загрузки: $it") }
                        setState(CartContract.State.ServerError)
                    }
                )

        }
    }

    private fun calculateCheckout(selection: List<Int>) {
        viewModelScope.launch(dispatcher) {
            cartInteractor.calculatePrice(selection)
                .onSuccess { cart -> setOrderAmountInfo(cart) }
        }
    }

    private suspend fun setOrderAmountInfo(cart: Cart) {
        _total.emit(cart.total)
        _discount.emit(cart.discount)
        _cashbackPoints.emit(cart.cashbackPoints)
        _summary.emit(cart.finalPrice)
    }

    private fun removeFromCart(id: Int) {
        viewModelScope.launch(dispatcher) {
            cartInteractor.deleteFromCart(id)
                .onSuccess {
                    _products.update {
                        val mutable = it.toMutableList()
                        mutable.removeIf { it.id == id }
                        mutable.toList()
                    }
                    if (_products.value.isEmpty()) {
                        setState(CartContract.State.NoItems)
                    }
                }
        }
    }

    private fun toggleFavourite(id: Int) {
        viewModelScope.launch(dispatcher) {
            productInteractor.toggleFavourite(id)
                .onFailure {
                    setEffect { CartContract.Effect.ShowToast("Ошибка: $it") }
                }
        }
    }

    private fun createOrder(selection: List<Int>) {
        viewModelScope.launch(dispatcher) {
            cartInteractor.createOrder(selection)
                .fold(
                    onSuccess = {
                        setEffect { CartContract.Effect.OpenCheckoutScreen(it) }
                    },
                    onFailure = {
                        setEffect { CartContract.Effect.ShowToast("Ошибка: $it") }
                    }
                )
        }
    }

    private fun refreshData() {
        setState(CartContract.State.Loading)
        loadCart()
    }

    private fun disableOnboarding() {
        viewModelScope.launch(dispatcher) {
            onboardingInteractor.disableOnboarding(OnboardingCacheKey.CART)
            setState(CartContract.State.Loading)
        }
    }

}