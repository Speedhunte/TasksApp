package com.example.tasksapp.data.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.tasksapp.domain.scheduler.AlarmScheduler
import javax.inject.Inject

class TaskAlarmScheduler @Inject constructor(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun schedule(
        taskId: Int,
        title: String,
        triggerAtMillis: Long
    ) {

        val intent = Intent(
            context,
            TaskDeadlineReceiver::class.java
        ).apply {
            putExtra(TaskDeadlineReceiver.EXTRA_TASK_ID, taskId)
            putExtra(TaskDeadlineReceiver.EXTRA_TASK_TITLE, title)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (!alarmManager.canScheduleExactAlarms()) {
                return
            }
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    override fun cancel(taskId: Int) {

        val intent = Intent(
            context,
            TaskDeadlineReceiver::class.java
        )

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}