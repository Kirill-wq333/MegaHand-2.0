package com.evothings.data.storage.room.entity.card

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.evothings.domain.feature.card.model.Card

@Entity("card")
data class CardEntity(
    @PrimaryKey
    val qrUrl: String,
    val balance: Int,
)


fun CardEntity.map(): Card = Card(
    balance = this.balance,
    barcodeUrl = this.qrUrl
)

fun Card.toEntity(): CardEntity = CardEntity(
    balance = this.balance,
    qrUrl = this.barcodeUrl ?: "https://slabstore.ru/portfolio/13/4.jpg"
)
