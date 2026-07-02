package com.siddhant.grocilist.domain

sealed class CheckoutState {
    object Idle: CheckoutState()
    object Loading: CheckoutState()
    object Success : CheckoutState()
    data class Error(val message:String): CheckoutState()
}