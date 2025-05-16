package com.example.paytrack.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction_categories")
data class TransactionCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val icon: String,
    val color: String,
    val createdAt: Date = Date()
) 