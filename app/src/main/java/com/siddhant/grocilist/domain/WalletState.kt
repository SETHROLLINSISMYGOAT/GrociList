package com.siddhant.grocilist.domain

import com.siddhant.grocilist.data.model.Wallet

sealed class WalletState {
    object Loading : WalletState()
    data class Success(val wallet: Wallet) : WalletState()
    data class Error(val message: String) : WalletState()
}