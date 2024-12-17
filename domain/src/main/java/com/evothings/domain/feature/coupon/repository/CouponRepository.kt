package com.evothings.domain.feature.coupon.repository

import com.evothings.domain.feature.coupon.model.CouponForm

interface CouponRepository {

    suspend fun isCouponAvailableForUser(): Result<Boolean>
    suspend fun getCouponAmount(): Int

    suspend fun sendForm(form: CouponForm): Result<Unit>

    suspend fun confirmCode(phone: String, code: String): Result<Unit>

}