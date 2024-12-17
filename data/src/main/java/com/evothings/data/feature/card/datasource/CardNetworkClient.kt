package com.evothings.data.feature.card.datasource

import com.evothings.data.feature.card.dto.CardResponse
import com.evothings.data.feature.card.dto.CityAvailabilityResponse
import com.evothings.data.feature.card.dto.TransactionResponse
import com.evothings.data.network.annotation.WithAuthorization
import com.evothings.data.network.config.NetworkConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CardNetworkClient {

    @WithAuthorization
    @GET(NetworkConfig.Routes.Card.cardInfo)
    fun getCardInfo(): Call<CardResponse>

    @WithAuthorization
    @GET(NetworkConfig.Routes.Card.checkCityAvailable)
    fun checkCityAvailable(@Query("city") city: String): Call<CityAvailabilityResponse>

    @WithAuthorization
    @GET(NetworkConfig.Routes.Card.userTransactions)
    fun getUserTransactions(): Call<Array<TransactionResponse>>

}