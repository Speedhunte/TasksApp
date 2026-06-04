package com.example.tasksapp.data.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tasksapp.R

object NotificationHelper {

    const val CHANNEL_ID = "tasks_deadline_channel"

    fun createChannel(
        context: Context
    ) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Task reminders",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications about task deadlines"
            enableVibration(true)
            lockscreenVisibility =
                Notification.VISIBILITY_PUBLIC
        }

        val manager =
            context.getSystemService(
                NotificationManager::class.java
            )

        manager.createNotificationChannel(channel)
    }

    fun showTaskNotification(
        context: Context,
        taskId: Int,
        title: String
    ) {

        val launchIntent =
            context.packageManager
                .getLaunchIntentForPackage(
                    context.packageName
                )

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                taskId,
                launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setContentTitle("Task Deadline!!!")
                .setContentText(title)
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        NotificationManagerCompat
            .from(context)
            .notify(taskId, notification)
    }
}