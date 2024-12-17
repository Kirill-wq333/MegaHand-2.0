package com.evothings.data.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.evothings.data.storage.room.entity.products.ProductsEntity

@Dao
interface ProductsDao {

    @Insert(entity = ProductsEntity::class)
    suspend fun insertProductsEntity(entity: ProductsEntity)

    @Query("SELECT (SELECT COUNT(*) FROM unauthorized_products) == 0")
    suspend fun isEmpty(): Boolean

    @Query("SELECT * FROM unauthorized_products LIMIT 1")
    suspend fun getEntity(): ProductsEntity?

    @Query("UPDATE unauthorized_products SET cart = :cartItems")
    suspend fun setCartItems(cartItems: List<Int>)

    @Query("UPDATE unauthorized_products SET favourites = :favourites")
    suspend fun setFavourites(favourites: List<Int>)

    @Transaction
    suspend fun getCartItems(): List<Int> {
        val entity = getEntity()
        return entity?.cart ?: emptyList()
    }

    @Transaction
    suspend fun getFavourites(): List<Int> {
        val entity = getEntity()
        return entity?.favourites ?: emptyList()
    }

    @Transaction
    suspend fun appendCart(id: Int) {
        populateIfEmpty()
        val entity = getEntity()
        if (entity != null) {
            val itemsMutable = entity.cart.toMutableList()
            itemsMutable.add(id)
            setCartItems(itemsMutable)
        }
    }

    @Transaction
    suspend fun appendFavourite(id: Int) {
        populateIfEmpty()
        val entity = getEntity()
        if (entity != null) {
            val itemsMutable = entity.favourites.toMutableList()
            itemsMutable.add(id)
            setFavourites(itemsMutable)
        }
    }

    @Transaction
    suspend fun removeFromCart(id: Int) {
        val entity = getEntity()
        if (entity != null) {
            val items = entity.cart.toMutableList()
            items.remove(id)
            setCartItems(items)
        }
    }

    @Transaction
    suspend fun removeFromFavourite(id: Int) {
        val entity = getEntity()
        if (entity != null) {
            val items = entity.favourites.toMutableList()
            items.remove(id)
            setFavourites(items)
        }
    }

    private suspend fun populateIfEmpty() {
        if (isEmpty()) {
            val defaultEntity = ProductsEntity(cart = emptyList(), favourites = emptyList())
            insertProductsEntity(defaultEntity)
        }
    }

    @Query("DELETE FROM unauthorized_products")
    suspend fun clear()

}