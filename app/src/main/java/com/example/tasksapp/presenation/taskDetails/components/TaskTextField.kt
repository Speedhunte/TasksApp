package com.example.tasksapp.presenation.taskDetails.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {

        if (text.isEmpty()) {
            Text(
                text = "Введите текст задачи",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        BasicTextField(
            value = text,
            onValueChange = onTextChanged,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.fillMaxSize(),

        )
    }
}