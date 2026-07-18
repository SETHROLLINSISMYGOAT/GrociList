package com.siddhant.grocilist.data.model



data class WalletTransaction(
    val amount:Int=0,
    val type:String="",
    val description: String ="",
    val timestamp: Long = System.currentTimeMillis()

)