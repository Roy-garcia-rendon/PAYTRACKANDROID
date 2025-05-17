package com.example.paytrack.data.dao

import androidx.room.*
import com.example.paytrack.data.entities.QRCode
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface QRCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(qrCode: QRCode): Long

    @Update
    suspend fun update(qrCode: QRCode)

    @Delete
    suspend fun delete(qrCode: QRCode)

    @Query("SELECT * FROM qr_codes WHERE id = :qrCodeId")
    fun getQRCodeById(qrCodeId: Long): Flow<QRCode?>

    @Query("SELECT * FROM qr_codes WHERE userId = :userId")
    fun getQRCodesByUserId(userId: Long): Flow<List<QRCode>>

    @Query("SELECT * FROM qr_codes WHERE userId = :userId AND type = :type")
    fun getQRCodesByType(userId: Long, type: String): Flow<List<QRCode>>

    @Query("SELECT * FROM qr_codes WHERE expiresAt < :currentDate")
    fun getExpiredQRCodes(currentDate: Date): Flow<List<QRCode>>
} 