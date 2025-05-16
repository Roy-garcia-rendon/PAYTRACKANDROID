package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.NotificationDao
import com.example.paytrack.data.database.entities.Notification
import com.example.paytrack.data.database.entities.NotificationType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao
) {
    fun getNotificationsByUser(userId: Long): Flow<List<Notification>> =
        notificationDao.getNotificationsByUser(userId)

    suspend fun getNotificationById(notificationId: Long): Notification? =
        notificationDao.getNotificationById(notificationId)

    fun getNotificationsByType(userId: Long, type: NotificationType): Flow<List<Notification>> =
        notificationDao.getNotificationsByType(userId, type)

    fun getNotificationsByReadStatus(userId: Long, isRead: Boolean): Flow<List<Notification>> =
        notificationDao.getNotificationsByReadStatus(userId, isRead)

    suspend fun insertNotification(notification: Notification): Long =
        notificationDao.insertNotification(notification)

    suspend fun updateNotification(notification: Notification) =
        notificationDao.updateNotification(notification)

    suspend fun deleteNotification(notification: Notification) =
        notificationDao.deleteNotification(notification)

    suspend fun updateReadStatus(notificationId: Long, isRead: Boolean) =
        notificationDao.updateReadStatus(notificationId, isRead)

    suspend fun getUnreadNotificationCount(userId: Long): Int =
        notificationDao.getUnreadNotificationCount(userId)
} 