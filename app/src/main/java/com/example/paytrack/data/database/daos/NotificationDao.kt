package com.example.paytrack.data.database.daos

import androidx.room.*
import com.example.paytrack.data.database.entities.Notification
import com.example.paytrack.data.database.entities.NotificationType
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY createdAt DESC")
    fun getNotificationsByUser(userId: Long): Flow<List<Notification>>

    @Query("SELECT * FROM notifications WHERE id = :notificationId")
    suspend fun getNotificationById(notificationId: Long): Notification?

    @Query("SELECT * FROM notifications WHERE userId = :userId AND type = :type ORDER BY createdAt DESC")
    fun getNotificationsByType(userId: Long, type: NotificationType): Flow<List<Notification>>

    @Query("SELECT * FROM notifications WHERE userId = :userId AND isRead = :isRead ORDER BY createdAt DESC")
    fun getNotificationsByReadStatus(userId: Long, isRead: Boolean): Flow<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification): Long

    @Update
    suspend fun updateNotification(notification: Notification)

    @Delete
    suspend fun deleteNotification(notification: Notification)

    @Query("UPDATE notifications SET isRead = :isRead WHERE id = :notificationId")
    suspend fun updateReadStatus(notificationId: Long, isRead: Boolean)

    @Query("SELECT COUNT(*) FROM notifications WHERE userId = :userId AND isRead = 0")
    suspend fun getUnreadNotificationCount(userId: Long): Int
} 