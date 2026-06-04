package com.example.tasksapp.presenation.allTasks.componens

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun TaskRevealWrapper(
    curTaskId: Int,
    revealedTaskId: Int?,
    onCollapse: ()->Unit={},
    onExpand: (Int)->Unit={},
    onTaskClick: (Int)->Unit={},
    actions: @Composable RowScope.()-> Unit,
    content: @Composable ()-> Unit
) {

    val offset = remember{
        Animatable(0f)
    }

    val isRevealed = revealedTaskId==curTaskId
    var iconsMenuWidth by remember {mutableFloatStateOf(
        0f
    )}

    LaunchedEffect(isRevealed) {
        if(isRevealed)
            offset.animateTo(-iconsMenuWidth)
        else
            offset.animateTo(0f)
    }

    val scope = rememberCoroutineScope ()

    Box(
        modifier = Modifier
            .padding(2.dp)
            .clip(RoundedCornerShape(10.dp))
            //.background(Color.White)
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min ),

    ){
        Row(
            modifier = Modifier
                .onSizeChanged{ iconsMenuWidth =it.width.toFloat() }
                .align (Alignment.CenterEnd)
            ,
            //horizontalArrangement = Arrangement.spacedBy(6.dp)
        ){
            actions()
        }
        Surface(
            modifier = Modifier.fillMaxSize()
                .offset{IntOffset(offset.value.roundToInt(), 0)}
                .pointerInput(curTaskId){
                    detectHorizontalDragGestures(
                    onHorizontalDrag = {_, dragAmount->
                            val newOffset =
                                (offset.value+dragAmount).coerceIn(-iconsMenuWidth, 0f)
                            scope.launch {
                                offset.snapTo(newOffset)
                            }

                        },
                        onDragEnd = {
                            scope.launch{
                                if(offset.value<-iconsMenuWidth/2){
                                    offset.animateTo(-iconsMenuWidth)
                                    onExpand(curTaskId)
                                }
                                else{
                                    offset.animateTo(0f)
                                    onCollapse()
                                }

                            }

                        }
                    )

                }

        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Unspecified),
                contentAlignment = Alignment.CenterStart
            ) {
                content()
            }
        }
    }
}