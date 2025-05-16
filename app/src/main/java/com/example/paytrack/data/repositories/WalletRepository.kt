package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.WalletDao
import com.example.paytrack.data.database.entities.Wallet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository @Inject constructor(
    private val walletDao: WalletDao
) {
    fun getWalletsByUser(userId: Long): Flow<List<Wallet>> =
        walletDao.getWalletsByUser(userId)

    suspend fun getWalletById(walletId: Long): Wallet? =
        walletDao.getWalletById(walletId)

    suspend fun insertWallet(wallet: Wallet): Long =
        walletDao.insertWallet(wallet)

    suspend fun updateWallet(wallet: Wallet) =
        walletDao.updateWallet(wallet)

    suspend fun deleteWallet(wallet: Wallet) =
        walletDao.deleteWallet(wallet)

    suspend fun updateBalance(walletId: Long, amount: Double) =
        walletDao.updateBalance(walletId, amount)
} 