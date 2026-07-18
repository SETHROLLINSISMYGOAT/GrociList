package com.siddhant.grocilist.ui.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.siddhant.grocilist.domain.CheckoutState
import com.siddhant.grocilist.ui.Coupon.CouponViewModel
import com.siddhant.grocilist.ui.cart.CartViewModel

@Composable
fun CheckoutScreen(
    navController: NavController,
    cartViewModel: CartViewModel=hiltViewModel(),
    checkoutViewModel: CheckoutViewModel= hiltViewModel(),
    couponViewModel : CouponViewModel = hiltViewModel()

) {
    val couponState by couponViewModel.couponState.collectAsState()
    var couponCode by remember { mutableStateOf("") }
    val discount = couponState?.discountAmount ?: 0
    val cartItems by cartViewModel.cartState.collectAsState()
    val checkoutState by checkoutViewModel.checkoutState.collectAsState()
    val total = cartItems.sumOf { it.price * it.quantity }
    LaunchedEffect(checkoutState) {
        if (checkoutState is CheckoutState.Success) {
            navController.navigate("tracking/test_order_123") {
                popUpTo("home") { inclusive = false }
            }
        }
    }

    Column {
        Text("Order Summary")
        LazyColumn {
            items(cartItems){item->
                Text("${item.name} x${item.quantity} - ₹${item.price * item.quantity}")
            }

        }
        Text("Total: $total")
        TextField(
            value = couponCode,
            onValueChange = { couponCode = it },
            label = { Text("Coupon Code") }
        )
        Button(onClick = { couponViewModel.applyCoupon(couponCode) }) {
            Text("Apply Coupon")
        }
        couponState?.let {
            Text("Coupon applied! Saving ₹${it.discountAmount}")
        }
        Text("Total: ₹${total - discount}")
        Button(onClick = {checkoutViewModel.placeOrder(cartItems,total-discount)}) {
            Text("Place Order")
        }
        when (checkoutState) {
            is CheckoutState.Idle -> {}
            is CheckoutState.Loading -> { CircularProgressIndicator() }
            is CheckoutState.Success -> {

            }

            is CheckoutState.Error -> { Text((checkoutState as CheckoutState.Error).message) }


        }
    }


}