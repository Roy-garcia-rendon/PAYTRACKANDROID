package com.example.paytrack.data.database.daos

import androidx.room.*
import com.example.paytrack.data.database.entities.TransactionCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionCategoryDao {
    @Query("SELECT * FROM transaction_categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<TransactionCategory>>

    @Query("SELECT * FROM transaction_categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): TransactionCategory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: TransactionCategory): Long

    @Update
    suspend fun updateCategory(category: TransactionCategory)

    @Delete
    suspend fun deleteCategory(category: TransactionCategory)

    @Query("SELECT * FROM transaction_categories WHERE name LIKE '%' || :query || '%'")
    fun searchCategories(query: String): Flow<List<TransactionCategory>>
} 