package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.PaymentMethodDao
import com.example.paytrack.data.database.entities.PaymentMethod
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentMethodRepository @Inject constructor(
    private val paymentMethodDao: PaymentMethodDao
) {
    fun getPaymentMethodsByUser(userId: Long): Flow<List<PaymentMethod>> =
        paymentMethodDao.getPaymentMethodsByUser(userId)

    fun getPaymentMethodById(id: Long): Flow<PaymentMethod?> =
        paymentMethodDao.getPaymentMethodById(id)

    suspend fun insertPaymentMethod(paymentMethod: PaymentMethod): Long =
        paymentMethodDao.insertPaymentMethod(paymentMethod)

    suspend fun updatePaymentMethod(paymentMethod: PaymentMethod) =
        paymentMethodDao.updatePaymentMethod(paymentMethod)

    suspend fun deletePaymentMethod(paymentMethod: PaymentMethod) =
        paymentMethodDao.deletePaymentMethod(paymentMethod)

    suspend fun updateDefaultStatus(paymentMethodId: Long, isDefault: Boolean) =
        paymentMethodDao.updateDefaultStatus(paymentMethodId, isDefault)

    suspend fun clearDefaultStatus(userId: Long) =
        paymentMethodDao.clearDefaultStatus(userId)
} 