package com.evothings.data.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.evothings.data.storage.room.dao.CardDao
import com.evothings.data.storage.room.dao.NotificationDao
import com.evothings.data.storage.room.dao.ProductsDao
import com.evothings.data.storage.room.dao.TokenStoreDao
import com.evothings.data.storage.room.entity.auth.TokenEntity
import com.evothings.data.storage.room.entity.card.CardEntity
import com.evothings.data.storage.room.entity.notification.NotificationEntity
import com.evothings.data.storage.room.entity.products.ProductsEntity
import com.evothings.data.storage.room.typeConverter.ListTypeConverter

@TypeConverters(value = [ListTypeConverter::class])
@Database(
    entities = [
        TokenEntity::class,
        CardEntity::class,
        NotificationEntity::class,
        ProductsEntity::class
    ],
    version = 7
)
abstract class MhandDatabase : RoomDatabase() {

    abstract fun getTokenDao(): TokenStoreDao

    abstract fun getCardDao(): CardDao

    abstract fun getNotificationsDao(): NotificationDao

    abstract fun getProductsDao(): ProductsDao

}