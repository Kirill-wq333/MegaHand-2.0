package com.evothings.mhand.presentation.feature.shared.header.viewmodel

import androidx.lifecycle.viewModelScope
import com.evothings.domain.util.DateFormat.FULL_DATE_MICROSECONDS
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.domain.feature.auth.interactor.AuthInteractor
import com.evothings.domain.feature.card.interactor.CardInteractor
import com.evothings.domain.feature.notification.interactor.NotificationInteractor
import com.evothings.domain.feature.profile.interactor.ProfileInteractor
import com.evothings.domain.feature.notification.model.Notification
import com.evothings.mhand.presentation.feature.shared.header.view.base.BaseHeaderViewModel
import java.time.Duration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HeaderViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val cardInteractor: CardInteractor,
    private val notificationInteractor: NotificationInteractor,
    private val profileInteractor: ProfileInteractor,
    private val appSettingsInteractor: AppSettingsInteractor
) : BaseHeaderViewModel() {

    override fun setInitialState(): HeaderContract.State = HeaderContract.State.Idle


    private val _notifications: MutableStateFlow<List<Notification>> = MutableStateFlow(listOf())
    override val notifications = _notifications.asStateFlow()

    private val _unreadNotifications: MutableStateFlow<Int> = MutableStateFlow(0)
    override val unreadNotifications: StateFlow<Int>
        get() = _unreadNotifications.asStateFlow()

    private val _cardBalance: MutableStateFlow<Int> = MutableStateFlow(-1)
    override val cardBalance = _cardBalance.asStateFlow()

    override fun handleEvent(event: HeaderContract.Event) = when(event) {
        is HeaderContract.Event.ChooseCity -> setCity(event.city)
        is HeaderContract.Event.GetCardBalance -> getCardBalance()
        is HeaderContract.Event.GetNotifications -> getNotificationsList()
        is HeaderContract.Event.ToggleDevMode -> toggleDevMode()
        is HeaderContract.Event.DeleteNotification -> deleteNotificationById(event.id)
        is HeaderContract.Event.DeleteAllNotifications -> deleteAllNotifications()
        is HeaderContract.Event.ReadAllNotifications -> markNotificationsAsRead()
    }

    private fun getCardBalance() {
        viewModelScope.launch(dispatcher) {
            val isAuthorized = authInteractor.checkIsAuthorized()
            if (!isAuthorized) return@launch

            val user = profileInteractor.getProfile(false).getOrNull() ?: return@launch
            cardInteractor.getCardInfo(forceOnline = false, city = user.city).fold(
                onSuccess = { model ->
                    _cardBalance.emit(model.balance)
                },
                onFailure = {}
            )
        }
    }

    private fun getNotificationsList() {
        viewModelScope.launch(dispatcher) {
            notificationInteractor.getAllNotifications().fold(
                onSuccess = { notificationsList ->
                    val notificationsWithFormattedDays = notificationsList
                        .sortedByDescending { item -> item.arrivalTime }
                        .map { notification ->
                            notification.copy(
                                arrivalTime = notification.arrivalTime.formatDateToDelta()
                            )
                        }
                    val unreadCount = notificationsList.filter { it.unread }.size
                    _unreadNotifications.emit(unreadCount)
                   _notifications.emit(notificationsWithFormattedDays)
                },
                onFailure = {}
            )
        }
    }

    private fun String.formatDateToDelta(): String {
        val dateFormat = DateTimeFormatter.ofPattern(FULL_DATE_MICROSECONDS)
        val parsedDateInstant = dateFormat.parse(this)
        val date = LocalDateTime.from(parsedDateInstant)
        val now = LocalDateTime.now()
        val difference = Duration.between(date, now)

        val minutes = difference.toMinutes()
        val hours = difference.toHours()
        val days = difference.toDays()

        return when {
            days >= 1 -> "$days д. назад"
            hours >= 1 -> "$hours ч. назад"
            minutes < 1 -> "сейчас"
            else -> "$minutes м. назад"
        }
    }

    private fun deleteNotificationById(id: Int) {
        viewModelScope.launch(dispatcher) {
            notificationInteractor.deleteNotificationById(id)
            getNotificationsList()
        }
    }

    private fun deleteAllNotifications() {
        viewModelScope.launch(dispatcher) {
            _notifications.emit(mutableListOf())
            notificationInteractor.deleteAllNotifications()
        }
    }

    private fun markNotificationsAsRead() {
        viewModelScope.launch(dispatcher) {
            _unreadNotifications.emit(0)
            notificationInteractor.setAllNotificationsAsRead()
        }
    }

    private fun setCity(city: String) {
        viewModelScope.launch(dispatcher) {
            appSettingsInteractor.setCity(city)
        }
    }

    private fun toggleDevMode() {
        viewModelScope.launch(dispatcher) {
            appSettingsInteractor.toggleDevMode()
            val currentState = if (appSettingsInteractor.isDevModeEnabled()) "enabled" else "disabled"
            setEffect { HeaderContract.Effect.ShowToast("Dev mode $currentState") }
        }
    }


}