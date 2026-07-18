package com.siddhant.grocilist.ui.Wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.siddhant.grocilist.data.repository.WalletRepository
import com.siddhant.grocilist.domain.WalletState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WalletViewModel @Inject constructor(
    private val repository: WalletRepository
) : ViewModel(){
    private val auth = FirebaseAuth.getInstance()
    private val _walletState = MutableStateFlow<WalletState>(WalletState.Loading)
    val walletState: StateFlow<WalletState> = _walletState
    fun loadWallet() {
        viewModelScope.launch {
            try {
                val wallet = repository.getWallet(auth.currentUser?.uid ?: "")
                _walletState.value = WalletState.Success(wallet)
            } catch (e: Exception) {
                _walletState.value = WalletState.Error(e.message ?: "Something went wrong")
            }
        }
    }
    fun topUp(amount: Int) {
        viewModelScope.launch {
            try {
                repository.topUpWallet(auth.currentUser?.uid ?: "", amount)
                loadWallet()
            } catch (e: Exception) {
                _walletState.value = WalletState.Error(e.message ?: "Top up failed")
            }
        }
    }

    fun deduct(amount: Int) {
        viewModelScope.launch {
            try {
                repository.deductFromWallet(auth.currentUser?.uid ?: "", amount)
                loadWallet()
            } catch (e: Exception) {
                _walletState.value = WalletState.Error(e.message ?: "Insufficient balance")
            }
        }
    }

}