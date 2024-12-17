package com.evothings.data.feature.home.datasource

import com.evothings.data.network.config.NetworkConfig
import com.evothings.data.feature.home.dto.response.BannerResponse
import com.evothings.data.feature.home.dto.response.BrandResponse
import com.evothings.data.feature.home.dto.response.StoryResponse
import com.evothings.data.feature.home.dto.request.TransliterateCityRequest
import com.evothings.data.feature.home.dto.request.UserSurveyRequest
import com.evothings.domain.feature.product.model.PagedResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeNetworkClient {

    @GET(NetworkConfig.Routes.Main.banners)
    fun getBanners(): Call<Array<BannerResponse>>

    @GET(NetworkConfig.Routes.Main.brands)
    fun getBrands(): Call<Array<BrandResponse>>

    @GET(NetworkConfig.Routes.Main.stories)
    fun getStories(): Call<PagedResponse<StoryResponse>>

    @GET(NetworkConfig.Routes.Main.transliterate)
    fun transliterateCity(@Query("city") city: String): Call<TransliterateCityRequest>

    @POST(NetworkConfig.Routes.Main.userSurvey)
    fun submitSurvey(@Body survey: UserSurveyRequest): Call<Unit>

}