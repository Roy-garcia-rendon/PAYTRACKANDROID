package com.example.paytrack.data.repository

import com.example.paytrack.data.dao.TransactionDao
import com.example.paytrack.data.entities.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TransactionRepository(private val transactionDao: TransactionDao) {
    
    fun getTransactionsByUserId(userId: Long): Flow<List<Transaction>> = transactionDao.getTransactionsByUserId(userId)
        .flowOn(Dispatchers.IO)
    
    suspend fun getTransactionById(transactionId: Long): Transaction? = withContext(Dispatchers.IO) {
        transactionDao.getTransactionById(transactionId)
    }
    
    suspend fun insertTransaction(transaction: Transaction): Long = withContext(Dispatchers.IO) {
        transactionDao.insert(transaction)
    }
    
    suspend fun updateTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        transactionDao.update(transaction)
    }
    
    suspend fun deleteTransaction(transaction: Transaction) = withContext(Dispatchers.IO) {
        transactionDao.delete(transaction)
    }
} 