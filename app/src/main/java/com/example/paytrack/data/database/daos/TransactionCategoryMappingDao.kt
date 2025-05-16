package com.example.paytrack.data.database.daos

import androidx.room.*
import com.example.paytrack.data.database.entities.TransactionCategoryMapping
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionCategoryMappingDao {
    @Query("SELECT * FROM transaction_category_mappings WHERE transactionId = :transactionId")
    fun getMappingsByTransaction(transactionId: Long): Flow<List<TransactionCategoryMapping>>

    @Query("SELECT * FROM transaction_category_mappings WHERE categoryId = :categoryId")
    fun getMappingsByCategory(categoryId: Long): Flow<List<TransactionCategoryMapping>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMapping(mapping: TransactionCategoryMapping): Long

    @Update
    suspend fun updateMapping(mapping: TransactionCategoryMapping)

    @Delete
    suspend fun deleteMapping(mapping: TransactionCategoryMapping)

    @Query("DELETE FROM transaction_category_mappings WHERE transactionId = :transactionId")
    suspend fun deleteMappingsByTransaction(transactionId: Long)

    @Query("DELETE FROM transaction_category_mappings WHERE categoryId = :categoryId")
    suspend fun deleteMappingsByCategory(categoryId: Long)
} 