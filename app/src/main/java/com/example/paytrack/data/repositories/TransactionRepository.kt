package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.TransactionDao
import com.example.paytrack.data.database.entities.Transaction
import com.example.paytrack.data.database.entities.TransactionStatus
import com.example.paytrack.data.database.entities.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    fun getTransactionsByUser(userId: Long): Flow<List<Transaction>> =
        transactionDao.getTransactionsByUser(userId)

    fun getTransactionById(id: Long): Flow<Transaction?> =
        transactionDao.getTransactionById(id)

    fun getTransactionsByWallet(walletId: Long): Flow<List<Transaction>> =
        transactionDao.getTransactionsByWallet(walletId)

    fun getTransactionsByDateRange(userId: Long, startDate: Date, endDate: Date): Flow<List<Transaction>> =
        transactionDao.getTransactionsByDateRange(userId, startDate, endDate)

    fun getTransactionsByType(userId: Long, type: String): Flow<List<Transaction>> =
        transactionDao.getTransactionsByType(userId, type)

    suspend fun insertTransaction(transaction: Transaction): Long =
        transactionDao.insertTransaction(transaction)

    suspend fun updateTransaction(transaction: Transaction) =
        transactionDao.updateTransaction(transaction)

    suspend fun deleteTransaction(transaction: Transaction) =
        transactionDao.deleteTransaction(transaction)

    suspend fun updateTransactionStatus(transactionId: Long, status: String) =
        transactionDao.updateTransactionStatus(transactionId, status)

    suspend fun updateTransactionAmount(transactionId: Long, amount: Double) =
        transactionDao.updateTransactionAmount(transactionId, amount)

    suspend fun updateTransactionDate(transactionId: Long, date: Date) =
        transactionDao.updateTransactionDate(transactionId, date)

    fun getTransactionsByStatus(userId: Long, status: TransactionStatus): Flow<List<Transaction>> =
        transactionDao.getTransactionsByStatus(userId, status)

    suspend fun getTotalAmountByTypeAndStatus(
        userId: Long,
        type: TransactionType,
        status: TransactionStatus
    ): Double? = transactionDao.getTotalAmountByTypeAndStatus(userId, type, status)
} 