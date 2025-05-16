package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.TransactionCategoryDao
import com.example.paytrack.data.database.entities.TransactionCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionCategoryRepository @Inject constructor(
    private val transactionCategoryDao: TransactionCategoryDao
) {
    fun getAllCategories(): Flow<List<TransactionCategory>> =
        transactionCategoryDao.getAllCategories()

    suspend fun getCategoryById(categoryId: Long): TransactionCategory? =
        transactionCategoryDao.getCategoryById(categoryId)

    suspend fun insertCategory(category: TransactionCategory): Long =
        transactionCategoryDao.insertCategory(category)

    suspend fun updateCategory(category: TransactionCategory) =
        transactionCategoryDao.updateCategory(category)

    suspend fun deleteCategory(category: TransactionCategory) =
        transactionCategoryDao.deleteCategory(category)

    fun searchCategories(query: String): Flow<List<TransactionCategory>> =
        transactionCategoryDao.searchCategories(query)
} 