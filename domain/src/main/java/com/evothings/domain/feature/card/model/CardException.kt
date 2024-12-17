package com.evothings.domain.feature.card.model

sealed class CardException : Throwable() {
    data object LoyalityNotAvailable : CardException()
    data object NoSuchCard : CardException()
    data object NetworkError : CardException()
}