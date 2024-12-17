package com.evothings.data.feature.checkout.dto.mapper

import com.evothings.data.feature.checkout.dto.response.CdekOrder
import com.evothings.domain.feature.checkout.model.DeliveryOption
import com.evothings.domain.feature.checkout.model.PickupPoint

internal fun PickupPoint.toCdekOrder(): CdekOrder {
    return CdekOrder(
        pickupPointCode = this.code,
        deliveryTariff = DeliveryOption.PICK_UP.code
    )
}