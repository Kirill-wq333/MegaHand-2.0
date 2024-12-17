package com.evothings.data.feature.card.dto

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    val amount: Number,
    val type: String,
    @SerializedName("date")
    val transactionDate: String,
    val comment: String
)