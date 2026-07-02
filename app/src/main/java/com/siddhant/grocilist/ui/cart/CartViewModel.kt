package com.siddhant.grocilist.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.siddhant.grocilist.data.model.CartItem
import com.siddhant.grocilist.data.repository.CartRepository
import com.siddhant.grocilist.domain.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {
    private val _cartState = MutableStateFlow<List<CartItem>>(emptyList())
    val cartState: StateFlow<List<CartItem>> = _cartState

    init {
        viewModelScope.launch {
            repository.getCartItems().collect { cartItems ->
                _cartState.value = cartItems
            }
        }
    }

    fun addItem(item: CartItem) {
        viewModelScope.launch { repository.addItem(item) }

    }

    fun removeItem(item: CartItem) {
        viewModelScope.launch { repository.deleteItem(item) }
    }

    fun updateQuantity(item: CartItem, quantity: Int) {
        viewModelScope.launch { repository.updateQuantity(item, quantity) }
    }
}