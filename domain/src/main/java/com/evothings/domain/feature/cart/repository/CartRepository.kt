package com.evothings.domain.feature.cart.repository

import com.evothings.domain.feature.cart.model.Cart

interface CartRepository {

    suspend fun addProduct(id: Int): Result<Unit>
    suspend fun remove(id: Int): Result<Unit>

    suspend fun getCart(): Result<Cart>

    suspend fun calculatePrice(products: List<Int>): Result<Cart>

    suspend fun createOrder(selectedProducts: List<Int>): Result<String>

}