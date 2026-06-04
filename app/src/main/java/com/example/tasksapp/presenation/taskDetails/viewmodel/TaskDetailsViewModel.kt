package com.example.tasksapp.presenation.taskDetails.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tasksapp.data.repository.TasksRepositoryImpl
import com.example.tasksapp.domain.models.Task
import com.example.tasksapp.presenation.navigation.TaskDetails
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.random.Random


class TaskDetailsViewModel(
    private val repository: TasksRepositoryImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: Int? =
        savedStateHandle.toRoute<TaskDetails>().id
    private val _events = MutableSharedFlow<TaskDetailsEvent>()
    val events = _events.asSharedFlow()

    private val _state = MutableStateFlow(TaskDetailsState())
    val state: StateFlow<TaskDetailsState> = _state

    init {
        Log.d("mytag", taskId.toString())
        taskId?.let(::loadTask)
    }

    private fun loadTask(id: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val task = repository.getTaskById(id)

            _state.value = TaskDetailsState(
                text = task.text,
                deadline = task.deadline,
                isLoading = false,
                isCompleted = task.isDone
            )
        }
    }

    fun onTextChanged(text: String) {
        _state.update {
            it.copy(text = text)
        }
    }

    fun onDeadlineClicked() {
        _state.update {
            it.copy(
                dialogState = DialogState.DatePicker
            )
        }
    }

    fun onDismissDialog() {
        _state.update {
            it.copy(
                dialogState = DialogState.None
            )
        }
    }

    fun onDeadlineSelected(date: LocalDateTime) {
        _state.update {
            it.copy(
                deadline = date,
                dialogState = DialogState.None
            )
        }
    }

    fun onSaveTask() {
        val text = state.value.text.trim()
        if (text.isBlank()) {
            _state.update {
                it.copy(
                    dialogState = DialogState.Error(
                        "Введите текст задачи"
                    )
                )
            }
            return
        }
        viewModelScope.launch {

            val task = Task(
                id = taskId?:run{ Random.nextInt() },
                text = state.value.text,
                creationDate = LocalDateTime.now(),
                lastUpdateDate = LocalDateTime.now(),
                deadline = state.value.deadline,
                completionDate = null,
                isDone = false,
                isPinned = false
            )
            repository.addTaskItem(task)
            _events.emit(TaskDetailsEvent.Saved)

        }
    }
}