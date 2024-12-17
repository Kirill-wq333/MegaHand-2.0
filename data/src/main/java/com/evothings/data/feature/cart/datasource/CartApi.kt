package com.evothings.data.feature.cart.datasource

import com.evothings.data.feature.cart.dto.request.CreateOrderRequest
import com.evothings.data.feature.cart.dto.response.CartResponse
import com.evothings.data.feature.cart.dto.response.OrderIdResponse
import com.evothings.data.feature.product.dto.request.ProductId
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.data.network.config.NetworkConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface CartApi {

    @WithAuthorization
    @GET(NetworkConfig.Routes.Cart.getInfo)
    fun getCartInfo(): Call<CartResponse>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Cart.calculateCheckout)
    fun calculateCartPrice(@Body selection: Array<ProductId>): Call<CartResponse>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Cart.add)
    fun addToCart(@Body payload: ProductId): Call<Unit>

    @WithAuthorization
    @HTTP(method = "DELETE", path = NetworkConfig.Routes.Cart.remove, hasBody = true)
    fun removeFromCart(@Body payload: ProductId): Call<Unit>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Order.create)
    fun createOrder(@Body payload: CreateOrderRequest): Call<OrderIdResponse>
}