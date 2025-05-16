package com.example.paytrack.data.repositories

import com.example.paytrack.data.database.daos.ContactDao
import com.example.paytrack.data.database.entities.Contact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor(
    private val contactDao: ContactDao
) {
    fun getContactsByUser(userId: Long): Flow<List<Contact>> =
        contactDao.getContactsByUser(userId)

    fun getContactById(id: Long): Flow<Contact?> =
        contactDao.getContactById(id)

    fun getContactByEmail(email: String): Flow<Contact?> =
        contactDao.getContactByEmail(email)

    fun getContactByPhone(phone: String): Flow<Contact?> =
        contactDao.getContactByPhone(phone)

    suspend fun insertContact(contact: Contact): Long =
        contactDao.insertContact(contact)

    suspend fun updateContact(contact: Contact) =
        contactDao.updateContact(contact)

    suspend fun deleteContact(contact: Contact) =
        contactDao.deleteContact(contact)

    suspend fun updateContactName(contactId: Long, name: String) =
        contactDao.updateContactName(contactId, name)

    suspend fun updateContactEmail(contactId: Long, email: String) =
        contactDao.updateContactEmail(contactId, email)

    suspend fun updateContactPhone(contactId: Long, phone: String) =
        contactDao.updateContactPhone(contactId, phone)

    suspend fun updateContactStatus(contactId: Long, isActive: Boolean) =
        contactDao.updateContactStatus(contactId, isActive)
} 