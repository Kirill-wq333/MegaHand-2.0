package com.evothings.data.feature.coupon

import com.evothings.data.feature.coupon.datasource.CouponApiClient
import com.evothings.data.feature.coupon.dto.toCouponRequestDto
import com.evothings.data.feature.profile.dto.request.ConfirmPhoneRequest
import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.storage.cache.CacheStore
import com.evothings.data.storage.cache.fetchCache
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.coupon.model.CouponForm
import com.evothings.domain.feature.coupon.repository.CouponRepository

class CouponRepositoryImpl(
    private val apiSource: CouponApiClient,
    private val cacheStore: CacheStore
) : CouponRepository {

    override suspend fun isCouponAvailableForUser(): Result<Boolean> =
        apiSource.getIsCouponAvailable().awaitResult()
            .mapCatching { it.status }

    override suspend fun getCouponAmount(): Int {
        val reward = fetchCache(
            forceOnline = false,
            cacheKey = NetworkConfig.Routes.Coupon.getReward,
            cacheStore = cacheStore,
            fetchFromNetwork = { apiSource.getCouponRewardAmount().awaitResult() },
            mapper = {
                this.results.find { it.type == "promocode" }?.reward ?: -1
            }
        )

        return reward.getOrDefault(-1)
    }

    override suspend fun sendForm(form: CouponForm): Result<Unit> =
        apiSource.requestCouponCode(form.toCouponRequestDto()).awaitResult()

    override suspend fun confirmCode(phone: String, code: String): Result<Unit> {
        return apiSource.confirmCouponCode(ConfirmPhoneRequest(code.toInt(), phone)).awaitResult()
    }

}