package com.evothings.data.feature.checkout

import com.evothings.data.feature.checkout.datasource.CheckoutApi
import com.evothings.data.feature.checkout.dto.mapper.toAddressRequest
import com.evothings.data.feature.checkout.dto.mapper.toAmountRequest
import com.evothings.data.feature.checkout.dto.mapper.toCheckoutData
import com.evothings.data.feature.checkout.dto.mapper.toCheckoutInfo
import com.evothings.data.feature.checkout.dto.mapper.toDeliveryPrice
import com.evothings.data.feature.checkout.dto.mapper.toPickupPointsList
import com.evothings.data.feature.checkout.dto.mapper.toPriceDetails
import com.evothings.data.feature.checkout.dto.request.DeliveryPriceRequest
import com.evothings.data.feature.checkout.dto.request.PointsDiscountRequest
import com.evothings.data.utils.awaitResult
import com.evothings.domain.feature.address.repository.AddressRepository
import com.evothings.domain.feature.card.model.CardException
import com.evothings.domain.feature.card.repository.CardRepository
import com.evothings.domain.feature.checkout.model.CheckoutInfo
import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.domain.feature.checkout.model.DeliveryOption
import com.evothings.domain.feature.checkout.model.DeliveryPrice
import com.evothings.domain.feature.checkout.model.PickupPoint
import com.evothings.domain.feature.checkout.model.PriceDetails
import com.evothings.domain.feature.checkout.repository.CheckoutRepository
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.feature.product.repository.ProductRepository
import com.evothings.domain.feature.profile.repository.ProfileRepository

class CheckoutRepositoryImpl(
    private val api: CheckoutApi,
    private val profileRepository: ProfileRepository,
    private val addressRepository: AddressRepository,
    private val cardRepository: CardRepository,
    private val productRepository: ProductRepository
) : CheckoutRepository {

    override suspend fun getCheckoutInfo(id: String): Result<CheckoutInfo> {
        return runCatching {
            val profile = profileRepository.getProfileInfo(false).getOrThrow()
            val addresses = addressRepository.getUserAddresses().getOrThrow()

            val orderData = api.getOrderCheckoutData(id).awaitResult().getOrThrow()
            val checkoutInfo = orderData.toCheckoutInfo()
            val orderItems = orderData.items.map { it.productId }

            val cardResult = cardRepository.getCardInfo(false, false, profile.city)
            val cardBalance = cardResult.getOrNull()?.balance ?: 0
            val cardIsNotAvailable = (cardResult.exceptionOrNull() is CardException.LoyalityNotAvailable)
            val availableBalance = api.getAvailableCardBalance(id, cardBalance.toAmountRequest())
                .awaitResult()
                .getOrNull()
                ?.availableBalance?.toDouble() ?: 0.0

            checkoutInfo.copy(
                profile = profile,
                addresses = addresses,
                cardBalance = cardBalance,
                availableBalance = availableBalance,
                cardIsAvailable = !cardIsNotAvailable,
                orderItems = getOrderProducts(orderItems),
            )
        }
    }

    override suspend fun getDeliveryCost(
        id: String,
        city: String,
        option: DeliveryOption
    ): Result<DeliveryPrice> {
        return api.calculateDeliveryCost(
            id = id,
            payload = DeliveryPriceRequest(
                city = city,
                deliveryOptionCode = option.code
            )
        ).awaitResult().map { it.toDeliveryPrice() }
    }

    override suspend fun getPointsDiscount(
        id: String,
        deliveryCost: Double,
        pointsToWithdraw: Int
    ): Result<PriceDetails> {
        return api.calculatePointsDiscount(
            id = id,
            payload = PointsDiscountRequest(
                deliveryCost = deliveryCost,
                pointsToWithdraw = pointsToWithdraw
            )
        ).awaitResult().mapCatching { it.toPriceDetails() }
    }

    private suspend fun getOrderProducts(productIds: List<Int>): List<Product> {
        val items = arrayListOf<Product>()
        for (id in productIds) {
            val product = productRepository.getInfoById(id, false).getOrThrow()
            items.add(product)
        }
        return items
    }

    override suspend fun checkout(id: String, checkoutResult: CheckoutResult): Result<String> {
        return api.updateCheckoutOrder(id, checkoutResult.toCheckoutData()).awaitResult()
            .mapCatching { it.link }
    }

    override suspend fun getCdekPoints(city: String): Result<List<PickupPoint>> {
        return api.getCdekPickupPoints(city.toAddressRequest()).awaitResult()
            .mapCatching { it.toPickupPointsList() }
    }

}