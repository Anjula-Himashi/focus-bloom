import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.focusbloom.ui.components.TaskItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    // All tasks for all dates
    val allTasks = remember {
        mutableStateListOf(
            Task("Study Kotlin", LocalDate.now(), false),
            Task("Read a book", LocalDate.now(), false),
            Task("Workout", LocalDate.now().minusDays(1), true),
            Task("Call Mom", LocalDate.now().plusDays(1), false)
        )
    }

    // Filter tasks for the selected date
    val tasksForDate = allTasks.filter { it.date == selectedDate }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Placeholder Calendar
        Text(
            "📅 Calendar (Placeholder)",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { selectedDate = selectedDate.minusDays(1) }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous Day"
                )
            }
            Text(
                text = selectedDate.format(DateTimeFormatter.ofPattern("EEEE, MMM d")),
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = { selectedDate = selectedDate.plusDays(1) }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next Day"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show tasks
        if (tasksForDate.isEmpty()) {
            Text(
                "No tasks for this date.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
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