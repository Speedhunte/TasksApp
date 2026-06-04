package com.example.tasksapp.presenation.allTasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasksapp.data.repository.TasksRepositoryImpl
import com.example.tasksapp.domain.models.Task
import com.example.tasksapp.domain.repositories.TasksRepository
import com.example.tasksapp.presenation.allTasks.state.SelectionState
import com.example.tasksapp.presenation.common.State
import com.example.tasksapp.presenation.allTasks.state.SelectionEvent
import com.example.tasksapp.presenation.allTasks.state.TasksState
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllTasksViewModel @Inject constructor(
    private val repository: TasksRepository,
): ViewModel() {

    private var deletedItems: List<Task> = emptyList()

    private val selectionState = MutableStateFlow<SelectionState>(SelectionState())

    val exceptionHandler= CoroutineExceptionHandler{_, exception->
        viewModelScope.launch {
            errorFlow.emit( State.Error(exception.toString()))
        }
    }

    private val errorFlow = MutableSharedFlow<State.Error>()

    private val showCompleted = MutableStateFlow(false)

    val state: StateFlow<TasksState> = combine(
        repository.pinnedItems,
        repository.unpinnedItems,
        repository.completedItems,
        selectionState,
        showCompleted
    ){
        pinnedItems, unpinnedItems, completedItems,selectionState, showCompleted->
        TasksState(
            pinnedItems,
            unpinnedItems,
            completedItems,
            selectionState,
            showCompleted
        )

    }.catch {error-> errorFlow.emit(State.Error(error.toString())) }
    .stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TasksState()
    )


    init{
        viewModelScope.launch(exceptionHandler) {
            repository.getTasksList()
        }
    }

    fun onSelectionEvent(event: SelectionEvent){
        when(event){
            is SelectionEvent.OnSelectionModeChanged->{
                if(selectionState.value.isInSelectionMode){
                    selectionState.update {
                        it.copy(
                            isInSelectionMode = false,
                            selectedTasks = emptySet()
                        )
                    }
                }
                else{
                    selectionState.update {
                        it.copy(
                            isInSelectionMode = true,
                        )
                    }
                }
            }
            is SelectionEvent.OnItemSelectionChanged->{
                selectionState.update {
                    if (it.selectedTasks.contains(event.id))
                         it.copy(selectedTasks=it.selectedTasks-event.id)
                    else{
                        it.copy(selectedTasks=it.selectedTasks+event.id)
                    }
                }
            }
            is SelectionEvent.OnSelectAll -> {
                val state = state.value

                val all = state.pinnedItems+state.unpinnedItems+state.completedItems
                selectionState.update {
                    it.copy(selectedTasks=all.map {task-> task.id }.toSet())
                }
            }

        }
    }

    fun syncTasks(){
        viewModelScope.launch(exceptionHandler) {
            repository.syncWithServer()
        }
    }

    fun onCancelDeletion(){
        viewModelScope.launch(exceptionHandler) {
            deletedItems.forEach { repository.addTaskItem(it) }
        }
    }

    fun onCompleteTask(id: Int){
        viewModelScope.launch (exceptionHandler){
            repository.markCompleted(id)

        }
    }

    fun onDeleteTask(id: Int){
        val state = state.value
        val all = state.pinnedItems+state.unpinnedItems+state.completedItems
        val deletedTask = all.first { it.id == id }
        deletedItems=listOf(deletedTask)
        viewModelScope.launch(exceptionHandler) {
            repository.deleteTaskItem(id)
        }
    }

    fun onDeleteSelectedTasks(){
        val state = state.value
        val all = state.pinnedItems+state.unpinnedItems+state.completedItems
        val deletedTasks = all.filter { selectionState.value.selectedTasks.contains(it.id) }
        deletedItems=deletedTasks
        viewModelScope.launch (exceptionHandler){

            repository.deleteSelectedTasks(selectionState.value.selectedTasks)
            selectionState.update {
                it.copy(
                    isInSelectionMode = false,
                    selectedTasks = emptySet()
                )
            }
        }
    }

    fun onPinTask(id: Int){
        viewModelScope.launch (exceptionHandler){
            repository.pinTask(id)
        }
    }

    fun onShowCompletedToggle(){
        showCompleted.update { !it }
    }

}