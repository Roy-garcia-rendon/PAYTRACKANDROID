package com.example.paytrack.data.database.daos

import androidx.room.*
import com.example.paytrack.data.database.entities.Wallet
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallets WHERE userId = :userId")
    fun getWalletsByUser(userId: Long): Flow<List<Wallet>>

    @Query("SELECT * FROM wallets WHERE id = :walletId")
    suspend fun getWalletById(walletId: Long): Wallet?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: Wallet): Long

    @Update
    suspend fun updateWallet(wallet: Wallet)

    @Delete
    suspend fun deleteWallet(wallet: Wallet)

    @Query("UPDATE wallets SET balance = balance + :amount WHERE id = :walletId")
    suspend fun updateBalance(walletId: Long, amount: Double)
} 