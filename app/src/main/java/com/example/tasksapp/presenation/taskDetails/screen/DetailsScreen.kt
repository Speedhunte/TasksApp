package com.example.tasksapp.presenation.taskDetails.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tasksapp.data.utils.toPrettyString
import com.example.tasksapp.presenation.taskDetails.components.DateTimePickerDialog
import com.example.tasksapp.presenation.taskDetails.components.TaskTextField
import com.example.tasksapp.presenation.taskDetails.viewmodel.DialogState
import com.example.tasksapp.presenation.taskDetails.viewmodel.TaskDetailsEvent
import com.example.tasksapp.presenation.taskDetails.viewmodel.TaskDetailsViewModel
import com.example.tasksapp.ui.theme.Typography
import com.example.tasksapp.ui.theme.mainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel,
    onBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when(it){
                is TaskDetailsEvent.Saved -> onBack()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)),
                title = {
                    Text(
                        text = "Your task",
                        style = Typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = mainColor)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            TaskTextField(
                text = state.text,
                onTextChanged = viewModel::onTextChanged,
                modifier = Modifier
                    .weight(1f)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isCompleted,
                colors = ButtonDefaults.buttonColors(if (state.isCompleted) Color.Gray else mainColor),
                onClick = viewModel::onDeadlineClicked
            ) {
                Text(
                    text = state.deadline?.let {
                        "Deadline: ${
                            it.toPrettyString()
                        }"
                    } ?: "Добавить дедлайн"
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isCompleted,
                colors = ButtonDefaults.buttonColors(if (state.isCompleted) Color.Gray else MaterialTheme.colorScheme.primary),
                onClick = viewModel::onSaveTask
            )
            {
                Text(
                    text = "Сохранить",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

    when (val dialog = state.dialogState) {

        DialogState.None -> Unit

        DialogState.DatePicker -> {
            DateTimePickerDialog(
                onDismiss = viewModel::onDismissDialog,
                onDateTimeSelected = {
                    viewModel.onDeadlineSelected(it)
                }
            )
        }

        is DialogState.Error -> {
            AlertDialog(
                onDismissRequest = viewModel::onDismissDialog,
                title = {
                    Text("Ошибка")
                },
                text = {
                    Text(dialog.message)
                },
                confirmButton = {
                    TextButton(
                        onClick = viewModel::onDismissDialog
                    ) {
                        Text("ОК")
                    }
                }
            )
        }
    }
}