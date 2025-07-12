@file:OptIn(ExperimentalMaterial3Api::class)

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimerScreen(
    taskName: String,
    onBack: () -> Unit
) {
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0L) }
    var displayAnalog by remember { mutableStateOf(false) }

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
                text = taskName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Toggle between Analog and Digital
            Box(
                modifier = Modifier
                    .size(200.dp),
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
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { isRunning = true },
                    enabled = !isRunning
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Start", tint = Color.Green)
                }

                IconButton(
                    onClick = { isRunning = false },
                    enabled = isRunning
                ) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause", tint = Color.Yellow)
                }

                IconButton(
                    onClick = {
                        isRunning = false
                        elapsedTime = 0
                    }
                ) {
                    Icon(Icons.Default.Stop, contentDescription = "Stop", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun DigitalClock(elapsedSeconds: Long) {
    val hours = elapsedSeconds / 3600
    val minutes = (elapsedSeconds % 3600) / 60
    val seconds = elapsedSeconds % 60

    Text(
        text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
        fontSize = 36.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun AnalogClock(elapsedSeconds: Long) {
    val angle = (elapsedSeconds % 60) * 6f // Each second = 6 degrees
    Canvas(modifier = Modifier.fillMaxSize()) {
        val radius = size.minDimension / 2
        drawCircle(Color.LightGray, radius = radius, center = center)
        drawLine(
            color = Color.Black,
            start = center,
            end = Offset(
                x = center.x + radius * 0.8f * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat(),
                y = center.y - radius * 0.8f * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
            ),
            strokeWidth = 4.dp.toPx()
        )
    }
}