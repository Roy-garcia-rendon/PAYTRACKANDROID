package com.example.paytrack.data.dao

import androidx.room.*
import com.example.paytrack.data.entities.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: Notification): Long

    @Update
    suspend fun update(notification: Notification)

    @Delete
    suspend fun delete(notification: Notification)

    @Query("SELECT * FROM notifications WHERE id = :notificationId")
    fun getNotificationById(notificationId: Long): Flow<Notification?>

    @Query("SELECT * FROM notifications WHERE userId = :userId")
    fun getNotificationsByUserId(userId: Long): Flow<List<Notification>>

    @Query("SELECT * FROM notifications WHERE userId = :userId AND isRead = :isRead")
    fun getNotificationsByReadStatus(userId: Long, isRead: Boolean): Flow<List<Notification>>

    @Query("SELECT * FROM notifications WHERE userId = :userId AND type = :type")
    fun getNotificationsByType(userId: Long, type: String): Flow<List<Notification>>

    @Query("UPDATE notifications SET isRead = :isRead WHERE id = :notificationId")
    suspend fun updateReadStatus(notificationId: Long, isRead: Boolean)

    @Query("UPDATE notifications SET isRead = :isRead WHERE userId = :userId")
    suspend fun updateAllReadStatus(userId: Long, isRead: Boolean)
} 