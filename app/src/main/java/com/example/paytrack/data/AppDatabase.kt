package com.example.paytrack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.paytrack.data.dao.*
import com.example.paytrack.data.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        User::class,
        Wallet::class,
        Transaction::class,
        TransactionCategory::class,
        Contact::class,
        QRCode::class,
        Notification::class,
        SecuritySettings::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun walletDao(): WalletDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transactionCategoryDao(): TransactionCategoryDao
    abstract fun contactDao(): ContactDao
    abstract fun qrCodeDao(): QRCodeDao
    abstract fun notificationDao(): NotificationDao
    abstract fun securitySettingsDao(): SecuritySettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "paytrack_database"
                )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val userDao = getDatabase(context).userDao()
                            // Insert initial user
                            val initialUser = User(
                                name = "Rodrigo Aner Garcia Rendon",
                                email = "rodrigoa.gr11@gmail.com",
                                password = "2007rox345rt",
                                profileImage = null,
                                createdAt = System.currentTimeMillis(),
                                updatedAt = System.currentTimeMillis()
                            )
                            userDao.insert(initialUser)
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 