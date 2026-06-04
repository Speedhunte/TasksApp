package com.example.tasksapp.di

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.tasksapp.data.repository.TasksRepositoryImpl
import com.example.tasksapp.data.network.connection.ConnectionManager
import com.example.tasksapp.presenation.allTasks.viewmodel.AllTasksViewModel
import com.example.tasksapp.presenation.taskDetails.viewmodel.TaskDetailsViewModel
import jakarta.inject.Inject

class ViewModelFactory @Inject constructor(
    private val repository: TasksRepositoryImpl,
) {

    fun create(
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ): ViewModelProvider.Factory {

        return object : AbstractSavedStateViewModelFactory(
            owner,
            defaultArgs
        ) {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {

                return when {

                    modelClass.isAssignableFrom(
                        AllTasksViewModel::class.java
                    ) -> {
                        AllTasksViewModel(
                            repository,
                        ) as T
                    }

                    modelClass.isAssignableFrom(
                        TaskDetailsViewModel::class.java
                    ) -> {
                        TaskDetailsViewModel(
                            repository,
                            handle
                        ) as T
                    }

                    else -> throw IllegalArgumentException(
                        "Unknown ViewModel ${modelClass.name}"
                    )
                }
            }
        }
    }
}