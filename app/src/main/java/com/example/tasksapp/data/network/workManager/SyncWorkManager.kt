package com.example.tasksapp.data.network.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tasksapp.data.repository.TasksRepositoryImpl

class SyncTasksWorker(
    appContext: Context,
    params: WorkerParameters,
    private val repository: TasksRepositoryImpl
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            repository.syncWithServer()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}