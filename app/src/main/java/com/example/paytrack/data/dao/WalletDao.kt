package com.example.paytrack.data.dao

import androidx.room.*
import com.example.paytrack.data.entities.Wallet
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallets ORDER BY updatedAt DESC")
    suspend fun getAllWallets(): List<Wallet>

    @Query("SELECT * FROM wallets WHERE id = :walletId")
    suspend fun getWalletById(walletId: Long): Wallet?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wallet: Wallet): Long

    @Update
    suspend fun update(wallet: Wallet)

    @Delete
    suspend fun delete(wallet: Wallet)

    @Query("SELECT * FROM wallets WHERE userId = :userId")
    fun getWalletsByUserId(userId: Long): Flow<List<Wallet>>
} 