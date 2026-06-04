package com.example.tasksapp.presenation.allTasks.state

data class SelectionState(
    val isInSelectionMode: Boolean=false,
    val selectedTasks: Set<Int> = emptySet()
)
