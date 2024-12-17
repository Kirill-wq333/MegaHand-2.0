package com.evothings.data.feature.checkout.dto.mapper

import com.evothings.data.feature.checkout.dto.response.cdek.CdekPointsResponse
import com.evothings.domain.feature.checkout.model.MapPoint
import com.evothings.domain.feature.checkout.model.PickupPoint

internal fun CdekPointsResponse.toPickupPointsList(): List<PickupPoint> {
    return this.branches.map { cdekPoint ->
        PickupPoint(
            code = cdekPoint.code,
            address = cdekPoint.location.address,
            point = MapPoint(
                latitude = cdekPoint.location.latitude.toDouble(),
                longitude = cdekPoint.location.longitude.toDouble()
            )
        )
    }
}