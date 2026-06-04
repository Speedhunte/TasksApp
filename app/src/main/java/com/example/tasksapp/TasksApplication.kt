package com.example.tasksapp

import android.app.Application
import com.example.tasksapp.data.network.workManager.SyncScheduler
import com.example.tasksapp.data.notifications.NotificationHelper
import com.example.tasksapp.di.AppComponent
import com.example.tasksapp.di.AppWorkerFactory
import com.example.tasksapp.di.DaggerAppComponent
import javax.inject.Inject
import androidx.work.Configuration

class TasksApplication : Application(), Configuration.Provider {
    lateinit var component: AppComponent

    @Inject
    lateinit var workerFactory: AppWorkerFactory

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(this)
        component.injectApplication(this)
        NotificationHelper.createChannel(this)
        SyncScheduler.start(this)
    }
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}