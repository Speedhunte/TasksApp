package com.example.tasksapp.data.repository

import android.util.Log
import com.example.tasksapp.data.database.TaskDao
import com.example.tasksapp.data.database.TaskEntity
import com.example.tasksapp.data.network.TaskApiService
import com.example.tasksapp.data.network.connection.ConnectionManager
import com.example.tasksapp.data.network.toDomain
import com.example.tasksapp.data.network.toDto
import com.example.tasksapp.data.network.toEntity
import com.example.tasksapp.data.utils.toEpochMillis
import com.example.tasksapp.domain.models.Task
import com.example.tasksapp.domain.repositories.TasksRepository
import com.example.tasksapp.domain.scheduler.AlarmScheduler
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.time.LocalDateTime

class TasksRepositoryImpl @Inject constructor(
    private val api: TaskApiService,
    private val connectionManager: ConnectionManager,
    private val dao: TaskDao,
    private val alarmScheduler: AlarmScheduler
): TasksRepository {

    private val isNetworkAvailable: MutableStateFlow<Boolean> = MutableStateFlow(connectionManager.checkCurrentConnection())
    private val scope = CoroutineScope(Dispatchers.Default)

    override val items: Flow<List<Task>> =
        dao.getTaskList()
            .map { list -> list.map(TaskEntity::toDomain) }

    override val pinnedItems: Flow<List<Task>> =
        items.map { tasks ->
            tasks.filter { it.isPinned && !it.isDone }
        }

    override val unpinnedItems: Flow<List<Task>> =
        items.map { tasks ->
            tasks.filter { !it.isPinned && !it.isDone }
        }

    override val completedItems: Flow<List<Task>> =
        items.map { tasks ->
            tasks.filter { it.isDone }
        }
    init{
        scope.launch {
            connectionManager.isConnectedFlow.collect {
                isNetworkAvailable.value= it
            }
        }

    }

    private suspend fun <T> executeRequest(
        delayTime: Long = 3000L,
        maxRetries: Int=2,
        request: suspend ()-> Response<T>
    ):T{
        var lastError: String? = null
        repeat(maxRetries){
            val response = request()
            val body = response.body()
            if(!response.isSuccessful || body==null){
                Log.d("mytag", response.message())
                lastError = response.message()
                delay(delayTime)
            }else{
                Log.d("mytag", body.toString())

                return body
            }
        }
        throw Exception(lastError)
    }

    override suspend fun syncFromServer() =withContext(Dispatchers.IO){
        if (!connectionManager.checkCurrentConnection()) {
            return@withContext
        }
        val remoteTasks = executeRequest {
            api.getTaskList()
        }

        remoteTasks.forEach { dto ->
            val remote = dto.toEntity()
            val local = try {
                dao.getTaskById(remote.id)
            } catch (_: Exception) {
                null
            }
            when {
                local == null -> {
                    dao.upsertTask(remote)
                }
                remote.lastUpdateDate!! > (local.lastUpdateDate ?: 0L) -> {
                    dao.upsertTask(remote)
                }
            }
        }
    }
    override suspend fun syncWithServer() {

        if (!connectionManager.checkCurrentConnection()) {
            return
        }

        syncFromServer()
    }

    override suspend fun  getTasksList()= withContext(Dispatchers.IO){
        syncFromServer()
    }

    override suspend fun addTaskItem(task: Task)= withContext(Dispatchers.IO){
        dao.upsertTask(task.toDto().toEntity())
        task.deadline?.let { deadline ->

            alarmScheduler.schedule(
                taskId = task.id,
                title = task.text,
                triggerAtMillis = deadline.toEpochMillis()
            )
        }
        if(connectionManager.checkCurrentConnection()){
            executeRequest{
                api.addTask(task.id.toString(), task.toDto())
            }
        }
    }

    override suspend fun updateTaskItem(task: Task)=withContext(Dispatchers.IO){
        dao.upsertTask(task.toDto().toEntity())
        if(connectionManager.checkCurrentConnection()){
            executeRequest{
                api.addTask(task.id.toString(), task.toDto())
            }
        }
    }

    override suspend fun markCompleted(id: Int) = withContext(Dispatchers.IO){
        val task = dao.getTaskById(id)
        val updatedTask = task.copy(
            isDone = true,
        )

        updateTaskItem(updatedTask.toDomain().copy(completionDate = LocalDateTime.now()))
    }

    override suspend fun deleteTaskItem(id: Int) = withContext(Dispatchers.IO){
        dao.deleteTask(dao.getTaskById(id))
        if (connectionManager.checkCurrentConnection()){
            executeRequest{
                api.deleteTask(id)
            }
        }
    }
    override suspend fun deleteSelectedTasks(taskIds: Set<Int>)= withContext(Dispatchers.IO){
        dao.getTaskList().firstOrNull()?.filter { task ->
            taskIds.contains(task.id)
        }?.forEach { task ->
            dao.deleteTask(task)
        }
        if (!connectionManager.checkCurrentConnection()) return@withContext
        taskIds.map { id ->
            async {
                executeRequest {
                    api.deleteTask(id)
                }
            }
        }.awaitAll()
    }


    override suspend fun  getTaskById(id: Int) = withContext(Dispatchers.IO){
        val task = if(connectionManager.checkCurrentConnection()){
            executeRequest {
                api.getTaskById(id)
            }.toDomain()
        }
        else{
            dao.getTaskById(id).toDomain()
        }
        return@withContext task
    }

    override suspend fun pinTask(id: Int) = withContext(Dispatchers.IO){
        val task = dao.getTaskById(id)
        val newTask = task.copy(
            isPinned = !task.isPinned
        )
        updateTaskItem(newTask.toDomain())

    }
    override suspend fun deleteTaskList(tasks: List<Task>) = with(Dispatchers.IO){
        if(connectionManager.checkCurrentConnection()){
            executeRequest {
                api.deleteTaskList()
            }
        }
    }
}