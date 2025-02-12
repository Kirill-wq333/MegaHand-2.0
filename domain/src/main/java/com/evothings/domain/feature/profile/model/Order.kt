package com.evothings.domain.feature.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String = "",
    val orderId: String = "",
    val orderDate: String = "",
    val cost: Int = 0,
    val status: Status = Status.CREATED,
    val statusDisplay: String = "",
    val trackNumbers: List<String> = listOf(),
    val paymentUrl: String? = null,
    val products: List<OrderHistoryProduct> = emptyList()
) {

    enum class Status {
        CREATED, PAYMENT_AWAIT, PAYMENT_TIME_EXPIRED, PLACED, WAIT_DELIVERY,
        IN_PROGRESS, TRACK, REFUND_IN_PROGRESS, DELIVERY, READY_TO_PICKUP,
        READY_TO_SHIP_AT_SENDING_OFFICE, READY_FOR_SHIPMENT_IN_TRANSIT_CITY,
        ACCEPTED_AT_PICK_UP_POINT, TAKEN_BY_COURIER,
        COMPLETED, CANCELED, ERROR
    }

}
