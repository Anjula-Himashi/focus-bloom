package com.example.focusbloom.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusbloom.ui.theme.components.DateToggleButton
import com.example.focusbloom.ui.theme.components.MyScreen
import com.example.focusbloom.ui.components.TaskItem
import com.example.focusbloom.ui.theme.model.Task
import java.time.LocalDate

//data class Task(
//    val name: String,
//    val date: LocalDate,
//    val isDone: Boolean
//)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onAddTaskClicked: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val allTasks = remember {
        mutableStateListOf(
            Task("Study Kotlin", LocalDate.now(), false),
            Task("Read a book", LocalDate.now(), false),
            Task("Read 1 book", LocalDate.now(), true),
            Task("Read 2 book", LocalDate.now(), false),
            Task("Read 3 book", LocalDate.now(), true),
            Task("Workout", LocalDate.now().minusDays(1), true),
            Task("Call Mom", LocalDate.now().plusDays(1), false)
        )
    }

    val tasksForDate = allTasks.filter { it.date == selectedDate }

    MyScreen(
        title = "Your Tasks",
        moneyEarned = 123.45,
        onAddTaskClicked = onAddTaskClicked,
        onNavigate = onNavigate
    ) {
        // Calendar placeholder
        Text(
            "Calendar",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Date selection row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            val today = LocalDate.now()
            val yesterday = today.minusDays(1)
            val tomorrow = today.plusDays(1)

            DateToggleButton(
                text = "Yesterday",
                selected = selectedDate == yesterday,
                onClick = { selectedDate = yesterday },
                shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
                modifier = Modifier.weight(1f)
            )
            DateToggleButton(
                text = "Today",
                selected = selectedDate == today,
                onClick = { selectedDate = today },
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.weight(1f)
            )
            DateToggleButton(
                text = "Tomorrow",
                selected = selectedDate == tomorrow,
                onClick = { selectedDate = tomorrow },
                shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (tasksForDate.isEmpty()) {
            Text(
                "No tasks for this date.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            val totalTasks = tasksForDate.size
            val completedTasks = tasksForDate.count { it.isDone }
            val progress =
                if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier.size(100.dp),
                    strokeWidth = 8.dp,
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$completedTasks of $totalTasks tasks completed (${(progress * 100).toInt()}%)",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            tasksForDate.forEach { task ->
                TaskItem(
                    task = task,
                    onToggleDone = {
                        val index = allTasks.indexOf(task)
                        if (index != -1) {
                            allTasks[index] = task.copy(isDone = !task.isDone)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}