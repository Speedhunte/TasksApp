package com.example.tasksapp.presenation.allTasks.topBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tasksapp.R

@Composable
fun ExpandedTopBar(
    modifier: Modifier = Modifier,
   numberCompletedTasks: Int = 0,
   isVisible: Boolean= true
) {


    Column(
        modifier = modifier.padding(
            top = 15.dp,
            end= 5.dp
        ),
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge
        )
        Row{
            Text(
                color =  MaterialTheme.colorScheme.onPrimary,
                text= stringResource(R.string.completed_tasks, numberCompletedTasks),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier.width(10.dp))

            IconButton(
                onClick = {}
            ) {

                Icon(
                    tint = Color.White,
                    imageVector =
                        if(isVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff
                ,
                    contentDescription = null
                )
            }
        }

    }
}