package com.example.tasksapp.presenation.allTasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tasksapp.R

@Composable
fun CustomCheckedIcon(
    modifier: Modifier = Modifier,
    size: Dp,
){

    Box(modifier = modifier
        .border(1.dp, Color.White, CircleShape)
        .height(size)
        .width(size)
//        .drawBehind{
//            drawArc(
//                color = Color.Blue,
//                startAngle = 0f,
//                sweepAngle = 360f,
//                useCenter = false
//            )
//        }
        .clip(
            CircleShape
        )
        .background(Color.Blue)
    ){
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_check),
                contentDescription = "checked",
                tint = Color.White
            )
        }

    }
}

