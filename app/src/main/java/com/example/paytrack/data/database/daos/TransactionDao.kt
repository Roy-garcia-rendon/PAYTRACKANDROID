package com.example.paytrack.data.database.daos

import androidx.room.*
import com.example.paytrack.data.database.entities.Transaction
import com.example.paytrack.data.database.entities.TransactionStatus
import com.example.paytrack.data.database.entities.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY date DESC")
    fun getTransactionsByUser(userId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    suspend fun getTransactionById(transactionId: Long): Transaction?

    @Query("SELECT * FROM transactions WHERE userId = :userId AND type = :type ORDER BY date DESC")
    fun getTransactionsByType(userId: Long, type: TransactionType): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND status = :status ORDER BY date DESC")
    fun getTransactionsByStatus(userId: Long, status: TransactionStatus): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getTransactionsByDateRange(userId: Long, startDate: Date, endDate: Date): Flow<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction): Long

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT SUM(amount) FROM transactions WHERE userId = :userId AND type = :type AND status = :status")
    suspend fun getTotalAmountByTypeAndStatus(userId: Long, type: TransactionType, status: TransactionStatus): Double?
} 