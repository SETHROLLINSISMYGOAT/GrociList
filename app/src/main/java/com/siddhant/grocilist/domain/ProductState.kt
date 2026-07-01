package com.siddhant.grocilist.domain

import com.siddhant.grocilist.data.model.Product

sealed class ProductState{
    object Loading: ProductState()
    data class Success(val products:List<Product>): ProductState()
    data class Error(val message:String): ProductState()

}