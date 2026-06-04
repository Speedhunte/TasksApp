package com.example.tasksapp.data.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TaskDeadlineReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

        val taskId =
            intent.getIntExtra(EXTRA_TASK_ID, -1)

        val title =
            intent.getStringExtra(EXTRA_TASK_TITLE)
                ?: "Task deadline"

        NotificationHelper.showTaskNotification(
            context = context,
            taskId = taskId,
            title = title
        )
    }

    companion object {

        const val EXTRA_TASK_ID = "extra_task_id"
        const val EXTRA_TASK_TITLE = "extra_task_title"
    }
}