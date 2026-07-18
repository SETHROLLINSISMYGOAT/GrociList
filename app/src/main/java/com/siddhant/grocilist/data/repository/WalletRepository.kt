package com.siddhant.grocilist.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.siddhant.grocilist.data.model.Wallet
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WalletRepository @Inject constructor(){
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun getWallet(userId: String): Wallet {
        val snapshot = firestore.collection("wallets")
            .document(userId)
            .get()
            .await()
        return snapshot.toObject(Wallet::class.java) ?: Wallet(userId = userId)
    }
    suspend fun topUpWallet(userId: String, amount: Int) {
        val walletRef = firestore.collection("wallets").document(userId)
        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(walletRef)
            val currentBalance = snapshot.getLong("balance")?.toInt() ?: 0
            val newBalance = currentBalance + amount
            transaction.update(walletRef, "balance",newBalance)
        }.await()
    }
    suspend fun deductFromWallet(userId: String, amount: Int) {
        val walletRef = firestore.collection("wallets").document(userId)
        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(walletRef)
            val currentBalance = snapshot.getLong("balance")?.toInt() ?: 0
            if (currentBalance < amount) {
                throw Exception("Insufficient balance")
            }
            val newBalance = currentBalance - amount
            transaction.update(walletRef, "balance", newBalance)
        }.await()
    }
}