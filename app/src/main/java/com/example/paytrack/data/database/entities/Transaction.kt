package com.example.paytrack.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val amount: Double,
    val type: TransactionType,
    val description: String,
    val date: Date,
    val status: TransactionStatus,
    val createdAt: Date = Date()
)

enum class TransactionType {
    PAYMENT,
    TRANSFER,
    RECEIPT
}

enum class TransactionStatus {
    COMPLETED,
    PENDING,
    CANCELLED
} 