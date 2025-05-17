package com.example.paytrack.data.repository

import com.example.paytrack.data.dao.WalletDao
import com.example.paytrack.data.entities.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class WalletRepository(private val walletDao: WalletDao) {
    
    fun getWalletsByUserId(userId: Long): Flow<List<Wallet>> = walletDao.getWalletsByUserId(userId)
        .flowOn(Dispatchers.IO)
    
    suspend fun getWalletById(walletId: Long): Wallet? = withContext(Dispatchers.IO) {
        walletDao.getWalletById(walletId)
    }
    
    suspend fun insertWallet(wallet: Wallet): Long = withContext(Dispatchers.IO) {
        walletDao.insert(wallet)
    }
    
    suspend fun updateWallet(wallet: Wallet) = withContext(Dispatchers.IO) {
        walletDao.update(wallet)
    }
    
    suspend fun deleteWallet(wallet: Wallet) = withContext(Dispatchers.IO) {
        walletDao.delete(wallet)
    }
} 