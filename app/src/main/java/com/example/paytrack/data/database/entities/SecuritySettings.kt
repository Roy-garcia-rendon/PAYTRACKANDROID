package com.example.paytrack.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "security_settings",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SecuritySettings(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val twoFactorEnabled: Boolean = false,
    val biometricEnabled: Boolean = false,
    val lastPasswordChange: Date = Date(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) 