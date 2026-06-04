package com.example.tasksapp.di

import com.example.tasksapp.data.network.RetrofitBuilder
import com.example.tasksapp.data.network.TaskApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit= RetrofitBuilder.retrofit

    @Singleton
    @Provides
    fun provideTaskApi(retrofit: Retrofit): TaskApiService= retrofit.create(TaskApiService::class.java)
}