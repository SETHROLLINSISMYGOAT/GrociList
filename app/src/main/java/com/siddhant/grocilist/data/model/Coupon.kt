package com.siddhant.grocilist.data.model

data class Coupon(
    val code:String="",
    val discountAmount: Int = 0,
    val isActive: Boolean = true,
    val expiryTimestamp: Long = 0,

)