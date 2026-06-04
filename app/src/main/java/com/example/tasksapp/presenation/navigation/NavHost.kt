package com.example.tasksapp.presenation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tasksapp.di.ViewModelFactory
import com.example.tasksapp.presenation.allTasks.screen.AllTasksScreen
import com.example.tasksapp.presenation.allTasks.viewmodel.AllTasksViewModel
import com.example.tasksapp.presenation.permissionScreen.PermissionScreen
import com.example.tasksapp.presenation.taskDetails.screen.TaskDetailsScreen
import com.example.tasksapp.presenation.taskDetails.viewmodel.TaskDetailsViewModel
import kotlinx.serialization.Serializable


@Serializable
data class TaskDetails(
    val id: Int?=null
)
@Serializable
data object AllTasks

@Serializable
data object PermissionScreen


@Composable
fun AppNavigation(
    viewModelFactory: ViewModelFactory,
    modifier: Modifier = Modifier
){
    val navController = rememberNavController()

    NavHost(navController=navController, startDestination = PermissionScreen
    ) {

        composable<PermissionScreen> {
            PermissionScreen(
                navigateNext = {
                    navController.navigate(AllTasks)
                }
            )
        }
        composable<AllTasks> {

            val viewModel = viewModel<AllTasksViewModel>(
                factory = viewModelFactory.create(it)
            )

            AllTasksScreen(
                viewModel = viewModel,
                navigateToTask = { id ->
                    navController.navigate(TaskDetails(id))
                },
                navigateToNewTask = {
                    navController.navigate(TaskDetails())
                }
            )
        }

        composable<TaskDetails> { backStackEntry ->

            val id = backStackEntry.toRoute<TaskDetails>().id
            Log.d("mytag", "outside viewmodel: ${id.toString()}")
            val viewModel: TaskDetailsViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                factory = viewModelFactory.create(
                    owner = backStackEntry,
                    defaultArgs = backStackEntry.arguments
                )
            )
            TaskDetailsScreen(
                viewModel,
                onBack = {navController.popBackStack()}
            )

        }
    }
}
