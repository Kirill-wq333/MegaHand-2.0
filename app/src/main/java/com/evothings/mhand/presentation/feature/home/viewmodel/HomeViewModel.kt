package com.evothings.mhand.presentation.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.coupon.interactor.CouponInteractor
import com.evothings.domain.feature.home.interactor.HomeInteractor
import com.evothings.domain.feature.onboarding.interactor.OnboardingInteractor
import com.evothings.domain.feature.product.interactor.ProductInteractor
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.home.ui.HomeUiState
import com.evothings.mhand.presentation.feature.onboarding.model.OnboardingCacheKey
import com.evothings.mhand.presentation.feature.splash.viewmodel.checkUpdate.CheckUpdateService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeInteractor: HomeInteractor,
    private val onboardingInteractor: OnboardingInteractor,
    private val couponInteractor: CouponInteractor,
    private val productInteractor: ProductInteractor
) : BaseViewModel<HomeContract.Event, HomeContract.State, Nothing>() {

    private val _city = MutableStateFlow("")
    val city = _city.asStateFlow()

    private val _uiState = MutableStateFlow(HomeUiState())
    internal val uiState = _uiState.asStateFlow()

    override fun setInitialState(): HomeContract.State = HomeContract.State.Loading

    override fun handleEvent(event: HomeContract.Event) = when(event) {
        is HomeContract.Event.LoadData -> loadData()
        is HomeContract.Event.FinishOnboarding -> finishOnboarding()
        is HomeContract.Event.SubmitUserSurvey -> submitUserSurvey(event.chosen)
        is HomeContract.Event.Refresh -> refresh()
        is HomeContract.Event.RefreshProductsList -> refreshProductsList()
        is HomeContract.Event.EnableNoInternetConnectionState ->
            setState(HomeContract.State.NoInternetConnection)

        is HomeContract.Event.AddProductToCart -> addProductToCart(event.id)
        is HomeContract.Event.ToggleFavouriteOnProduct -> addProductToFavourites(event.id)

        is HomeContract.Event.CheckIsUpdateAvailable -> checkUpdateAvailable(event.updateService)
    }

    private fun loadData() {
        viewModelScope.launch(dispatcher) {

            val isOnboardingActive = onboardingInteractor.isOnboardingActive(OnboardingCacheKey.HOME)
            if (isOnboardingActive) {
                setState(HomeContract.State.OnboardingActive)
                return@launch
            }

            val isCouponAvailable =
                couponInteractor.isCouponAvailableForUser().getOrDefault(false)

            homeInteractor.buildHomeState().fold(
                onSuccess = { state ->
                    val showCard = (state.profile != null && state.card != null)
                    _uiState.update {
                        it.copy(
                            stories = state.stories,
                            brands = state.brands.toPersistentList(),
                            newProducts = state.newProducts,
                            cashback = state.profile?.cashback ?: -1,
                            showCard = showCard,
                            cardQRLink = state.card?.barcodeUrl.orEmpty(),
                            showCouponBanner = isCouponAvailable,
                            couponAmount = couponInteractor.getCouponAmount()
                        )
                    }
                    updateState { HomeContract.State.Loaded }
                },
                onFailure = {
                    Log.d("HomeViewModel", "loadData: ${it.message}")
                    updateState { HomeContract.State.NetworkError }
                }
            )

        }
    }

    private fun addProductToCart(id: Int) {
        viewModelScope.launch(dispatcher) {
            productInteractor.toggleCart(id)
        }
    }

    private fun addProductToFavourites(id: Int) {
        viewModelScope.launch(dispatcher) {
            productInteractor.toggleFavourite(id)
        }
    }

    private fun checkUpdateAvailable(updateService: CheckUpdateService) {
        updateService.checkIsUpdateAvailable { available ->
            _uiState.update { it.copy(isUpdateAvailable = available) }
        }
    }

    private fun refreshProductsList() {
        viewModelScope.launch(dispatcher) {
            homeInteractor.getNewProducts()
                .onSuccess { products ->
                    _uiState.update { it.copy(newProducts = products) }
                }
        }
    }

    private fun refresh() = updateState { HomeContract.State.Loading }

    private fun finishOnboarding() {
        viewModelScope.launch(dispatcher) {
            onboardingInteractor.disableOnboarding(OnboardingCacheKey.HOME)
            setState(HomeContract.State.Loading)
        }
    }

    private fun submitUserSurvey(chosen: Int) {
        viewModelScope.launch(dispatcher) {
            homeInteractor.submitSurvey(chosen)
        }
    }

}