package com.example.tasksapp.presenation.allTasks.componens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.tasksapp.domain.models.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskActionsSheet(
    task: Task,
    onDismissSheet: ()->Unit
) {

    ModalBottomSheet(
        onDismissRequest  =onDismissSheet
    ) {
        ListItem(
            headlineContent = {Text("Edit task")},
            leadingContent = {
                Icon(imageVector = Icons.Default.Edit,
                contentDescription = null)
            }
        )
        ListItem(
            headlineContent = {Text("Change priority")},
            leadingContent = {
                Icon(imageVector = Icons.Default.PriorityHigh,
                contentDescription = null)
            }
        )

        ListItem(
            headlineContent = {Text("Set/change Deadline")},
            leadingContent = {
                Icon(imageVector = Icons.Default.Alarm,
                    contentDescription = null)
            }
        )


    }

}