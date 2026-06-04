package com.example.tasksapp.data.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


private const val DATE_TIME_FORMAT = "MMMM dd, yyyy, 'at' HH:mm"



fun LocalDateTime.toEpochMillis(): Long {
    return atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun LocalDateTime.toPrettyString(): String {
    val formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
    return this.format(formatter)
}