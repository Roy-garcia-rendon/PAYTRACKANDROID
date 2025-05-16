package com.example.paytrack.data.database.daos

import androidx.room.*
import com.example.paytrack.data.database.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts WHERE userId = :userId ORDER BY contactName ASC")
    fun getContactsByUser(userId: Long): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    suspend fun getContactById(contactId: Long): Contact?

    @Query("SELECT * FROM contacts WHERE userId = :userId AND (contactName LIKE '%' || :query || '%' OR contactEmail LIKE '%' || :query || '%' OR contactPhone LIKE '%' || :query || '%')")
    fun searchContacts(userId: Long, query: String): Flow<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)
} 