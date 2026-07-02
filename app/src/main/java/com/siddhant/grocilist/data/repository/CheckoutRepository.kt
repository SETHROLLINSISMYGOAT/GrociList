package com.siddhant.grocilist.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.siddhant.grocilist.data.local.CartDao
import com.siddhant.grocilist.data.model.CartItem
import com.siddhant.grocilist.data.model.Order
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CheckoutRepository @Inject constructor(
    private val cartDao: CartDao
) {
    private val firestore= FirebaseFirestore.getInstance()
    private val auth= FirebaseAuth.getInstance()
    suspend fun placeOrder(items:List<CartItem>, total:Int){
        val order= Order(
            items = items,
            total = total,
            userId = auth.currentUser?.uid ?: ""

        )
        firestore.collection("orders").add(order).await()
        Log.d("CheckoutRepository", "Order placed: $order")
        items.forEach { cartDao.deleteCartItem(it) }

    }

}