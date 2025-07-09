package com.example.focusbloom.ui.components

import Task
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskItem(
    task: Task,
    onToggleDone: () -> Unit
) {
    val activeColor = Color(0xFFE8F5E9)      // Soft mint green
    val completedColor = Color(0xFFC8E6C9)   // Pale green
    val textColor = if (task.isDone) Color.DarkGray else Color.Black
    val checkColor = Color(0xFF388E3C)       // Leafy green

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isDone) completedColor else activeColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onToggleDone() },
                colors = CheckboxDefaults.colors(
                    checkedColor = checkColor,
                    uncheckedColor = checkColor
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge.copy(color = textColor)
            )
        }
    }
}