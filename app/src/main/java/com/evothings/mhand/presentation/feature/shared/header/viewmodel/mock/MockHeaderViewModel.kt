package com.evothings.mhand.presentation.feature.shared.header.viewmodel.mock

import androidx.compose.runtime.Stable
import com.evothings.domain.feature.notification.model.Notification
import com.evothings.mhand.presentation.feature.shared.header.viewmodel.HeaderContract
import com.evothings.mhand.presentation.feature.shared.header.viewmodel.base.BaseHeaderViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class MockHeaderViewModel : BaseHeaderViewModel() {

    override fun setInitialState(): HeaderContract.State =
        HeaderContract.State.Idle

    override val cardBalance: StateFlow<Int>
        get() = MutableStateFlow(-1)

    override val unreadNotifications: StateFlow<Int>
        get() = MutableStateFlow(0)

    override val notifications: StateFlow<List<Notification>>
        get() = MutableStateFlow(listOf())

    override fun handleEvent(event: HeaderContract.Event) {}

}