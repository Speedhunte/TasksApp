package com.example.tasksapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [TaskEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class TaskDataBase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}