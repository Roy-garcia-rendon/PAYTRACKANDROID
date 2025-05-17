package com.example.paytrack.data.dao

import androidx.room.*
import com.example.paytrack.data.entities.Transaction
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE id = :transactionId")
    fun getTransactionById(transactionId: Long): Flow<Transaction?>

    @Query("SELECT * FROM transactions WHERE userId = :userId")
    fun getTransactionsByUserId(userId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND type = :type")
    fun getTransactionsByType(userId: Long, type: String): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND transactionDate BETWEEN :startDate AND :endDate")
    fun getTransactionsByDateRange(userId: Long, startDate: Date, endDate: Date): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND categoryId = :categoryId")
    fun getTransactionsByCategory(userId: Long, categoryId: Long): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE userId = :userId AND status = :status")
    fun getTransactionsByStatus(userId: Long, status: String): Flow<List<Transaction>>
} 