package com.evothings.data.feature.profile.dto.mapper

import com.evothings.data.feature.profile.dto.response.order.OrderResponse
import com.evothings.data.utils.date.tryFormatDate
import com.evothings.domain.feature.profile.model.Order
import com.evothings.domain.util.DateFormat

internal fun Array<OrderResponse>.toOrdersList(): List<Order> {
    return this.map { response ->
        with(response) {
            Order(
                id = id,
                orderId = orderId,
                orderDate = creationDate.formatOrderDate(),
                cost = cost.toInt(),
                status = status.toOrderStatus(),
                paymentUrl = paymentLink,
                statusDisplay = displayStatus,
                track = track,
                products = items.toHistoryItems()
            )
        }
    }
}

private fun String.formatOrderDate(): String = tryFormatDate(
    inputFormat = DateFormat.FULL_DATE_PRECISE,
    outputFormat = DateFormat.FULL_DATE
)

private fun String.toOrderStatus(): Order.Status = when(this) {
    "created" -> Order.Status.CREATED
    "wait_payment" -> Order.Status.PAYMENT_AWAIT
    "time_expired" -> Order.Status.PAYMENT_TIME_EXPIRED
    "placed" -> Order.Status.PLACED
    "in_progress" -> Order.Status.IN_PROGRESS
    "wait_delivery" -> Order.Status.WAIT_DELIVERY
    "add_track_number" -> Order.Status.TRACK
    "make_refund" -> Order.Status.REFUND_IN_PROGRESS
    "in_transit" -> Order.Status.DELIVERY
    "wait_client" -> Order.Status.READY_TO_PICKUP
    "delivered" -> Order.Status.COMPLETED
    "canceled" -> Order.Status.CANCELED
    "READY_TO_SHIP_AT_SENDING_OFFICE" -> Order.Status.READY_TO_SHIP_AT_SENDING_OFFICE
    "READY_FOR_SHIPMENT_IN_TRANSIT_CITY" -> Order.Status.READY_FOR_SHIPMENT_IN_TRANSIT_CITY
    "ACCEPTED_AT_PICK_UP_POINT" -> Order.Status.ACCEPTED_AT_PICK_UP_POINT
    "TAKEN_BY_COURIER" -> Order.Status.TAKEN_BY_COURIER
    else -> Order.Status.ERROR
}