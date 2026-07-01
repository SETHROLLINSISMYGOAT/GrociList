package com.siddhant.grocilist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.siddhant.grocilist.data.model.CartItem
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)
    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)
    @androidx.room.Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItem>>
}