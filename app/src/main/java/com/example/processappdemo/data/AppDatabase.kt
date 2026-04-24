package com.example.processappdemo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.processappdemo.data.dao.AuthDao
import com.example.processappdemo.data.dao.TestDao
import com.example.processappdemo.data.dao.UserDao
import com.example.processappdemo.data.model.AuthEntity
import com.example.processappdemo.data.model.TestEntity
import com.example.processappdemo.data.model.UserEntity

@Database(entities = [
    TestEntity::class,
    AuthEntity::class,
    UserEntity::class
                     ], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun iTestDao(): TestDao
    abstract fun iUserDao(): UserDao
    abstract fun iAuthDao(): AuthDao

    companion object{
        private const val DB_NAME = "factory_database"
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }

        fun isDatabaseConfigured(context: Context): Boolean{
            val db = context.getDatabasePath(DB_NAME)
            return db.exists()
        }

        fun iniMigration(context: Context){
            val db = getDatabase(context)
            val sqLiteDb: SupportSQLiteDatabase = db.openHelper.writableDatabase

            sqLiteDb.beginTransaction()
            try {
                sqLiteDb.execSQL("DROP TABLE IF EXISTS `test`")
                sqLiteDb.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `test` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `name` TEXT NOT NULL,
                        `description` TEXT NOT NULL
                    )
                    """.trimIndent()
                )

                sqLiteDb.setTransactionSuccessful()
            }finally {
                sqLiteDb.endTransaction()
            }
        }

    }
}