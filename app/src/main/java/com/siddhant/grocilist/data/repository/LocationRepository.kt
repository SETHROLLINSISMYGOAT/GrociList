package com.siddhant.grocilist.data.repository

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.FirebaseException
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
) {
    suspend fun updateLocation(orderId:String,lat:Double,lng:Double){
        val locationData = mapOf(
            "latitude" to lat,
            "longitude" to lng
        )
        try {
            database.getReference("locations").child(orderId).setValue(locationData).await()
        } catch (e: FirebaseException) {
            throw e
        }

    }
    fun getDeliveryLocation(orderId: String) : Flow<LatLng?> = callbackFlow {
        val ref=database.reference.child("orders/${orderId}/location")
        val listener = ref.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val lat = snapshot.child("latitude").getValue(Double::class.java)
                val lng = snapshot.child("longitude").getValue(Double::class.java)
                if (lat != null && lng != null) {
                    trySend(LatLng(lat, lng))
                } else {
                    trySend(null)
                }
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                close(error.toException())
            }
        })
        awaitClose {
            ref.removeEventListener(listener)
        }

    }
}