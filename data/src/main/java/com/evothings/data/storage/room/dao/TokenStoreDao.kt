package com.evothings.data.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.evothings.data.storage.room.entity.auth.TokenEntity

@Dao
interface TokenStoreDao {

    @Insert(entity = TokenEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(entity: TokenEntity)

    @Query("UPDATE tokenStore SET refreshToken = :newRefresh")
    suspend fun updateRefreshToken(newRefresh: String)

    @Query("UPDATE tokenStore SET accessToken = :newAccess")
    suspend fun updateAccess(newAccess: String)

    @Query("UPDATE tokenStore SET accessExpiration = :expirationDate")
    suspend fun updateAccessExpiration(expirationDate: Long)

    @Query("UPDATE tokenStore SET refreshExpiration = :expirationDate")
    suspend fun updateRefreshExpiration(expirationDate: Long)

    @Transaction
    suspend fun updateAccessToken(newToken: String, expirationDate: Long) {
        updateAccess(newToken)
        updateAccessExpiration(expirationDate)
    }

    @Transaction
    suspend fun updateRefreshToken(newRefresh: String, expirationDate: Long) {
        updateRefreshToken(newRefresh)
        updateRefreshExpiration(expirationDate)
    }

    @Query("SELECT * FROM tokenStore")
    suspend fun getTokenEntity(): TokenEntity?

    @Query("DELETE FROM tokenStore")
    suspend fun clear()

    @Transaction
    suspend fun replaceToken(entity: TokenEntity) {
        clear()
        insertToken(entity)
    }

}