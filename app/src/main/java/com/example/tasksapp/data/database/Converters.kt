package com.example.tasksapp.data.database

import androidx.room.TypeConverter
import com.example.tasksapp.data.utils.toEpochMillis
import com.example.tasksapp.data.utils.toLocalDateTime
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.toLocalDateTime()
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toEpochMillis()
    }
}