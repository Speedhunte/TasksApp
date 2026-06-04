package com.example.tasksapp.presenation.allTasks.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.Undo
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.example.tasksapp.domain.models.Task
import com.example.tasksapp.presenation.allTasks.ActionIcon
import com.example.tasksapp.presenation.allTasks.componens.TaskItem
import com.example.tasksapp.presenation.allTasks.componens.TaskPreviewDialog
import com.example.tasksapp.presenation.allTasks.componens.ActionButton
import com.example.tasksapp.presenation.allTasks.componens.PullToRefreshWrapper
import com.example.tasksapp.presenation.allTasks.componens.TaskRevealWrapper
import com.example.tasksapp.presenation.allTasks.snackbar.CustomSnackBar
import com.example.tasksapp.presenation.allTasks.topBar.CollapsedTopBar
import com.example.tasksapp.ui.theme.deleteButtonColor
import com.example.tasksapp.ui.theme.pinButtonColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.tasksapp.presenation.allTasks.state.SelectionState
import com.example.tasksapp.ui.theme.mainColor


import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextDecoration
import com.example.tasksapp.presenation.allTasks.componens.Scrim

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksContent(
    pinnedItems: List<Task>,
    unpinnedItems: List<Task>,
    completedItems: List<Task>,
    selectionState: SelectionState,
    onChangeSelectionMode:()->Unit,
    onSelectItem: (Int)->Unit,
    navigateToTask: (Int)->Unit,
    navigateToNewTask: ()->Unit,
    onDeleteTask:(Int)->Unit,
    onCompleteTask:(Int)->Unit,
    onPinTask:(Int)->Unit,
    onDeleteSelected: ()->Unit,
    onSelectAll: ()->Unit,
    onCancelDeletion: ()->Unit,
    showCompleted: Boolean,
    onShowCompletedToggle: ()->Unit,
    syncTasks: ()->Unit
){


    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val onDelete:()->Unit ={
        scope.launch {
            val result = snackBarHostState
                .showSnackbar(
                    message ="task deleted",
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Indefinite
                )
            when(result){
                SnackbarResult.ActionPerformed -> {onCancelDeletion()}
                SnackbarResult.Dismissed -> Unit
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val expandedHeight = 120.dp
    val headerTranslation = (expandedHeight/2)

    val pullToRefreshState = rememberPullToRefreshState()
    val collapsedFraction by remember{ derivedStateOf { scrollBehavior.state.collapsedFraction }}
    var isRefreshing by remember { mutableStateOf(false) }
    val isRefreshEnabled by remember{derivedStateOf { collapsedFraction ==0f}}
    val topPaddingIfRefreshing = animateDpAsState(derivedStateOf { if (isRefreshing) 40.dp else 0.dp}.value)
    val onRefresh:()->Unit = {
        scope.launch {
            isRefreshing=true
            syncTasks()
            isRefreshing=false
        }
    }

    var currentRevealedItemId by remember { mutableStateOf<Int?>(null) }
    val onExpand:(Int)->Unit ={
        currentRevealedItemId=it
    }
    val onCollapse:()->Unit = {currentRevealedItemId=null}


    val haptics = LocalHapticFeedback.current
    var contextMenuTaskId by remember { mutableStateOf<Int?>(null) }



    Box(
        modifier= Modifier.fillMaxSize()
    ){
        PullToRefreshWrapper(
            state = pullToRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            enabled = isRefreshEnabled
        ) {
            Scaffold(
                containerColor = Color.White,
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackBarHostState,
                        snackbar = { CustomSnackBar(it) }
                    )
                },
                floatingActionButton = {
                    ActionButton(
                        isSelectionModeEnabled = selectionState.isInSelectionMode,
                        onClick ={
                            if(selectionState.isInSelectionMode){
                                onDeleteSelected()
                                onDelete()
                            } else navigateToNewTask()
                        },
                        selectedCount = selectionState.selectedTasks.size
                    )
                },
                topBar = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .clip(
                                RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp)
                            )
                            .background(mainColor)
                    ) {
                        CollapsedTopBar(
                            topPadding = topPaddingIfRefreshing.value,
                            onStartSelection = { onChangeSelectionMode()},
                            onSelectAll = {onSelectAll()  },
                            onCloseSelection = {onChangeSelectionMode()},
                            isSelectionMode = selectionState.isInSelectionMode,
                            numberSelectedTasks = selectionState.selectedTasks.size
                        )
//                    TopAppBar(
//                        colors = TopAppBarDefaults.topAppBarColors(
//                            containerColor = MaterialTheme.colorScheme.primary,
//                            scrolledContainerColor = MaterialTheme.colorScheme.primary,
//                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
//                            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
//                        ),
//                        title = {
//                            ExpandedTopBar(
//                                modifier = Modifier.graphicsLayer {
//                                    translationY =
//                                        collapsedFraction * headerTranslation.toPx()
//                                }
//                            )
//                        },
//                        expandedHeight = expandedHeight,
//                        scrollBehavior = scrollBehavior,
//                        windowInsets = WindowInsets(0)
//                    )
                    }

                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier.padding(innerPadding)
                        .nestedScroll(scrollBehavior.nestedScrollConnection)

                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                top = 8.dp,
                                bottom = 12.dp,
                                start = 8.dp,
                                end = 8.dp
                            )
                            .fillMaxSize()
                            //.padding(innerPadding)
                            .background(Color.White)

                    ) {
                        if (pinnedItems.isNotEmpty()) {
                            items(pinnedItems) {
                                TaskRevealWrapper(
                                    curTaskId = it.id,
                                    revealedTaskId = currentRevealedItemId,
                                    onCollapse = onCollapse,
                                    onExpand = onExpand,
                                    actions = {
                                        ActionIcon(
                                            onClick = {
                                                onDeleteTask(it.id)
                                                onDelete()
                                                onCollapse()
                                            },
                                            backgroundColor = deleteButtonColor,
                                            contentColor = Color.White,
                                            icon = Icons.Default.Delete,
                                            contentDescription = "Delete"
                                        )
                                        ActionIcon(
                                            onClick = {
                                                onCollapse()
                                                onPinTask(it.id)
                                            },
                                            backgroundColor = pinButtonColor,
                                            contentColor = Color.White,
                                            icon = if (it.isPinned) Icons.Outlined.Undo else Icons.Default.PushPin,
                                            contentDescription = if (it.isPinned) "Unpin" else "Pin"
                                        )

                                    },
                                    content = {
                                        TaskItem(
                                            onLongClick = {
                                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                contextMenuTaskId = it.id
                                            },
                                            item = it,
                                            navigateToItem = navigateToTask,
                                            isSelected = selectionState.selectedTasks.contains(
                                                it.id
                                            ),
                                            isInSelectionMode = selectionState.isInSelectionMode,
                                            onSelectTask = onSelectItem,
                                            onCompleteTask = { id ->
                                                onCompleteTask(id)
                                            }
                                        )
                                    }
                                )
                            }
                        }

                        if (unpinnedItems.isNotEmpty()) {
                            if(pinnedItems.isNotEmpty()){
                                item{
                                    HorizontalDivider()
                                }
                            }

                            items(unpinnedItems) {
                                TaskRevealWrapper(
                                    it.id,
                                    revealedTaskId = currentRevealedItemId,
                                    onCollapse = onCollapse,
                                    onExpand = onExpand,
                                    actions = {

                                        ActionIcon(
                                            onClick = {
                                                onPinTask(it.id)
                                                onCollapse()
                                            },
                                            backgroundColor = pinButtonColor,
                                            contentColor = Color.White,
                                            icon = if (it.isPinned) Icons.Outlined.Undo else Icons.Default.PushPin,
                                            contentDescription = if (it.isPinned) "Unpin" else "Pin"
                                        )
                                        ActionIcon(
                                            onClick = {
                                                onDeleteTask(it.id)
                                                onDelete()
                                                onCollapse()
                                            },
                                            backgroundColor = deleteButtonColor,
                                            contentColor = Color.White,
                                            icon = Icons.Default.Delete,
                                            contentDescription = "Delete"
                                        )
                                    },
                                    content = {
                                        TaskItem(
                                            onLongClick = {
                                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                contextMenuTaskId = it.id
                                            },
                                            item = it,
                                            navigateToItem = navigateToTask,
                                            isSelected = selectionState.selectedTasks.contains(
                                                it.id
                                            ),
                                            onSelectTask = onSelectItem,
                                            isInSelectionMode = selectionState.isInSelectionMode,
                                            onCompleteTask = { id ->
                                                onCompleteTask(id)
                                            }
                                        )
                                    }
                                )
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        onShowCompletedToggle()
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (showCompleted) {
                                        "Hide completed"
                                    } else {
                                        "Show completed"
                                    },
                                    color = Color.Gray,
                                    textDecoration = TextDecoration.Underline,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Icon(
                                    imageVector = if (showCompleted) {
                                        Icons.Default.KeyboardArrowUp
                                    } else {
                                        Icons.Default.KeyboardArrowDown
                                    },
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                        }

                        if (completedItems.isNotEmpty() && showCompleted) {

                            items(completedItems) {
                                TaskRevealWrapper(
                                    it.id,
                                    revealedTaskId = currentRevealedItemId,
                                    onCollapse = onCollapse,
                                    onExpand = onExpand,
                                    actions = {
                                        ActionIcon(
                                            onClick = {
                                                onPinTask(it.id)
                                                onCollapse()
                                            },
                                            backgroundColor = pinButtonColor,
                                            contentColor = Color.White,
                                            icon = if (it.isPinned) Icons.Outlined.Undo else Icons.Default.PushPin,
                                            contentDescription = if (it.isPinned) "Unpin" else "Pin"
                                        )
                                        ActionIcon(
                                            onClick = {
                                                onDeleteTask(it.id)
                                                onDelete()
                                                onCollapse()
                                            },
                                            backgroundColor = deleteButtonColor,
                                            contentColor = Color.White,
                                            icon = Icons.Default.Delete,
                                            contentDescription = "Delete"
                                        )
                                    },
                                    content = {
                                        TaskItem(
                                            onLongClick = {
                                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                                contextMenuTaskId = it.id
                                            },
                                            item = it,
                                            navigateToItem = {},
                                            isSelected = selectionState.selectedTasks.contains(
                                                it.id
                                            ),
                                            onSelectTask = onSelectItem,
                                            isInSelectionMode = selectionState.isInSelectionMode,
                                            onCompleteTask = { id ->
                                                onCompleteTask(id)
                                            }
                                        )
                                    }
                                )
                            }
                        }

                    }
                    //}

                }
            }
        }
        if(contextMenuTaskId != null) {
            Scrim(
                modifier = Modifier.fillMaxSize(),
                onClose = {
                    contextMenuTaskId = null
                }
            )
            TaskPreviewDialog(
                task = (pinnedItems + unpinnedItems + completedItems).first { it.id == contextMenuTaskId },
                onClose = { contextMenuTaskId = null },
                onPinTask = { onPinTask(contextMenuTaskId!!) },
                onDeleteTask = {
                    onDelete()
                    onDeleteTask(contextMenuTaskId!!)
                },
                onSetDeadline = { /*TODO*/ },
                navigateToTask = {
                    navigateToTask(contextMenuTaskId!!)
                    contextMenuTaskId = null
                }
            )
        }
    }

}


