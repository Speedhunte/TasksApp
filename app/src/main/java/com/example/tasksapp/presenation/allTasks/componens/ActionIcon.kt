package com.example.tasksapp.presenation.allTasks

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasksapp.ui.theme.Typography

@SuppressLint("RememberInComposition")
@Composable
fun ActionIcon(
    onClick: () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String,
    iconTint: Color = Color.White,
    textColor: Color = Color.White
) {

        Box(modifier= Modifier
            .background(
                color = backgroundColor,
                //shape = RoundedCornerShape(50)
            )
            .heightIn(80.dp)
            .width(80.dp)
//            .onSizeChanged{
//                Log.d("mytag", it.width.toString())
//            }
            .fillMaxHeight()
            .clickable(
                interactionSource = MutableInteractionSource(),
                onClick = onClick
            ),
            contentAlignment = Alignment.Center

        ){
            Column(
                modifier= Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Icon(
                    //modifier = Modifier.align (alignment = Alignment.Center)
                    tint= iconTint,
                    imageVector = icon,
                    contentDescription = null
                )
                Text(
                    style = Typography.bodyLarge,
                    text = contentDescription,
                    color= textColor
                )
            }
        }

}

@Preview
@Composable
fun CustomButtonPreview(){
    ActionIcon(
        onClick = {},
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        icon = Icons.Default.Edit,
        contentDescription = "Edit"
    )
}