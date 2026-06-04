package com.example.tasksapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey
    val id: Int,
    val text: String,
    val creationDate: Long,
    val lastUpdateDate: Long?,
    val deadline: Long?,
    val completionDate: Long?,
    val isDone: Boolean,
    val isPinned: Boolean
)
