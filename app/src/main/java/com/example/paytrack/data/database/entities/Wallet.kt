package com.example.paytrack.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "wallets",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Wallet(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val balance: Double,
    val currency: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) 