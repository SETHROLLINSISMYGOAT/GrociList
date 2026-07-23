package com.siddhant.grocilist.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.siddhant.grocilist.data.model.CartItem
import com.siddhant.grocilist.data.repository.CheckoutRepository
import com.siddhant.grocilist.data.repository.WalletRepository
import com.siddhant.grocilist.domain.AuthState
import com.siddhant.grocilist.domain.CheckoutState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val checkoutRepository: CheckoutRepository,
    private val walletRepository: WalletRepository

) : ViewModel() {
    private val _checkoutState = MutableStateFlow<CheckoutState>(CheckoutState.Idle)
    private val auth = FirebaseAuth.getInstance()
    val checkoutState: StateFlow<CheckoutState> = _checkoutState
    fun placeOrder(items: List<CartItem>, total: Int) {
        viewModelScope.launch {

            _checkoutState.value = CheckoutState.Loading

            try {
                val orderPlaced = checkoutRepository.placeOrder(items, total)
                _checkoutState.value = CheckoutState.Success
                walletRepository.addLoyaltyPoints(
                    userId = auth.currentUser?.uid ?: "",
                    points = total
                )

            } catch (e: Exception) {
                _checkoutState.value = CheckoutState.Error(e.message ?: "Something went wrong")
            }

        }


    }




}