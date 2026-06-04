package com.example.tasksapp.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.tasksapp.MainActivity
import com.example.tasksapp.TasksApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ViewModelModule::class, NetworkModule::class, DatabaseModule::class, NotificationsModule::class])
interface  AppComponent {

    fun injectActivity(activity: MainActivity)
    //fun getViewModelFactory():ViewModelFactory
    fun injectApplication(application: TasksApplication)

    @Component.Factory
    interface Factory{
        fun create (@BindsInstance context: Context): AppComponent
    }
}