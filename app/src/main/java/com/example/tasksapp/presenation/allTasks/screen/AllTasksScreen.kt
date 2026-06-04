package com.example.tasksapp.presenation.allTasks.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.tasksapp.presenation.allTasks.viewmodel.AllTasksViewModel
import com.example.tasksapp.presenation.allTasks.state.SelectionEvent


@Composable
fun AllTasksScreen(
    viewModel: AllTasksViewModel,
    navigateToTask:(Int)->Unit,
    navigateToNewTask: ()->Unit
){

    val state  by viewModel.state.collectAsState()

    TasksContent(
        pinnedItems = state.pinnedItems,
        unpinnedItems = state.unpinnedItems,
        completedItems = state.completedItems,
        selectionState = state.selectionState,
        onChangeSelectionMode = {
            viewModel.onSelectionEvent(SelectionEvent.OnSelectionModeChanged)
        },
        onSelectItem = {
            viewModel.onSelectionEvent(
                SelectionEvent.OnItemSelectionChanged(id = it)
            )
        },
        navigateToTask = navigateToTask,
        navigateToNewTask = navigateToNewTask,
        onCompleteTask = {
            viewModel.onCompleteTask(it)
        },
        onDeleteTask = {
            viewModel.onDeleteTask(it)
        },
        onPinTask = {
            viewModel.onPinTask(it)
        },
        onDeleteSelected = {
            viewModel.onDeleteSelectedTasks()
        },
        onSelectAll = {
            viewModel.onSelectionEvent(SelectionEvent.OnSelectAll)
        },
        onCancelDeletion = { viewModel.onCancelDeletion() },
        showCompleted = viewModel.state.value.showCompleted,
        onShowCompletedToggle = viewModel::onShowCompletedToggle,
        syncTasks = viewModel::syncTasks
    )
}