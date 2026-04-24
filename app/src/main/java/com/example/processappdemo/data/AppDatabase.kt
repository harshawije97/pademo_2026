package com.example.processappdemo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.processappdemo.data.dao.ITestDAO
import com.example.processappdemo.data.model.TestEntity

@Database(entities = [TestEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun iTestDAO(): ITestDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "factory_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}