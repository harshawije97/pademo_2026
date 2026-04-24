package com.example.processappdemo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.processappdemo.data.model.TestEntity

@Dao
interface ITestDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TestEntity)

    @Query("SELECT * FROM test")
    suspend fun getAll(): List<TestEntity>

    @Delete
    suspend fun delete(item: TestEntity)
}