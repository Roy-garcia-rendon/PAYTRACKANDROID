package com.example.paytrack.data.database.daos

import androidx.room.*
import com.example.paytrack.data.database.entities.SecuritySettings
import kotlinx.coroutines.flow.Flow

@Dao
interface SecuritySettingsDao {
    @Query("SELECT * FROM security_settings WHERE userId = :userId")
    fun getSecuritySettingsByUser(userId: Long): Flow<SecuritySettings?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSecuritySettings(securitySettings: SecuritySettings): Long

    @Update
    suspend fun updateSecuritySettings(securitySettings: SecuritySettings)

    @Query("UPDATE security_settings SET twoFactorEnabled = :enabled WHERE userId = :userId")
    suspend fun updateTwoFactorEnabled(userId: Long, enabled: Boolean)

    @Query("UPDATE security_settings SET biometricEnabled = :enabled WHERE userId = :userId")
    suspend fun updateBiometricEnabled(userId: Long, enabled: Boolean)

    @Query("UPDATE security_settings SET lastPasswordChange = :date WHERE userId = :userId")
    suspend fun updateLastPasswordChange(userId: Long, date: java.util.Date)
} 