package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.QRCodeDao
import com.example.paytrack.data.database.entities.QRCode
import com.example.paytrack.data.database.entities.QRCodeType
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QRCodeRepository @Inject constructor(
    private val qrCodeDao: QRCodeDao
) {
    fun getQRCodesByUser(userId: Long): Flow<List<QRCode>> =
        qrCodeDao.getQRCodesByUser(userId)

    suspend fun getQRCodeById(qrCodeId: Long): QRCode? =
        qrCodeDao.getQRCodeById(qrCodeId)

    fun getQRCodesByType(userId: Long, type: QRCodeType): Flow<List<QRCode>> =
        qrCodeDao.getQRCodesByType(userId, type)

    fun getActiveQRCodes(currentDate: Date): Flow<List<QRCode>> =
        qrCodeDao.getActiveQRCodes(currentDate)

    suspend fun insertQRCode(qrCode: QRCode): Long =
        qrCodeDao.insertQRCode(qrCode)

    suspend fun updateQRCode(qrCode: QRCode) =
        qrCodeDao.updateQRCode(qrCode)

    suspend fun deleteQRCode(qrCode: QRCode) =
        qrCodeDao.deleteQRCode(qrCode)

    suspend fun deleteExpiredQRCodes(currentDate: Date) =
        qrCodeDao.deleteExpiredQRCodes(currentDate)
} 