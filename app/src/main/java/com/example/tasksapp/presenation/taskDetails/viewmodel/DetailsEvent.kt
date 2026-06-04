package com.example.tasksapp.presenation.taskDetails.viewmodel

sealed interface TaskDetailsEvent {
    data object Saved : TaskDetailsEvent
}