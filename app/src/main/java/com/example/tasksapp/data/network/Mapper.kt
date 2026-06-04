package com.example.tasksapp.data.network

import com.example.tasksapp.data.database.TaskEntity
import com.example.tasksapp.data.network.dto.TaskDto
import com.example.tasksapp.data.utils.toEpochMillis
import com.example.tasksapp.data.utils.toLocalDateTime
import com.example.tasksapp.domain.models.Task
import java.util.Date


fun TaskDto.toDomain(): Task= Task(
    id = this.id,
    text = this.text,
    creationDate = this.creationDate.toLocalDateTime(),
    lastUpdateDate =this.creationDate.toLocalDateTime(),
    deadline = this.creationDate.toLocalDateTime(),
    completionDate = this.creationDate.toLocalDateTime(),
    isDone = this.isDone,
    isPinned = this.isPinned
)

fun Task.toDto(): TaskDto = TaskDto(
    id = id,
    text = text,
    creationDate =creationDate.toEpochMillis() ,
    lastUpdateDate = lastUpdateDate.toEpochMillis(),
    deadline = deadline?.toEpochMillis(),
    completionDate = completionDate?.toEpochMillis(),
    isDone = isDone,
    isPinned = isPinned
)

fun TaskDto.toEntity(): TaskEntity = TaskEntity(
    id = id,
    text = text,
    creationDate = creationDate,
    lastUpdateDate = lastUpdateDate,
    deadline = deadline,
    completionDate = completionDate,
    isDone = isDone,
    isPinned = isPinned
)

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    text = text,
    creationDate = creationDate.toLocalDateTime(),
    lastUpdateDate = lastUpdateDate?.toLocalDateTime()?:creationDate.toLocalDateTime(),
    deadline = deadline?.toLocalDateTime(),
    completionDate = completionDate?.toLocalDateTime(),
    isDone = isDone,
    isPinned = isPinned
)
