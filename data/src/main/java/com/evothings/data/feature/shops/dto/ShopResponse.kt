package com.evothings.data.feature.shops.dto

import com.google.gson.annotations.SerializedName

data class ShopResponse(
    val phone: String?,
    @SerializedName("city") val cityName: String,
    @SerializedName("city_id") val cityId: String,
    @SerializedName("work_hours") val workSchedule: String?,
    @SerializedName("product_addition") val productAddition: Array<Int>,
    val coordinates: String,
    @SerializedName("addr") val address: String?,
    @SerializedName("addr_two") val shortAddress: String?,
    val calendar: Array<CalendarDay>,
)
