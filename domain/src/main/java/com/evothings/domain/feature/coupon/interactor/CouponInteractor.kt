package com.evothings.domain.feature.coupon.interactor

import com.evothings.domain.feature.coupon.model.CouponForm
import com.evothings.domain.feature.coupon.repository.CouponRepository

class CouponInteractor(val repository: CouponRepository) {

    suspend fun getCouponAmount() = repository.getCouponAmount()

    suspend fun isCouponAvailableForUser() = repository.isCouponAvailableForUser()

    suspend fun sendForm(form: CouponForm) = repository.sendForm(form)

    suspend fun confirmCode(phone: String, code: String) = repository.confirmCode(phone, code)

}