package com.example.tasksapp.domain.models

import java.time.LocalDateTime
import java.util.Date

data class Task(
    val id : Int,
    val text: String,
    val creationDate: LocalDateTime,
    val lastUpdateDate: LocalDateTime,
    val deadline: LocalDateTime?,
    val completionDate: LocalDateTime?,
    val isDone: Boolean,
    val isPinned: Boolean
)
