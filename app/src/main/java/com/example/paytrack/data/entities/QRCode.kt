package com.example.paytrack.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "qr_codes",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class QRCode(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val qrData: String,
    val type: String,
    val createdAt: Date,
    val expiresAt: Date?
) 