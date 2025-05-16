package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.TransactionCategoryMappingDao
import com.example.paytrack.data.database.entities.TransactionCategoryMapping
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionCategoryMappingRepository @Inject constructor(
    private val transactionCategoryMappingDao: TransactionCategoryMappingDao
) {
    fun getMappingsByTransaction(transactionId: Long): Flow<List<TransactionCategoryMapping>> =
        transactionCategoryMappingDao.getMappingsByTransaction(transactionId)

    fun getMappingsByCategory(categoryId: Long): Flow<List<TransactionCategoryMapping>> =
        transactionCategoryMappingDao.getMappingsByCategory(categoryId)

    suspend fun insertMapping(mapping: TransactionCategoryMapping): Long =
        transactionCategoryMappingDao.insertMapping(mapping)

    suspend fun updateMapping(mapping: TransactionCategoryMapping) =
        transactionCategoryMappingDao.updateMapping(mapping)

    suspend fun deleteMapping(mapping: TransactionCategoryMapping) =
        transactionCategoryMappingDao.deleteMapping(mapping)

    suspend fun deleteMappingsByTransaction(transactionId: Long) =
        transactionCategoryMappingDao.deleteMappingsByTransaction(transactionId)

    suspend fun deleteMappingsByCategory(categoryId: Long) =
        transactionCategoryMappingDao.deleteMappingsByCategory(categoryId)
} 