package com.evothings.data.feature.shops.dto.mapper

import com.evothings.data.feature.shops.dto.ShopResponse
import com.evothings.domain.feature.checkout.model.MapPoint
import com.evothings.domain.feature.shops.model.Shop

internal fun Array<ShopResponse>.toShopList(): List<Shop> {
    return this.map {
        Shop(
            address = it.address ?: "<Адрес неизвестен>",
            workSchedule = it.workSchedule?.tryReplaceIfContains("<br>", "\n")
                ?: "<График неизвестен>",
            point = tryGetCoordinates(it.coordinates),
            phone = it.phone ?: "",
            discountWeeks = runCatching {
                CalendarDayMapper(it.productAddition).mapToDiscountCalendar(it.calendar)
            }.getOrDefault(listOf()),
            productAdditionDays = runCatching {
                it.productAddition.joinToString(", ")
            }.getOrDefault("Не заполнено"),
            shortAddress = it.shortAddress,
            email = ""
        )
    }
}


private fun String.tryReplaceIfContains(oldValue: String, newValue: String): String =
    runCatching {
        if (!this.contains(oldValue))
            this
        else
            this.replace(oldValue, newValue)
    }.getOrDefault(this)

private fun tryGetCoordinates(coordinates: String): MapPoint =
    try {
        val coordinatesSplitted = coordinates.split(",")
        MapPoint(
            latitude = coordinatesSplitted[0].toDouble(),
            longitude = coordinatesSplitted[1].toDouble()
        )
    } catch(e: Exception) {
        MapPoint(0.0, 0.0)
    }