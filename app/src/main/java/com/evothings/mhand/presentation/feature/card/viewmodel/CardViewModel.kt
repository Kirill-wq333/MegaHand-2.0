package com.evothings.mhand.presentation.feature.card.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.util.DateFormat
import com.evothings.domain.feature.auth.interactor.AuthInteractor
import com.evothings.domain.feature.card.interactor.CardInteractor
import com.evothings.domain.feature.profile.interactor.ProfileInteractor
import com.evothings.domain.feature.card.model.Transaction
import com.evothings.domain.feature.card.model.CardException
import com.evothings.domain.feature.onboarding.interactor.OnboardingInteractor
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.card.viewmodel.enumeration.CardFilterType
import com.evothings.mhand.presentation.feature.card.ui.CardUiState
import com.evothings.mhand.presentation.feature.onboarding.model.OnboardingCacheKey
import com.evothings.mhand.presentation.feature.snackbar.host.SnackbarItemHost
import com.evothings.mhand.presentation.feature.snackbar.model.SnackbarItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val cardInteractor: CardInteractor,
    private val authInteractor: AuthInteractor,
    private val profileInteractor: ProfileInteractor,
    private val onboardingInteractor: OnboardingInteractor,
    private val snackbarItemHost: SnackbarItemHost
) : BaseViewModel<CardContract.Event, CardContract.State, CardContract.Effect>() {

    private val _initialTransactionsList: MutableStateFlow<List<Transaction>> =
        MutableStateFlow(listOf())

    private val _currentlyChosenFilter = MutableStateFlow(CardFilterType.ALL)

    private val _cardUiState = MutableStateFlow(CardUiState())
    internal val cardUiState = _cardUiState.asStateFlow()

    override fun setInitialState(): CardContract.State = CardContract.State.Loading

    override fun handleEvent(event: CardContract.Event) = when(event) {
        is CardContract.Event.LoadData -> loadData(event.force, event.offlineMode)
        is CardContract.Event.ChangeFilter -> chooseFilter(event.filterIndex)
        is CardContract.Event.FinishOnboarding -> finishOnboarding()
        is CardContract.Event.NotifyLoyalitySystemAppear -> enableLoyalitySystemNotificationSnackbar()
    }

    private fun loadData(force: Boolean, offlineMode: Boolean) {
        viewModelScope.launch(dispatcher) {
            setState(CardContract.State.Loading)

            val isAuthorized = authInteractor.checkIsAuthorized()
            if (!isAuthorized) {
                updateState { CardContract.State.UserIsNotAuthorized }
                return@launch
            }

            val isOnboardingActive = onboardingInteractor.isOnboardingActive(OnboardingCacheKey.CARD)
            if (isOnboardingActive) {
                setState(CardContract.State.OnboardingActive)
                return@launch
            }

            val profile = if (!offlineMode) {
                profileInteractor.getProfile(false).getOrNull()
            } else null

            cardInteractor.getCardInfo(force, offlineMode, profile?.city.orEmpty()).fold(
                onSuccess = { model ->

                    val transactions =
                        cardInteractor.getRawTransactions(force, offlineMode).getOrDefault(listOf())
                    _initialTransactionsList.emit(transactions)

                    _currentlyChosenFilter.emit(CardFilterType.ALL)

                    val sortedTransactions = cardInteractor.sortTransactions(transactions)
                    _cardUiState.emit(
                        CardUiState(
                            card = model,
                            cashback = (profile?.cashback ?: 0),
                            currentFilter = _currentlyChosenFilter.value,
                            transactions = sortedTransactions,
                            offlineMode = offlineMode
                        )
                    )

                    setState(CardContract.State.Loaded)
                },
                onFailure = { error ->
                    updateState {
                        when (error) {
                            is CardException.LoyalityNotAvailable -> {
                                CardContract.State.LoyalityNotAvailable
                            }
                            is CardException.NoSuchCard -> {
                                showSnackbarCardStorageEmpty()
                                CardContract.State.NetworkError
                            }
                            else -> CardContract.State.NetworkError
                        }
                    }
                }
            )

        }
    }

    private fun showSnackbarCardStorageEmpty() {
        snackbarItemHost.setSnackbar(
            SnackbarItem(
                title = "Оффлайн-режим недоступен",
                subtitle = "В хранилище приложения нет вашей карты"
            )
        )
    }

    private fun finishOnboarding() {
        viewModelScope.launch(dispatcher) {
            onboardingInteractor.disableOnboarding("cardOnboarding")
            setState(CardContract.State.Loading)
        }
    }

    private fun chooseFilter(chosenIndex: Int) {
        val filter = CardFilterType.entries[chosenIndex]
        _currentlyChosenFilter.update { filter }

        _cardUiState.update { current ->
            val filteredList = filterTransactions(filter)
            val transactionsMap = cardInteractor.sortTransactions(filteredList)

            current.copy(
                currentFilter = filter,
                transactions = transactionsMap
            )
        }
    }

    private fun enableLoyalitySystemNotificationSnackbar() {
        snackbarItemHost.setSnackbar(
            SnackbarItem(
                title = "Пришлем пуш",
                subtitle = "Как только в твоем городе заработает система лояльности"
            )
        )
    }

    private fun filterTransactions(currentFilter: CardFilterType): List<Transaction> =
        _initialTransactionsList.value
            .filter { model -> isConformsToFilter(currentFilter, model.date) }


    private fun isConformsToFilter(
        filterType: CardFilterType,
        operationDate: String
    ): Boolean {
        val maxDaysDelta = when(filterType) {
            CardFilterType.YEAR -> 365
            CardFilterType.MONTH -> 31
            CardFilterType.DAY -> 1
            CardFilterType.ALL -> return true
        }
        val dateFormat = DateTimeFormatter.ofPattern(DateFormat.FULL_DATE_PRECISE)

        val operationDateParsed = try {
            dateFormat.parse(operationDate)
        } catch(e: DateTimeParseException) {
            return false
        }

        val operationLocalDate = LocalDateTime.from(operationDateParsed)
        val now = LocalDateTime.now()
        val differenceBetweenDates = Duration.between(operationLocalDate, now)

        return differenceBetweenDates.toDays() <= maxDaysDelta
    }

}