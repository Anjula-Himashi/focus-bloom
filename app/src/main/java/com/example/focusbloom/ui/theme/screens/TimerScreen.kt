@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.focusbloom.ui.theme.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimerScreen(
    taskId: String,
    onBack: () -> Unit
) {
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0L) }
    var displayAnalog by remember { mutableStateOf(false) }
    // State to track if the timer has ever been started
    var hasStarted by remember { mutableStateOf(false) }


    // Tick every second
    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            elapsedTime += 1
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = taskId,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "work",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Toggle between Analog and Digital
            Box(
                modifier = Modifier
                    .size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayAnalog) {
                    AnalogClock(elapsedTime)
                } else {
                    DigitalClock(elapsedTime)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { displayAnalog = !displayAnalog }) {
                Text(if (displayAnalog) "Switch to Digital" else "Switch to Analog")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Control Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)

            ) {
                Box(
                    modifier = Modifier
                        .border(2.dp, Color.Gray, shape = RoundedCornerShape(50))
                        .padding(8.dp)
                ) {
                    IconButton(
                        onClick = {
                            isRunning = !isRunning // Toggle state
                        }
                    ) {
                        Icon(
                            imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isRunning) "Pause" else "Start",
                            tint = if (isRunning) Color.Black else Color.Black,
                                    modifier = Modifier.size(48.dp)

                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .border(2.dp, Color.Gray, shape = RoundedCornerShape(50))
                        .padding(8.dp)
                ) {
                    IconButton(
                        onClick = {
                            isRunning = false
                            elapsedTime = 0
                        }
                    ) {
                        Icon(
                            Icons.Default.Stop,
                            contentDescription = "Stop",
                            tint = Color.Black,
                            modifier = Modifier.size(48.dp)

                        )
                    }
                }
            }


        }
    }
}

@Composable
fun DigitalClock(elapsedSeconds: Long) {
    val hours = (elapsedSeconds / 3600) % 24
    val minutes = (elapsedSeconds / 60) % 60
    val seconds = elapsedSeconds % 60

    Box(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .border(
                width = 4.dp,
                color = Color(0xFFC0C0C0),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = Color(0xFF222222),
                shape = RoundedCornerShape(16.dp)
            )
//            .size(width = 550.dp, height = 220.dp),
        ,contentAlignment = Alignment.Center
    ) {
        Text(
            text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



@Composable
fun AnalogClock(elapsedSeconds: Long) {
    val seconds = elapsedSeconds % 60
    val minutes = (elapsedSeconds / 60) % 60
    val hours = (elapsedSeconds / 3600) % 12

    // Calculate angles
    val secondAngle = seconds * 6f
    val minuteAngle = minutes * 6f + seconds * 0.1f // each sec moves minute hand
    val hourAngle = hours * 30f + minutes * 0.5f    // each min moves hour hand

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .border(
                width = 4.dp,
                color = Color(0xFFC0C0C0),
                shape = RoundedCornerShape(150.dp)
            )
    ) {
        val radius = size.minDimension / 2
        val bezelRadius = radius - 8.dp.toPx()
        val fontSize = 14.sp.toPx()

        // Circle
        drawCircle(
            color = Color.LightGray,
            radius = bezelRadius,
            center = center
        )

        // Numbers 1–12
        for (i in 1..12) {
            val numberAngle = Math.toRadians((i * 30 - 90).toDouble())
            val numberRadius = bezelRadius - 24.dp.toPx()
            val numberOffset = Offset(
                x = center.x + numberRadius * kotlin.math.cos(numberAngle).toFloat(),
                y = center.y + numberRadius * kotlin.math.sin(numberAngle).toFloat()
            )

            drawContext.canvas.nativeCanvas.drawText(
                i.toString(),
                numberOffset.x,
                numberOffset.y + fontSize / 3,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = fontSize
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

        // Hour hand
        drawLine(
            color = Color.Black,
            start = center,
            end = Offset(
                x = center.x + bezelRadius * 0.5f * kotlin.math.sin(Math.toRadians(hourAngle.toDouble())).toFloat(),
                y = center.y - bezelRadius * 0.5f * kotlin.math.cos(Math.toRadians(hourAngle.toDouble())).toFloat()
            ),
            strokeWidth = 6.dp.toPx()
        )

        // Minute hand
        drawLine(
            color = Color.DarkGray,
            start = center,
            end = Offset(
                x = center.x + bezelRadius * 0.7f * kotlin.math.sin(Math.toRadians(minuteAngle.toDouble())).toFloat(),
                y = center.y - bezelRadius * 0.7f * kotlin.math.cos(Math.toRadians(minuteAngle.toDouble())).toFloat()
            ),
            strokeWidth = 4.dp.toPx()
        )

        // Second hand
        drawLine(
            color = Color.Red,
            start = center,
            end = Offset(
                x = center.x + bezelRadius * 0.85f * kotlin.math.sin(Math.toRadians(secondAngle.toDouble())).toFloat(),
                y = center.y - bezelRadius * 0.85f * kotlin.math.cos(Math.toRadians(secondAngle.toDouble())).toFloat()
            ),
            strokeWidth = 2.dp.toPx()
        )

        // Center pivot
        drawCircle(
            color = Color.Black,
            radius = 6.dp.toPx(),
            center = center
        )
    }
}