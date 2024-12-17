package com.evothings.widget.viewmodel

sealed interface WidgetState {

    data object Success : WidgetState
    data object LoyalityNotAvailable : WidgetState
    data object NotAuthorized : WidgetState

}