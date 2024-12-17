package com.evothings.data.feature.address.dto

import com.google.gson.annotations.SerializedName

data class AddressDataModel(
    val id: Int,
    val address: String,
    val city: String,
    val street: String,
    val house: String,
    val apartment: String?,
    @SerializedName("postal_code")
    val postalCode: String?,
    val main: Boolean?
)
