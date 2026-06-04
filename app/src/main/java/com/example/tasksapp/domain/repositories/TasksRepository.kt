package com.example.tasksapp.domain.repositories

import com.example.tasksapp.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    val items: Flow<List<Task>>

    val pinnedItems: Flow<List<Task>>

    val unpinnedItems: Flow<List<Task>>

    val completedItems: Flow<List<Task>>

    suspend fun syncFromServer()

    suspend fun syncWithServer()

    suspend fun getTasksList()

    suspend fun addTaskItem(task: Task)

    suspend fun updateTaskItem(task: Task)

    suspend fun markCompleted(id: Int)

    suspend fun deleteTaskItem(id: Int)

    suspend fun deleteSelectedTasks(taskIds: Set<Int>)

    suspend fun getTaskById(id: Int): Task

    suspend fun pinTask(id: Int)

    suspend fun deleteTaskList(tasks: List<Task>)
}