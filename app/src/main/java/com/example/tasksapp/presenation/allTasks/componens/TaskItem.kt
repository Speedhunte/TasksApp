package com.example.tasksapp.presenation.allTasks.componens

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasksapp.R
import com.example.tasksapp.data.utils.toPrettyString
import com.example.tasksapp.domain.models.Task
import com.example.tasksapp.presenation.allTasks.CustomCheckBox
import com.example.tasksapp.presenation.allTasks.CustomCheckedIcon
import com.example.tasksapp.ui.theme.Typography
import java.time.LocalDateTime

@SuppressLint("RememberInComposition")
@Composable
fun TaskItem(
    item: Task,
    onLongClick: ()->Unit,
    navigateToItem: (Int) -> Unit,
    isSelected: Boolean,
    isInSelectionMode: Boolean ,
    onSelectTask: (Int) -> Unit,
    onCompleteTask: (Int) -> Unit,
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.surfaceContainerHighest
    else MaterialTheme.colorScheme.surfaceContainerHigh

    val overlayColor by animateColorAsState(
        targetValue = if (isInSelectionMode && isSelected) Color.Black.copy(alpha = 0.4f) else Color.Transparent,
        label = "selectionOverlay"
    )

    val modifier = Modifier.combinedClickable(
        interactionSource = MutableInteractionSource(),
        onLongClick = onLongClick,
        onClick = {
            if (isInSelectionMode) onSelectTask(item.id)
            else navigateToItem(item.id)
        }

    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            //.clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .then(modifier),
        contentAlignment = Alignment.CenterStart

    ) {

        Row(
            modifier = Modifier
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isInSelectionMode) {
                CustomCheckBox(
                    size = 25.dp,
                    isChecked = item.isDone,
                    onChecked = { onCompleteTask(item.id) }
                )
            }


            Spacer(modifier = Modifier.width(15.dp))
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = item.text,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when {
                        item.isDone -> Color.Gray
                        item.deadline?.isBefore(LocalDateTime.now()) == true -> Color.Red
                        else -> Color.Black
                    },
                    textDecoration = if (item.isDone) TextDecoration.LineThrough else null,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                if (item.deadline != null && !item.isDone) {
                    Text(text = "Deadline: ${item.deadline.toPrettyString()}", style = Typography.bodySmall)
                }

                if (item.isDone) {
                    item.completionDate?.let {
                        Text(text = stringResource(R.string.completed,
                            it.toPrettyString()
                        ))
                    }

                }
            }

            if (item.isPinned) {
                Icon(
                    imageVector = Icons.Default.PushPin,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(25.dp).padding(5.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(overlayColor)
        )

        if (isInSelectionMode && isSelected) {
            CustomCheckedIcon(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .padding(8.dp),
                size = 20.dp
            )

        }
    }
}


@Preview
@Composable
private fun ItemPreview() {
    TaskItem(
        item = Task(
            id = 1,
            text = "Купить продукты",
            creationDate = LocalDateTime.now().minusDays(2),
            lastUpdateDate = LocalDateTime.now().minusDays(1),
            deadline =null,
            completionDate = null,
            isDone = false,
            isPinned = true
        ),
        navigateToItem = {},
        onSelectTask = {},
        onCompleteTask = {},
        onLongClick = {},
        isSelected = false,
        isInSelectionMode = false,
    )
}


