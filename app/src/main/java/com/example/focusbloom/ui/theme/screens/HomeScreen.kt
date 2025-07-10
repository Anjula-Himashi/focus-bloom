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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll



@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {
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
                title = {
                    Text("Your Tasks")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // You can navigate or open a dialog
                    // For now, just print to log or show snackbar if you wish
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // ⬅️ Add this
                .padding(innerPadding)
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

// Progress display widget
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Circular progress bar with percentage
                    CircularProgressIndicator(
                    progress = progress,
                    modifier = Modifier.size(100.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 8.dp,
                        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)

//                    trackColor = COMPILED_CODE,
//                    strokeCap = COMPILED_CODE,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Text showing number of completed tasks and percentage
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