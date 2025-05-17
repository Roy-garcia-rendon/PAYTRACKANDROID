package com.example.paytrack.data.dao

import androidx.room.*
import com.example.paytrack.data.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact): Long

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    fun getContactById(contactId: Long): Flow<Contact?>

    @Query("SELECT * FROM contacts WHERE userId = :userId")
    fun getContactsByUserId(userId: Long): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE userId = :userId AND (name LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%')")
    fun searchContacts(userId: Long, query: String): Flow<List<Contact>>
} 