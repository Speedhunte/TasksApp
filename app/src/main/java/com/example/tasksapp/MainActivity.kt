package com.example.tasksapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.tasksapp.data.network.connection.ConnectionManager
import com.example.tasksapp.di.ViewModelFactory
import com.example.tasksapp.presenation.navigation.AppNavigation
import com.example.tasksapp.ui.theme.TasksAppTheme
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : ComponentActivity() {

    @Inject
    lateinit var  connectionManager: ConnectionManager
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as TasksApplication).component.injectActivity(this)
        enableEdgeToEdge()
        setContent {
            TasksAppTheme {
                val isConnected by connectionManager.isConnectedFlow.collectAsStateWithLifecycle(
                    connectionManager.checkCurrentConnection()
                )
                ToastManager(isConnected)

                AppNavigation(
                    viewModelFactory
                )
            }
        }
    }
}


@Composable
fun ToastManager(isConnected: Boolean) {
    val context = LocalContext.current
    var prevNetworkStatus by remember { mutableStateOf(true) }
    LaunchedEffect(isConnected) {
        if (prevNetworkStatus && !isConnected){
            Toast.makeText(context, "Соединение потеряно",Toast.LENGTH_SHORT)
        }
        else if (!prevNetworkStatus && isConnected){
            Toast.makeText(context, "Соединение восстановлено",Toast.LENGTH_SHORT)
        }
        prevNetworkStatus=isConnected
    }
}
