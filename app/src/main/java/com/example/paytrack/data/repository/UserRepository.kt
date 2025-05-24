package com.example.paytrack.data.repository

import com.example.paytrack.data.dao.UserDao
import com.example.paytrack.data.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {
    
    suspend fun getUserByEmail(email: String): User? = withContext(Dispatchers.IO) {
        userDao.getUserByEmail(email)
    }
    
    suspend fun getUserById(userId: Long): User? = withContext(Dispatchers.IO) {
        userDao.getUserByIdSuspend(userId)
    }
    
    suspend fun insertUser(user: User): Long = withContext(Dispatchers.IO) {
        userDao.insert(user)
    }
    
    suspend fun updateUser(user: User) = withContext(Dispatchers.IO) {
        userDao.update(user)
    }
    
    suspend fun validateCredentials(email: String, password: String): User? = withContext(Dispatchers.IO) {
        val user = userDao.getUserByEmail(email)
        if (user != null && user.password == password) user else null
    }
} 