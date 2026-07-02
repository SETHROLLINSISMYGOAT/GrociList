package com.siddhant.grocilist.ui.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.siddhant.grocilist.domain.CheckoutState
import com.siddhant.grocilist.ui.cart.CartViewModel

@Composable
fun CheckoutScreen(
    navController: NavController,
    cartViewModel: CartViewModel=hiltViewModel(),
    checkoutViewModel: CheckoutViewModel= hiltViewModel()

) {
    val cartItems by cartViewModel.cartState.collectAsState()
    val checkoutState by checkoutViewModel.checkoutState.collectAsState()
    val total = cartItems.sumOf { it.price * it.quantity }
    LaunchedEffect(checkoutState){
        when(checkoutState){
            is CheckoutState.Success->{
                navController.navigate("home")
            }
            else->{}
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
        Button(onClick = {checkoutViewModel.placeOrder(cartItems,total)}) {
            Text("Place Order")
        }
        when (checkoutState) {
            is CheckoutState.Idle -> {}
            is CheckoutState.Loading -> { CircularProgressIndicator() }
            is CheckoutState.Success -> {}
            is CheckoutState.Error -> { Text((checkoutState as CheckoutState.Error).message) }

        }
    }


}