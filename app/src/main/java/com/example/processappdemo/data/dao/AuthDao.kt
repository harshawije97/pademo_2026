package com.example.processappdemo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.processappdemo.data.model.AuthEntity

@Dao
interface AuthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(auth: AuthEntity)

    @Query("SELECT * FROM auth WHERE email = :emailAddr")
    suspend fun getByEmail(emailAddr: String): AuthEntity?

    @Update
    suspend fun update(auth: AuthEntity)

    @Delete
    suspend fun delete(auth: AuthEntity)
}