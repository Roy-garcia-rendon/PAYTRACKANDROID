package com.example.paytrack.data.dao

import androidx.room.*
import com.example.paytrack.data.entities.SecuritySettings
import kotlinx.coroutines.flow.Flow

@Dao
interface SecuritySettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: SecuritySettings): Long

    @Update
    suspend fun update(settings: SecuritySettings)

    @Delete
    suspend fun delete(settings: SecuritySettings)

    @Query("SELECT * FROM security_settings WHERE id = :settingsId")
    fun getSettingsById(settingsId: Long): Flow<SecuritySettings?>

    @Query("SELECT * FROM security_settings WHERE userId = :userId")
    fun getSettingsByUserId(userId: Long): Flow<SecuritySettings?>

    @Query("UPDATE security_settings SET twoFactorEnabled = :enabled WHERE userId = :userId")
    suspend fun updateTwoFactorStatus(userId: Long, enabled: Boolean)

    @Query("UPDATE security_settings SET biometricEnabled = :enabled WHERE userId = :userId")
    suspend fun updateBiometricStatus(userId: Long, enabled: Boolean)
} 