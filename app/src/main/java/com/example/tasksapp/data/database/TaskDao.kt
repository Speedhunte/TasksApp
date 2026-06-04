package com.example.tasksapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
     fun getTaskList(): Flow<List<TaskEntity>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTaskList(tasks: List<TaskEntity>)

    @Query("SELECT * FROM task WHERE id =:id")
    suspend fun getTaskById(id: Int): TaskEntity

    @Delete
    suspend fun deleteTask (task: TaskEntity)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTask(task: TaskEntity)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTasks(tasks: List<TaskEntity>)


    @Query("DELETE FROM task")
    suspend fun deleteAllTasks()

}