package com.example.paytrack.data.database.daos

import androidx.room.*
import com.example.paytrack.data.database.entities.QRCode
import com.example.paytrack.data.database.entities.QRCodeType
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface QRCodeDao {
    @Query("SELECT * FROM qr_codes WHERE userId = :userId ORDER BY createdAt DESC")
    fun getQRCodesByUser(userId: Long): Flow<List<QRCode>>

    @Query("SELECT * FROM qr_codes WHERE id = :qrCodeId")
    suspend fun getQRCodeById(qrCodeId: Long): QRCode?

    @Query("SELECT * FROM qr_codes WHERE userId = :userId AND type = :type ORDER BY createdAt DESC")
    fun getQRCodesByType(userId: Long, type: QRCodeType): Flow<List<QRCode>>

    @Query("SELECT * FROM qr_codes WHERE expiresAt > :currentDate")
    fun getActiveQRCodes(currentDate: Date): Flow<List<QRCode>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQRCode(qrCode: QRCode): Long

    @Update
    suspend fun updateQRCode(qrCode: QRCode)

    @Delete
    suspend fun deleteQRCode(qrCode: QRCode)

    @Query("DELETE FROM qr_codes WHERE expiresAt <= :currentDate")
    suspend fun deleteExpiredQRCodes(currentDate: Date)
} 