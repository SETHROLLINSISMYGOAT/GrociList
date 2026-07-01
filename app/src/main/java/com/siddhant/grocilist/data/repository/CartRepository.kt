package com.siddhant.grocilist.data.repository

import com.siddhant.grocilist.data.local.CartDao
import com.siddhant.grocilist.data.model.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    fun getCartItems(): Flow<List<CartItem>> = cartDao.getAllCartItems()
    suspend fun addItem(cartItem: CartItem){
        cartDao.insertCartItem(cartItem)
    }
    suspend fun deleteItem(cartItem: CartItem){
        cartDao.deleteCartItem(cartItem)
    }
    suspend fun updateQuantity(item: CartItem, quantity: Int)=item.copy(quantity = quantity).let {
        cartDao.insertCartItem(it)
    }


}