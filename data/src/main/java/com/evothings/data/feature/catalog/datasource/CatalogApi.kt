package com.evothings.data.feature.catalog.datasource

import com.evothings.data.feature.catalog.dto.FilterValueResponse
import com.evothings.data.feature.catalog.dto.ProductCategoryResponse
import com.evothings.data.feature.catalog.dto.SearchHintResponse
import com.evothings.data.network.config.NetworkConfig
import com.evothings.domain.feature.product.model.PagedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatalogApi {

    @GET(NetworkConfig.Routes.Catalog.categories)
    fun getProductCategories(): Call<PagedResponse<ProductCategoryResponse>>

    @GET(NetworkConfig.Routes.Catalog.filterValues)
    fun getFilterValues(
        @Query("type__name") type: String
    ): Call<PagedResponse<FilterValueResponse>>

    @GET(NetworkConfig.Routes.Catalog.searchHints)
    fun performSearch(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int = 0
    ): Call<SearchHintResponse>

}