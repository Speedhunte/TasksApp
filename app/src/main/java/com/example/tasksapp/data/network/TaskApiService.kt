package com.example.tasksapp.data.network

import androidx.room.Delete
import com.example.tasksapp.data.network.dto.TaskDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApiService {

    @GET("tasks.json")
    suspend fun getTaskList(): Response<List<TaskDto>>

    @GET("tasks/{id}.json")
    suspend fun getTaskById(@Path("id") id: Int ): Response<TaskDto>

    @PUT("tasks/{id}.json")
    suspend fun addTask(@Path("id") id: String, @Body task: TaskDto): Response<TaskDto>

    @PUT("tasks.json")
    suspend fun insertTaskList(@Body tasks: List<TaskDto>): Response<List<TaskDto>>
    @DELETE("task/{id}.json")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>

    @DELETE("tasks.json")
    suspend fun deleteTaskList(): Response<Unit>
}