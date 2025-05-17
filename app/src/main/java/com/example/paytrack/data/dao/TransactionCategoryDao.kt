package com.example.paytrack.data.dao

import androidx.room.*
import com.example.paytrack.data.entities.TransactionCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: TransactionCategory): Long

    @Update
    suspend fun update(category: TransactionCategory)

    @Delete
    suspend fun delete(category: TransactionCategory)

    @Query("SELECT * FROM transaction_categories WHERE id = :categoryId")
    fun getCategoryById(categoryId: Long): Flow<TransactionCategory?>

    @Query("SELECT * FROM transaction_categories")
    fun getAllCategories(): Flow<List<TransactionCategory>>

    @Query("SELECT * FROM transaction_categories WHERE name LIKE '%' || :query || '%'")
    fun searchCategories(query: String): Flow<List<TransactionCategory>>
} 