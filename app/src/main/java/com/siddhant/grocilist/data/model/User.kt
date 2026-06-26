package com.siddhant.grocilist.data.model



data class User(
    val uid: String,
    val phoneNumber: String,
    val name: String = "",
    val createdAt: Long = System.currentTimeMillis()
)