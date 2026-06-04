package com.example.tasksapp.di

import com.example.tasksapp.data.notifications.TaskAlarmScheduler
import com.example.tasksapp.domain.scheduler.AlarmScheduler
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface NotificationsModule {

    @Singleton
    @Binds
    fun bindAlarmScheduler(impl: TaskAlarmScheduler): AlarmScheduler
}