package com.evothings.data.storage.room.entity.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unauthorized_products")
data class ProductsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cart: List<Int>,
    val favourites: List<Int>
)
