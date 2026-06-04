package com.example.tasksapp.presenation.permissionScreen

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner


@Composable
fun PermissionScreen(
    modifier: Modifier = Modifier,
    navigateNext: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity

    val lifecycleOwner = LocalLifecycleOwner.current
    var notificationRequestAttempted by rememberSaveable {
        mutableStateOf(false)
    }

    var showOpenSettings by rememberSaveable {
        mutableStateOf(false)
    }

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                showOpenSettings = false

                if (context.hasExactAlarmPermission()) {
                    navigateNext()
                } else {
                    context.openExactAlarmSettings()
                }

                return@rememberLauncherForActivityResult
            }

            val shouldShowRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                )

            showOpenSettings = notificationRequestAttempted && !shouldShowRationale
        }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (
                    context.hasNotificationPermission() &&
                    context.hasExactAlarmPermission()
                ) {
                    navigateNext()
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    PermissionContent(
        title = "Enable Notifications",
        description = "Allow notifications and exact alarms so we can remind you about tasks and events on time.",
        buttonText = "Continue",
        onClick = {
            when {

                showOpenSettings -> {
                    context.openAppSettings()
                }

                !context.hasNotificationPermission() -> {
                    notificationRequestAttempted = true

                    notificationPermissionLauncher.launch(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                }

                !context.hasExactAlarmPermission() -> {
                    context.openExactAlarmSettings()
                }

                else -> {
                    navigateNext()
                }
            }
        }
    )
}

@Composable
private fun PermissionContent(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = description,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
           // color= MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onClick
        ) {
            Text(buttonText)
        }
    }
}




