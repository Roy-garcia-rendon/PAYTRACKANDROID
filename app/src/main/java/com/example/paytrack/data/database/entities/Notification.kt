package com.example.paytrack.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "notifications",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val title: String,
    val message: String,
    val type: NotificationType,
    val isRead: Boolean = false,
    val createdAt: Date = Date()
)

enum class NotificationType {
    TRANSACTION,
    SECURITY,
    SYSTEM,
    PROMOTION
} 