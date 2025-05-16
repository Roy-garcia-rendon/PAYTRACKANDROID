package com.example.paytrack.data.database.daos

import androidx.room.*
import com.example.paytrack.data.database.entities.PaymentMethod
import com.example.paytrack.data.database.entities.PaymentMethodType
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentMethodDao {
    @Query("SELECT * FROM payment_methods WHERE userId = :userId ORDER BY isDefault DESC")
    fun getPaymentMethodsByUser(userId: Long): Flow<List<PaymentMethod>>

    @Query("SELECT * FROM payment_methods WHERE id = :paymentMethodId")
    suspend fun getPaymentMethodById(paymentMethodId: Long): PaymentMethod?

    @Query("SELECT * FROM payment_methods WHERE userId = :userId AND type = :type")
    fun getPaymentMethodsByType(userId: Long, type: PaymentMethodType): Flow<List<PaymentMethod>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentMethod(paymentMethod: PaymentMethod): Long

    @Update
    suspend fun updatePaymentMethod(paymentMethod: PaymentMethod)

    @Delete
    suspend fun deletePaymentMethod(paymentMethod: PaymentMethod)

    @Query("UPDATE payment_methods SET isDefault = 0 WHERE userId = :userId")
    suspend fun clearDefaultPaymentMethod(userId: Long)

    @Query("UPDATE payment_methods SET isDefault = 1 WHERE id = :paymentMethodId")
    suspend fun setDefaultPaymentMethod(paymentMethodId: Long)
} 