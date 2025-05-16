package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.SecuritySettingsDao
import com.example.paytrack.data.database.entities.SecuritySettings
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecuritySettingsRepository @Inject constructor(
    private val securitySettingsDao: SecuritySettingsDao
) {
    fun getSecuritySettingsByUser(userId: Long): Flow<SecuritySettings?> =
        securitySettingsDao.getSecuritySettingsByUser(userId)

    suspend fun insertSecuritySettings(securitySettings: SecuritySettings): Long =
        securitySettingsDao.insertSecuritySettings(securitySettings)

    suspend fun updateSecuritySettings(securitySettings: SecuritySettings) =
        securitySettingsDao.updateSecuritySettings(securitySettings)

    suspend fun updateTwoFactorEnabled(userId: Long, enabled: Boolean) =
        securitySettingsDao.updateTwoFactorEnabled(userId, enabled)

    suspend fun updateBiometricEnabled(userId: Long, enabled: Boolean) =
        securitySettingsDao.updateBiometricEnabled(userId, enabled)

    suspend fun updateLastPasswordChange(userId: Long, date: Date) =
        securitySettingsDao.updateLastPasswordChange(userId, date)
} 