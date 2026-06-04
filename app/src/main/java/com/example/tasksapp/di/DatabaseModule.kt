package com.example.tasksapp.di

import android.content.Context
import androidx.room.Room
import com.example.tasksapp.data.database.TaskDao
import com.example.tasksapp.data.database.TaskDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(context: Context): TaskDataBase{
        return Room.databaseBuilder(
            context = context,
            klass = TaskDataBase::class.java,
            name= "tasks_db").build()
    }

    @Singleton
    @Provides
    fun provideDao(db: TaskDataBase): TaskDao{
        return db.taskDao()
    }
}