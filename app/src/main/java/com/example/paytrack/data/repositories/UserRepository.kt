package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.UserDao
import com.example.paytrack.data.database.entities.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    fun getUserById(id: Long): Flow<User?> =
        userDao.getUserById(id)

    fun getUserByEmail(email: String): Flow<User?> =
        userDao.getUserByEmail(email)

    fun getUserByPhone(phone: String): Flow<User?> =
        userDao.getUserByPhone(phone)

    suspend fun insertUser(user: User): Long =
        userDao.insertUser(user)

    suspend fun updateUser(user: User) =
        userDao.updateUser(user)

    suspend fun deleteUser(user: User) =
        userDao.deleteUser(user)

    suspend fun updateUserProfile(
        userId: Long,
        name: String,
        email: String,
        phone: String
    ) = userDao.updateUserProfile(userId, name, email, phone)

    suspend fun updateUserPassword(userId: Long, hashedPassword: String) =
        userDao.updateUserPassword(userId, hashedPassword)

    suspend fun updateUserLastLogin(userId: Long) =
        userDao.updateUserLastLogin(userId)

    suspend fun updateUserStatus(userId: Long, isActive: Boolean) =
        userDao.updateUserStatus(userId, isActive)

    suspend fun login(email: String, password: String): User? = userDao.login(email, password)
} 