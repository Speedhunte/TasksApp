package com.example.tasksapp.presenation.taskDetails.viewmodel

import java.time.LocalDateTime

data class TaskDetailsState(
    val text: String = "",
    val deadline: LocalDateTime? = null,
    val isCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val dialogState: DialogState = DialogState.None
)