package com.siddhant.grocilist.data.model

data class Wallet(
    var userId:String="",
    var balance: Int = 0,
    var transaction: List<WalletTransaction> = emptyList()

)