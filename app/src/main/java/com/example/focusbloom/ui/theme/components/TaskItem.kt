package com.example.focusbloom.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.focusbloom.ui.theme.model.Task


@Composable
fun TaskItem(
    task: Task,
    onToggleDone: () -> Unit,
    onStartClick: () -> Unit = {}
) {
    val activeColor = Color(0xFFFFFFFF)      // White
    val completedColor = Color(0xFFF0F0F0)   // Light gray
    val textColor = if (task.isDone) Color(0xFF777777) else Color(0xFF333333) // Dark gray text
    val checkColor = Color(0xFF388E3C)       // Medium green for brand consistency

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isDone) completedColor else activeColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onToggleDone() },
                colors = CheckboxDefaults.colors(
                    checkedColor = checkColor,
                    uncheckedColor = checkColor
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Task Title
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge.copy(color = textColor)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Start Button with Icon
            TextButton(
                onClick = onStartClick,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = checkColor
                )
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Start",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}