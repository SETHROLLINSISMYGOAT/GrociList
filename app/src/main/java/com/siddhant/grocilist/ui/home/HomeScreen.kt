package com.siddhant.grocilist.ui.home

import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.siddhant.grocilist.data.model.CartItem
import com.siddhant.grocilist.domain.ProductState
import com.siddhant.grocilist.ui.cart.CartViewModel

@Composable
fun HomeScreen(
    navController: NavController,

    viewModel: HomeViewModel = hiltViewModel(),
    cartViewModel: CartViewModel= hiltViewModel()) {
    val productsState by viewModel.productsState.collectAsState()
    Button(onClick = {navController.navigate("cart")}) {
        Text("Go to Cart")
    }






    when (productsState) {
        is ProductState.Loading -> {
            CircularProgressIndicator()
        }
        is ProductState.Success -> {
            var searchQuery by remember { mutableStateOf("") }
            val products = (productsState as ProductState.Success).products
            var selectedCategory by remember { mutableStateOf<String?>(null) }
            val filteredProducts = products
                .filter{selectedCategory == null || it.category==selectedCategory}
                .filter{it.name.contains(searchQuery, ignoreCase = true)}
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it }
            )
            Row {
                Button(onClick = { selectedCategory = null }) { Text("All") }
                Button(onClick = { selectedCategory = "Fruits" }) { Text("Fruits") }
                Button(onClick = { selectedCategory = "Dairy" }) { Text("Dairy") }
                Button(onClick = { selectedCategory = "Bakery" }) { Text("Bakery") }
            }




            LazyColumn {
                items(filteredProducts) { product ->
                    Row{
                        Text("${product.name} - ₹${product.price}")
                        Button(onClick = {
                            cartViewModel.addItem(
                                CartItem(
                                    name=product.name,
                                    price=product.price,
                                    imageUrl = product.imageUrl

                                )

                            )
                        }){
                            Text("Add to Cart")
                        }
                    }
                }
            }
        }
        is ProductState.Error -> {
            Text((productsState as ProductState.Error).message)
        }

    }

}