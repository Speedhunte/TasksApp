package com.example.tasksapp.presenation.allTasks.componens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PullToRefreshWrapper(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    onRefresh: ()->Unit,
    enabled: Boolean = true,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.()->Unit
) {

    Box(
        modifier= Modifier.pullToRefresh(
            isRefreshing= isRefreshing,
            state = state,
            enabled = enabled,
            onRefresh=onRefresh
        ),
       contentAlignment=contentAlignment
    ){

        content()
        Indicator(
            state = state,
            isRefreshing=isRefreshing,
            modifier= Modifier.align(alignment = Alignment.TopCenter)
        )

    }

}