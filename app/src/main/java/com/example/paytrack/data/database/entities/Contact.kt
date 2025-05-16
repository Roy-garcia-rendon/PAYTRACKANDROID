package com.example.paytrack.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "contacts",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val contactName: String,
    val contactEmail: String,
    val contactPhone: String,
    val createdAt: Date = Date()
) 