package com.example.tasksapp.presenation.allTasks.state


sealed class SelectionEvent{
    data object OnSelectionModeChanged: SelectionEvent()
    data class OnItemSelectionChanged(val id: Int): SelectionEvent()
    data object OnSelectAll: SelectionEvent()
}
