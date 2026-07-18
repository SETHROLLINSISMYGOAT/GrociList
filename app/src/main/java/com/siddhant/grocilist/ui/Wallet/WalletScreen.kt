package com.siddhant.grocilist.ui.Wallet


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items

import com.siddhant.grocilist.domain.WalletState


@Composable
fun WalletScreen(
    viewModel: WalletViewModel = hiltViewModel()){
    val walletState by viewModel.walletState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadWallet()
    }
    when (walletState) {
        is WalletState.Loading -> CircularProgressIndicator()
        is WalletState.Success -> {
            val wallet = (walletState as WalletState.Success).wallet
            Column {
                Text("Balance: ₹${wallet.balance}")
                Button(onClick = { viewModel.topUp(100) }) {
                    Text("Top Up ₹100")
                }
                LazyColumn {
                    items(wallet.transactions) { transaction ->
                        Text("${transaction.type}: ₹${transaction.amount} - ${transaction.description}")
                    }
                }
            }
        }
        is WalletState.Error -> {
            Text((walletState as WalletState.Error).message)
        }
    }

}

