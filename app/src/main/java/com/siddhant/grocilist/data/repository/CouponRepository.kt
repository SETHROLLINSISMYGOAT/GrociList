package com.siddhant.grocilist.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.siddhant.grocilist.data.model.Coupon
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CouponRepository @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()
    suspend fun validateCoupon(code:String) : Coupon?{
        val snapshot=firestore.collection("coupons")
            .whereEqualTo("code",code)
            .get()
            .await()
        val coupon=snapshot.documents.firstOrNull()?.toObject(Coupon::class.java)
        if(coupon==null || !coupon.isActive) return null
        if(coupon.expiryTimestamp>0 && coupon.expiryTimestamp<System.currentTimeMillis()) return null
        return coupon

    }

}