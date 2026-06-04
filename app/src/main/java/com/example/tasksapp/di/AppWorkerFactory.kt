package com.example.tasksapp.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.tasksapp.data.network.workManager.SyncTasksWorker
import com.example.tasksapp.data.repository.TasksRepositoryImpl
import javax.inject.Inject

class AppWorkerFactory @Inject constructor(
    private val syncTasksWorkerFactory: SyncTasksWorkerFactory
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {

            SyncTasksWorker::class.java.name ->
                syncTasksWorkerFactory.create(
                    appContext,
                    workerParameters
                )

            else -> null
        }
    }
}

class SyncTasksWorkerFactory @Inject constructor(
    private val repository: TasksRepositoryImpl
) {

    fun create(
        appContext: Context,
        params: WorkerParameters
    ): SyncTasksWorker {
        return SyncTasksWorker(
            appContext,
            params,
            repository
        )
    }
}