package com.example.processappdemo.data.converter

import androidx.room.TypeConverter
import com.example.processappdemo.data.model.UserRoles

class Converter {
    @TypeConverter
    fun fromRole(role: UserRoles): String{
        return role.name
    }

    @TypeConverter
    fun toRole(value: String): UserRoles{
        return UserRoles.valueOf(value)
    }
}