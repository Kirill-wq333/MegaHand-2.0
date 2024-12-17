package com.evothings.data.feature.cart

import com.evothings.data.feature.cart.datasource.CartApi
import com.evothings.data.feature.cart.dto.mapper.toCalculatedAmountCart
import com.evothings.data.feature.cart.dto.mapper.toCart
import com.evothings.data.feature.cart.dto.mapper.toCreateOrderRequest
import com.evothings.data.feature.product.dto.mapper.toProductIdRequest
import com.evothings.data.feature.product.util.modifyIfPresentInStorage
import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.data.utils.awaitResult
import com.evothings.data.utils.foldAuthorized
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.cart.model.Cart
import com.evothings.domain.feature.cart.repository.CartRepository
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.feature.product.repository.ProductRepository

class CartRepositoryImpl(
    private val cartApi: CartApi,
    private val authRepository: AuthRepository,
    private val productsDao: ProductsDao,
    private val productRepository: ProductRepository,
) : CartRepository {

    override suspend fun getCart(): Result<Cart> {
        return foldAuthorized(
            checkIsLoggedIn = { authRepository.isLoggedIn() },
            onAuthorized = {
                cartApi.getCartInfo().awaitResult()
                    .mapCatching { it.toCart() }
            },
            onNotAuthorized = { getUnauthorizedCart() }
        )
    }

    private suspend fun getUnauthorizedCart(): Result<Cart> =
        runCatching {
            val productIds = productsDao.getCartItems()
            val products = arrayListOf<Product>()
            for (id in productIds) {
                productRepository.getInfoById(id)
                    .onSuccess { products.add(it) }
            }
            val modified = products.modifyIfPresentInStorage(productsDao)
            Cart(modified, 0.0, 0.0, 0.0, 0.0)
        }


    override suspend fun calculatePrice(products: List<Int>): Result<Cart> {
        val selectionArray = products
            .map { it.toProductIdRequest() }
            .toTypedArray()
        return cartApi.calculateCartPrice(selectionArray)
            .awaitResult()
            .mapCatching { it.toCalculatedAmountCart() }
    }

    override suspend fun addProduct(id: Int): Result<Unit> {
        return foldAuthorized(
            checkIsLoggedIn = { authRepository.isLoggedIn() },
            onAuthorized = { cartApi.addToCart(id.toProductIdRequest()).awaitResult() },
            onNotAuthorized = {
                runCatching {
                    productsDao.appendCart(id)
                }
            }
        )
    }

    override suspend fun remove(id: Int): Result<Unit> {
        return foldAuthorized(
            checkIsLoggedIn = { authRepository.isLoggedIn() },
            onAuthorized = { cartApi.removeFromCart(id.toProductIdRequest()).awaitResult() },
            onNotAuthorized = {
                runCatching {
                    productsDao.removeFromCart(id)
                }
            }
        )
    }

    override suspend fun createOrder(selectedProducts: List<Int>): Result<String> {
        return cartApi.createOrder(selectedProducts.toCreateOrderRequest())
            .awaitResult()
            .mapCatching { it.orderId }
    }

}