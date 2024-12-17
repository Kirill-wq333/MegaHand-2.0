package com.evothings.data.feature.coupon.dto

import com.evothings.domain.feature.coupon.model.CouponForm
import com.google.gson.annotations.SerializedName

data class CouponRequestDto(
    @SerializedName("first_name") val name: String,
    @SerializedName("last_name") val surname: String,
    val phone: String,
    val city: String,
    val source: String = "promo_list"
)

fun CouponForm.toCouponRequestDto(): CouponRequestDto =
    CouponRequestDto(
        name = name,
        surname = surname,
        phone = phone,
        city = city
    )