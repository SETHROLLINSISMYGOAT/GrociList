package com.siddhant.grocilist.data.model

import com.google.firebase.Timestamp

data class Order(
    val items: List<CartItem> = emptyList(),
    val total: Int = 0,
    val status: String = "pending",
    val timestamp: Long = System.currentTimeMillis(),
    val userId: String = ""
)