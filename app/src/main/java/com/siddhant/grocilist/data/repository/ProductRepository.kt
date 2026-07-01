package com.siddhant.grocilist.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.siddhant.grocilist.data.model.Product
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ProductRepository @Inject constructor(){
    private val firestore= FirebaseFirestore.getInstance()
    fun getProducts(): Flow<List<Product>> = callbackFlow {
        val listener = firestore.collection("products")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("ProductRepository", "Firestore error: ${error.message}", error)
                    close(error)  // closes the Flow with an error
                    return@addSnapshotListener
                }
                val products = snapshot?.toObjects(Product::class.java) ?: emptyList()
                trySend(products)  // emits a new value into the Flow
            }
        awaitClose { listener.remove() }  // cleanup when the Flow is cancelled
    }

}
