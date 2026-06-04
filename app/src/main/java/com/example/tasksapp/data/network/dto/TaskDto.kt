package com.example.tasksapp.data.network.dto

import kotlinx.serialization.SerialName
import java.time.LocalDateTime
import java.util.Date

data class TaskDto(
    @SerialName("id") val id : Int,
    @SerialName("text") val text: String,
    @SerialName("creation_date") val creationDate: Long,
    @SerialName("last_update_date") val lastUpdateDate: Long,
    @SerialName("deadline") val deadline: Long?,
    @SerialName("completion_date")val completionDate: Long?,
    @SerialName("is_done") val isDone: Boolean,
    @SerialName("is_pinned")val isPinned: Boolean
)
