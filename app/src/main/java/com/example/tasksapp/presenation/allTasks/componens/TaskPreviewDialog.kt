package com.example.tasksapp.presenation.allTasks.componens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasksapp.domain.models.Task

@Composable
fun TaskPreviewDialog(
    modifier: Modifier = Modifier,
    task: Task,
    onClose: () -> Unit,
    onPinTask: ()->Unit,
    onSetDeadline: ()->Unit,
    onDeleteTask: ()->Unit,
    navigateToTask: (Int)->Unit
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        navigateToTask(task.id)
                    }
                    .background(Color.White.copy(alpha = 0.9f))
                    .padding(16.dp)

            ) {
                Text(
                    text = task.text,
                    maxLines = 8,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier=Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.DarkGray.copy(alpha = 0.85f))

            ){
                ActionButton(
                    onClick = {
                        onPinTask()
                        onClose()
                    },
                    buttonName = if (task.isPinned) "Unpin Task" else "Pin Task",
                    buttonIcon = Icons.Default.PushPin
                )
                HorizontalDivider(
                    modifier = Modifier.width(200.dp),
                    thickness = 0.5.dp,
                    color = Color.White
                )
                ActionButton(
                    onClick = {
                        onSetDeadline()
                        onClose()
                    },
                    buttonName = "Set deadline",
                    buttonIcon = Icons.Default.Alarm
                )

                HorizontalDivider(
                    modifier = Modifier.width(200.dp),
                    thickness = 0.5.dp,
                    color = Color.White
                )
                ActionButton(
                    onClick = {
                        onDeleteTask()
                        onClose()
                    },
                    textColor = Color.Red,
                    iconColor = Color.Red,
                    buttonName = "Delete",
                    buttonIcon = Icons.Default.Delete
                )
            }


        }
    }

}


@Composable
fun ActionButton(
    onClick: ()->Unit={},
    iconColor: Color = Color.White,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier,
    buttonName: String,
    buttonIcon: ImageVector,
) {

    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .width(200.dp)
            .background(Color.Transparent)
            .clickable(
                interactionSource=interactionSource,
                indication = ripple(
                    bounded = true,
                    color = Color.White.copy(alpha = 0.3f)
                )
            ){
                onClick()
            }
            .padding(start=10.dp, end = 10.dp, top=8.dp, bottom=8.dp)

        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = buttonName,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )

        Icon(
            modifier= Modifier.size(15.dp),
            imageVector = buttonIcon,
            tint = iconColor,
            contentDescription = null
        )
    }
}



@Preview(showSystemUi = true)
@Composable
private fun taskDialogPreview() {

//    TaskPreviewDialog(
//        task = Task(
//            id = 1,
//            text = "Купить продуктыfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
//            creationDate = LocalDateTime.now().minusDays(2),
//            lastUpdateDate = LocalDateTime.now().minusDays(1),
//            deadline = LocalDateTime.now().plusDays(1),
//            completionDate = LocalDateTime.now(),
//            isDone = false,
//            priority = Priority.MEDIUM,
//            isPinned = true
//        ),
//        onClose = {}
//    )
}