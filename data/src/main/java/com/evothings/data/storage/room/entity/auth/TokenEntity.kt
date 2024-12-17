package com.evothings.data.storage.room.entity.auth

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokenStore")
data class TokenEntity(
    @PrimaryKey val accessToken: String,
    val refreshToken: String,
    val accessExpiration: Long,
    val refreshExpiration: Long
)
