package com.example.paytrack.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "payment_methods",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PaymentMethod(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val type: PaymentMethodType,
    val lastFourDigits: String,
    val isDefault: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class PaymentMethodType {
    CREDIT_CARD,
    DEBIT_CARD,
    BANK_ACCOUNT
} 