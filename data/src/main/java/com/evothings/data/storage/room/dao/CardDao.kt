package com.evothings.data.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evothings.data.storage.room.entity.card.CardEntity

@Dao
interface CardDao {

    @Insert(entity = CardEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)

    @Query("SELECT * FROM card")
    suspend fun getCard(): CardEntity

    @Query("DELETE FROM card")
    suspend fun deleteCard()

}