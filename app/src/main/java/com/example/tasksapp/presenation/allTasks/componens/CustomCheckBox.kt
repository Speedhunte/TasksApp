package com.example.tasksapp.presenation.allTasks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tasksapp.R
import com.example.tasksapp.ui.theme.checkBoxColor

@Composable
fun CustomCheckBox(
    modifier: Modifier = Modifier,
    size: Dp,
    isChecked: Boolean,
    onChecked: ()->Unit,
){

    val onPrimaryColor = if(isChecked) checkBoxColor else  Color.Gray

    val animatedColor by animateColorAsState(
        if (isChecked) checkBoxColor else Color.White ,
        label = "color"
    )

    Box(modifier = Modifier
        .border(1.dp, onPrimaryColor, CircleShape)
        .height(size)
        .width(size)
//        .drawBehind {
//            drawArc(
//                color = animatedColor,
//                startAngle = 0f,
//                sweepAngle = 360f,
//                useCenter = false
//            )
//        }
        .clip(
            CircleShape
        )
        .background(animatedColor)
        .clickable {
            onChecked()
        }
    ){
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {

            AnimatedVisibility(
                isChecked,
                enter = scaleIn(initialScale = 0.5f),
                exit = shrinkOut(shrinkTowards = Alignment.Center)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = "checked",
                    tint = Color.White
                )
            }
        }

    }
}


@Preview
@Composable
private fun previewCustomCheckBox() {
    CustomCheckBox(
        size=20.dp,
        isChecked = false,
        onChecked = {}
    )
}
