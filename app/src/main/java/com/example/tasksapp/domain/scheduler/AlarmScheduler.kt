package com.example.tasksapp.domain.scheduler

interface AlarmScheduler {

    fun schedule(
        taskId: Int,
        title: String,
        triggerAtMillis: Long
    )

    fun cancel(taskId: Int)
}