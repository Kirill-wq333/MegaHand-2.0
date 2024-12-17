package com.evothings.domain.feature.cart.interactor

import com.evothings.domain.feature.cart.repository.CartRepository

class CartInteractor(private val repository: CartRepository) {

    suspend fun deleteFromCart(id: Int) = repository.remove(id)

    suspend fun getAllItems() = repository.getCart()

    suspend fun calculatePrice(products: List<Int>) = repository.calculatePrice(products)

    suspend fun createOrder(products: List<Int>) = repository.createOrder(products)

}