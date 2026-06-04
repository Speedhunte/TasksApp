package com.example.tasksapp.presenation.allTasks.componens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tasksapp.ui.theme.deleteButtonColor
import com.example.tasksapp.ui.theme.mainColor


@Composable
fun ActionButton(onClick: () -> Unit, isSelectionModeEnabled: Boolean, selectedCount: Int) {
    val isActive = !isSelectionModeEnabled || (selectedCount > 0)
    FloatingActionButton(
        containerColor = if (!isSelectionModeEnabled) {
            mainColor
        } else if (!isActive) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            deleteButtonColor
        },
        contentColor = Color.White,
        onClick = if (isActive) onClick else { -> },
    ) {
        if (isSelectionModeEnabled) {
            Icon(Icons.Filled.Delete, null)
        } else {
            Icon(Icons.Filled.Add, null)
        }
    }
}