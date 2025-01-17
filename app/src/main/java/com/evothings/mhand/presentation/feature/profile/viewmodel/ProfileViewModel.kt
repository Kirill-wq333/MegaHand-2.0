package com.evothings.mhand.presentation.feature.profile.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.evothings.domain.feature.address.model.Address
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.domain.feature.auth.interactor.AuthInteractor
import com.evothings.domain.feature.notification.interactor.NotificationInteractor
import com.evothings.domain.feature.onboarding.interactor.OnboardingInteractor
import com.evothings.domain.feature.profile.interactor.ProfileInteractor
import com.evothings.domain.feature.profile.model.Order
import com.evothings.domain.feature.splash.SplashInteractor
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.feature.profile.model.ReferalInfo
import com.evothings.domain.util.DateFormat
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.onboarding.model.OnboardingCacheKey
import com.evothings.mhand.presentation.feature.snackbar.host.SnackbarItemHost
import com.evothings.mhand.presentation.feature.snackbar.model.SnackbarItem
import com.evothings.mhand.presentation.utils.sdkutil.ageFromDate
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.my.tracker.MyTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val profileInteractor: ProfileInteractor,
    private val splashInteractor: SplashInteractor,
    private val onboardingInteractor: OnboardingInteractor,
    private val appSettingsInteractor: AppSettingsInteractor,
    private val notificationInteractor: NotificationInteractor,
    private val snackbarItemHost: SnackbarItemHost
) : BaseViewModel<ProfileContract.Event, ProfileContract.State, ProfileContract.Effect>() {

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(Profile())
    val profile = _profile.asStateFlow()

    private val _addresses: MutableStateFlow<List<Address>> = MutableStateFlow(listOf())
    val addresses = _addresses.asStateFlow()

    private val _referralInfo: MutableStateFlow<ReferalInfo> = MutableStateFlow(ReferalInfo())
    val referralInfo = _referralInfo.asStateFlow()

    private val initialOrders: ArrayList<Order> = arrayListOf()
    private val _orders: MutableStateFlow<List<Order>> = MutableStateFlow(listOf())
    val orders = _orders.asStateFlow()

    private val _ordersQuery: MutableStateFlow<String> = MutableStateFlow("")
    val ordersQuery = _ordersQuery.asStateFlow()

    private val _ordersDateQuery: MutableStateFlow<String> = MutableStateFlow("")
    val ordersDateQuery = _ordersDateQuery.asStateFlow()

    private val mostRecentState: MutableStateFlow<ProfileContract.State> =
        MutableStateFlow(ProfileContract.State.UserData)

    override fun setInitialState(): ProfileContract.State = ProfileContract.State.Loading

    override fun handleEvent(event: ProfileContract.Event) = when(event) {
        is ProfileContract.Event.LoadData -> loadData(false)
        is ProfileContract.Event.Refresh -> loadData(true)

        is ProfileContract.Event.SearchOrders -> searchOrders(event.query)
        is ProfileContract.Event.SearchByDate -> filterOrdersByDate(event.dateQuery)
        is ProfileContract.Event.HandleOrderPaymentStatus -> handleOrderPaymentSuccess(event.status)

        is ProfileContract.Event.SaveProfileChanges -> saveProfileChanges(event.profile)
        is ProfileContract.Event.ConfirmPhone ->
            setEffect { ProfileContract.Effect.NavigateToCode(event.newPhone) }

        is ProfileContract.Event.DeleteAccount -> deleteAccount()
        is ProfileContract.Event.Logout -> logout()

        is ProfileContract.Event.ToggleContentState -> toggleContentState()
        is ProfileContract.Event.FinishOnboarding -> finishOnboarding()
    }

    private fun toggleContentState() {
        val newState = if (state.value is ProfileContract.State.UserData) {
            if (_orders.value.isNotEmpty()) {
                ProfileContract.State.OrdersHistory
            } else {
                ProfileContract.State.EmptyOrdersHistory
            }
        } else {
            ProfileContract.State.UserData
        }
        mostRecentState.update { newState }
        setState(newState)
    }

    private fun saveProfileChanges(profile: Profile) {
        viewModelScope.launch(dispatcher) {

            profileInteractor.editProfile(profile).fold(
                onSuccess = { errorMessage ->
                    if (errorMessage != null) {
                        setEffect { ProfileContract.Effect.ShowToast("Ошибка: $errorMessage") }
                        return@fold
                    }
                    appSettingsInteractor.setCity(profile.city)
                    updateValuesInTracker(profile)
                    subscribeToCityTopic(profile.city)
                    setEffect { ProfileContract.Effect.UpdateWidget }
                    showProfileUpdateSuccessSnackbar()
                },
                onFailure = {}
            )

            loadData(true)
        }
    }

    private fun showProfileUpdateSuccessSnackbar() {
        snackbarItemHost.setSnackbar(
            SnackbarItem(
                title = "Успешно!",
                subtitle = "Изменения профиля сохранены"
            )
        )
    }

    private fun subscribeToCityTopic(city: String) {
        Firebase.messaging.token
            .addOnSuccessListener {  token ->
                viewModelScope.launch(dispatcher) {
                    notificationInteractor.subscribeToTopic(city, token)
                }
            }
    }

    private fun searchOrders(query: String) {
        viewModelScope.launch(dispatcher) {
            _ordersQuery.emit(query)
//            val foundOrders = if (query.isNotEmpty()) {
//                initialOrders.filter { order ->
//                    order.products.any { product ->
//                        product.title.contains(query, ignoreCase = true)
//                    }
//                }
//            } else initialOrders
//
//            _orders.emit(foundOrders)
        }
    }

    private fun filterOrdersByDate(date: String) {
        viewModelScope.launch(dispatcher) {
            _ordersDateQuery.emit(date)

            val dateFormat = DateTimeFormatter.ofPattern(DateFormat.FULL_DATE)
            val parseResult = runCatching { dateFormat.parse(date) }
            val parsedDate = parseResult.getOrNull() ?: return@launch
            val localDate = LocalDate.from(parsedDate)

            val found = if (date.isNotEmpty()) {
                initialOrders.filter { order ->
                    val orderDateTemporal = dateFormat.parse(order.orderDate)
                    val orderLocalDate = LocalDate.from(orderDateTemporal)

                    orderLocalDate.isEqual(localDate)
                }
            } else initialOrders

            _orders.emit(found)
        }
    }

    private fun handleOrderPaymentSuccess(status: Boolean) {
        val snackbar = if (status) {
            SnackbarItem(title = "Заказ успешно оплачен", subtitle = "")
        } else {
            SnackbarItem(
                title = "Оплата не прошла",
                subtitle = "Попробуйте оплатить заказ повторно в личном кабинете"
            )
        }
        snackbarItemHost.setSnackbar(snackbar)
        loadData(forceOnline = true)
    }

    private fun updateValuesInTracker(profile: Profile) {
        runCatching {
            MyTracker.getTrackerParams()
                .setCustomParam("birthday", profile.birthday)
                .setCustomParam("city", profile.city)
                .setCustomParam("cashback", profile.cashback.toString())
                .setAge(ageFromDate(profile.birthday))
        }.getOrElse {}
    }

    fun setNetworkIsUnavailableState() =
        updateState { ProfileContract.State.ServerError }

    private fun loadData(forceOnline: Boolean) {
        viewModelScope.launch(dispatcher) {
            setState(ProfileContract.State.Loading)

            val isAuthorized = authInteractor.checkIsAuthorized()
            if (!isAuthorized) {
                checkForAuthorizationTechnicalWorks()
                return@launch
            }

            if (shouldEnableOnboarding()) {
                updateState { ProfileContract.State.OnboardingActive }
                return@launch
            }

            profileInteractor.getProfile(forceOnline).fold(
                onSuccess = { profile ->

                    if (profile.firstName.isEmpty() || profile.lastName.isEmpty()) {
                        updateState { ProfileContract.State.NecessaryFieldsUnfilled }
                        return@launch
                    }

                    loadOrders()
                    loadReferrals(forceOnline)
                    loadAddresses()
                    _profile.emit(profile)

                    setState(mostRecentState.value)
                },
                onFailure = {
                    updateState { ProfileContract.State.ServerError }
                }
            )

        }
    }

    private suspend fun loadOrders() {
        profileInteractor.getOrdersHistory()
            .onSuccess { orderList ->
                initialOrders.clear()
                initialOrders.addAll(orderList)
                _orders.emit(orderList)
            }
            .onFailure { Log.d("ProfileViewModel", "loadOrders: ${it.message}") }
    }

    private suspend fun loadReferrals(force: Boolean) {
        val referalInfo = profileInteractor.getReferals(force).getOrNull() ?: return
        _referralInfo.emit(referalInfo)
    }

    private suspend fun loadAddresses() {
        val addressesList = profileInteractor.getAddressList().getOrDefault(emptyList())
        _addresses.emit(addressesList)
    }

    private fun checkForAuthorizationTechnicalWorks() {
        viewModelScope.launch(dispatcher) {
            splashInteractor.fetchAppStatus().fold(
                onSuccess = { status ->
                    if (status.authTechnicalWorks) {
                        updateState { ProfileContract.State.AuthorizationTechnicalWorks }
                    } else {
                        setEffect { ProfileContract.Effect.NavigateToAuth }
                    }
                },
                onFailure = { setNetworkIsUnavailableState() }
            )
        }
    }

    private suspend fun shouldEnableOnboarding(): Boolean =
        onboardingInteractor.isOnboardingActive(OnboardingCacheKey.PROFILE)

    private fun deleteAccount() {
        viewModelScope.launch(dispatcher) {
            profileInteractor.deleteAccount()
            updateState { ProfileContract.State.Loading }
            setEffect { ProfileContract.Effect.ShowToast("Ваш аккаунт успешно удален") }
        }
    }

    private fun logout() {
        viewModelScope.launch(dispatcher) {
            profileInteractor.logout()
            MyTracker.trackEvent("logout")
        }
    }

    private fun finishOnboarding() {
        viewModelScope.launch(dispatcher) {
            onboardingInteractor.disableOnboarding("profileOnboarding")
            loadData(true)
        }
    }

}