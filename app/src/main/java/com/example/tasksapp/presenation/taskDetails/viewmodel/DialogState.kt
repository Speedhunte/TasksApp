package com.example.tasksapp.presenation.taskDetails.viewmodel

sealed interface DialogState {
    data object None : DialogState
    data object DatePicker : DialogState
    data class Error(
        val message: String
    ) : DialogState
}