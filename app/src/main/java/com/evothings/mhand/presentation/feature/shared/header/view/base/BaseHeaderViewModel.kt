package com.evothings.mhand.presentation.feature.shared.header.view.base

import com.evothings.domain.feature.notification.model.Notification
import com.evothings.mhand.core.viewmodel.BaseViewModel
import com.evothings.mhand.presentation.feature.shared.header.view.HeaderContract
import kotlinx.coroutines.flow.StateFlow

abstract class BaseHeaderViewModel : BaseViewModel<HeaderContract.Event, HeaderContract.State, HeaderContract.Effect>() {

    abstract val notifications: StateFlow<List<Notification>>
    abstract val unreadNotifications: StateFlow<Int>
    abstract val cardBalance: StateFlow<Int>

}