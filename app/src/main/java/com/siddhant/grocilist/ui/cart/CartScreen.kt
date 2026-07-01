package com.siddhant.grocilist.ui.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.siddhant.grocilist.data.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel= hiltViewModel()
) {
    val cartItems by viewModel.cartState.collectAsState()
    val total = cartItems.sumOf { it.price * it.quantity }
    Column{
        Text("Cart")
        LazyColumn {
            items(cartItems) { item ->
                Row {
                    Text("${item.name} x${item.quantity} - ₹${item.price * item.quantity}")
                    Button(onClick = { viewModel.updateQuantity(item, item.quantity + 1) }) {
                        Text("+")
                    }
                    Button(onClick = {
                        if (item.quantity > 1) viewModel.updateQuantity(item, item.quantity - 1)
                        else viewModel.removeItem(item)
                    }) {
                        Text("-")
                    }
                    Button(onClick = { viewModel.removeItem(item) }) {
                        Text("Remove")
                    }
                }
            }
        }
        Text("Total: ₹$total")
        Button(onClick = {navController.navigate("checkout")}) {
            Text("Checkout")
        }
    }

}


