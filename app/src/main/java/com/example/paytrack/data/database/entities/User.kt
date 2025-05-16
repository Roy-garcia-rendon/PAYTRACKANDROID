package com.example.paytrack.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    val profileImageUrl: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) 