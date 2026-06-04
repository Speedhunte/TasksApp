package com.example.tasksapp.presenation.taskDetails.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import com.example.tasksapp.data.utils.toEpochMillis
import com.example.tasksapp.ui.theme.mainColor
import com.example.tasksapp.ui.theme.primaryColor
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerDialog(
    onDismiss: () -> Unit,
    onDateTimeSelected: (LocalDateTime) -> Unit,
) {


    var selectedDateTime by remember {
        mutableStateOf(LocalDateTime.now())
    }


    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(13.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.size(5.dp))

                WheelDateTimePicker(
                    modifier = Modifier
                        .padding(vertical = 20.dp),
                    minDateTime = LocalDateTime.now(),
                    textColor = mainColor
                ) { snappedDateTime ->
                    selectedDateTime = snappedDateTime
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        modifier = Modifier
                            .width(130.dp),
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        )
                    ) {
                        Text(
                            text = "Отменить",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        modifier = Modifier
                            .width(130.dp),
                        onClick = {

                            onDateTimeSelected(selectedDateTime )
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                        )
                    ) {
                        Text(
                            text = "OK",
                            color = mainColor,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview()
@Composable
fun DateTimePickerDialogPreview() {
    DateTimePickerDialog(
        onDismiss = { },
        onDateTimeSelected = { _ ->},
    )
}