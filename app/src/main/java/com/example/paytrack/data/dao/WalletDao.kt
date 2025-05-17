package com.example.paytrack.data.dao

import androidx.room.*
import com.example.paytrack.data.entities.Wallet
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wallet: Wallet): Long

    @Update
    suspend fun update(wallet: Wallet)

    @Delete
    suspend fun delete(wallet: Wallet)

    @Query("SELECT * FROM wallets WHERE id = :walletId")
    fun getWalletById(walletId: Long): Flow<Wallet?>

    @Query("SELECT * FROM wallets WHERE userId = :userId")
    fun getWalletsByUserId(userId: Long): Flow<List<Wallet>>

    @Query("SELECT * FROM wallets")
    fun getAllWallets(): Flow<List<Wallet>>
} 