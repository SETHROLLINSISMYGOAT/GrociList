package com.siddhant.grocilist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")

data class CartItem(
    @PrimaryKey val name:String,
    val price:Int,
    val quantity:Int=1,
    val imageUrl:String=""


)