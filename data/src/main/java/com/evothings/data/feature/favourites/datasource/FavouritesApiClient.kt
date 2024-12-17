package com.evothings.data.feature.favourites.datasource

import com.evothings.data.feature.catalog.dto.ProductCategoryResponse
import com.evothings.data.feature.product.dto.request.ProductId
import com.evothings.data.feature.product.dto.response.ProductResponse
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.data.network.config.NetworkConfig
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface FavouritesApiClient {

    @WithAuthorization
    @GET(NetworkConfig.Routes.Favourites.list)
    fun getFavouritesList(@Query("category") category: Int? = null): Call<Array<ProductResponse>>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Favourites.categories)
    fun getProductCategories(@Body payload: Array<ProductId>): Call<Array<ProductCategoryResponse>>

    @WithAuthorization
    @POST(NetworkConfig.Routes.Favourites.add)
    fun addToFavourites(@Body payload: ProductId): Call<Unit>

    @WithAuthorization
    @HTTP(method = "DELETE", path = NetworkConfig.Routes.Favourites.remove, hasBody = true)
    fun removeFromFavourites(@Body payload: ProductId): Call<Unit>

    @WithAuthorization
    @DELETE(NetworkConfig.Routes.Favourites.flush)
    suspend fun flushFavourites(): Response<Unit>

}