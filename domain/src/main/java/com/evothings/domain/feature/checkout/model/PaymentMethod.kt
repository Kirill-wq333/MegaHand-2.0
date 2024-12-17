package com.evothings.domain.feature.checkout.model

import kotlinx.serialization.Serializable

@Serializable
data class PaymentMethod(
    val provider: Provider,
    val title: String,
    val isDefault: Boolean
) {

    enum class Provider {
        VISA, MASTER_CARD, SBER_PAY, MIR, SBP,
        T_BANK
    }

}
