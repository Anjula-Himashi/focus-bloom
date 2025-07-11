import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import com.example.focusbloom.ui.components.TaskItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onAddTaskClicked: () -> Unit = {}
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val allTasks = remember {
        mutableStateListOf(
            Task("Study Kotlin", LocalDate.now(), false),
            Task("Read a book", LocalDate.now(), false),
            Task("Read 1 book", LocalDate.now(), false),
            Task("Read 2 book", LocalDate.now(), false),
            Task("Read 3 book", LocalDate.now(), false),
            Task("Read 4 book", LocalDate.now(), false),
            Task("Read 5 book", LocalDate.now(), false),
            Task("Workout", LocalDate.now().minusDays(1), true),
            Task("Call Mom", LocalDate.now().plusDays(1), false)
        )
    }

    val tasksForDate = allTasks.filter { it.date == selectedDate }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Your Tasks") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClicked
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Placeholder Calendar
            Text(
                "Calendar",
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 8.dp)
            ) {
                val today = LocalDate.now()
                val yesterday = today.minusDays(1)
                val tomorrow = today.plusDays(1)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
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
                val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        progress = progress,
                        modifier = Modifier.size(100.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 8.dp,
                        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
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
}

@Composable
fun DateToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    shape: RoundedCornerShape,
    modifier: Modifier = Modifier
) {
    val selectedBackground = Color(0xFF388E3C)
    val unselectedBackground = Color.Transparent
    val selectedTextColor = Color.White
    val unselectedTextColor = MaterialTheme.colorScheme.onSurface

    Surface(
        shape = shape,
        color = if (selected) selectedBackground else unselectedBackground,
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier.height(48.dp)
    ) {
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = if (selected) selectedTextColor else unselectedTextColor
            ),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text)
        }
    }
}