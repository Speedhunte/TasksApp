package com.example.tasksapp.presenation.allTasks.topBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.tasksapp.R
import com.example.tasksapp.ui.theme.Typography
import com.example.tasksapp.ui.theme.mainColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedTopBar(
    topPadding: Dp,
    onSelectAll: ()->Unit,
    onStartSelection: ()->Unit,
    onCloseSelection: ()->Unit,
    numberSelectedTasks: Int ,
    isSelectionMode: Boolean,
    modifier: Modifier = Modifier,
) {

//    if(isSelectionMode){
        CenterAlignedTopAppBar(
            modifier = Modifier
                .padding( top = topPadding)
            //.clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            ,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = mainColor,
                scrolledContainerColor = mainColor,
                navigationIconContentColor = mainColor,
                actionIconContentColor = mainColor
            ),
            title = {

                if(isSelectionMode){
                    Text(
                        text =
                            if (numberSelectedTasks>0)
                                stringResource(R.string.selected_tasks, numberSelectedTasks)
                            else
                                stringResource(R.string.selected)
                        ,
                        style = Typography.titleLarge,
                        color = Color.White
                    )
                }
                else{
                    Text(
                        text = "Your Tasks",
                        style = Typography.titleLarge,
                        color = Color.White,
                    )

                }
               // }

            },
            navigationIcon = {
                AnimatedVisibility(isSelectionMode) {
                    IconButton(
                        onClick=onCloseSelection,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            },
            actions = {
                AnimatedVisibility(
                    isSelectionMode
                ) {
                    IconButton(
                        onClick = onSelectAll
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_select_all),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null
                        )
                    }
                }
                AnimatedVisibility(!isSelectionMode) {
                    IconButton(
                        onClick = onStartSelection
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = null
                        )
                    }
                }
            },
        )
}