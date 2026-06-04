package com.example.tasksapp.presenation.allTasks.state
import com.example.tasksapp.domain.models.Task


data class TasksState(
    val pinnedItems: List<Task> = emptyList(),
    val unpinnedItems: List<Task> = emptyList(),
    val completedItems: List<Task> = emptyList(),
    val selectionState : SelectionState= SelectionState(),
    val showCompleted: Boolean = false,
)
