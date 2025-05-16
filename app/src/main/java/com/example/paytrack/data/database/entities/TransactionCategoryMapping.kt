package com.example.paytrack.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "transaction_category_mappings",
    foreignKeys = [
        ForeignKey(
            entity = Transaction::class,
            parentColumns = ["id"],
            childColumns = ["transactionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TransactionCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransactionCategoryMapping(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val transactionId: Long,
    val categoryId: Long,
    val createdAt: Date = Date()
) 