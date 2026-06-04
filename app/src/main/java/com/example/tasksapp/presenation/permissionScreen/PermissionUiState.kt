package com.example.tasksapp.presenation.permissionScreen


data class PermissionUiState(
    val notificationGranted: Boolean,
    val exactAlarmGranted: Boolean
) {
    val allGranted: Boolean
        get() = notificationGranted && exactAlarmGranted
}