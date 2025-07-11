//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.kizitonwose.calendar.compose.MonthCalendar
//import com.kizitonwose.calendar.compose.rememberMonthCalendarState
//import com.kizitonwose.calendar.core.CalendarDay
//import com.kizitonwose.calendar.core.YearMonth
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//
//
//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreenWithCalendar() {
//    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
//
//    val allTasks = remember {
//        mutableStateListOf(
//            Task("Study Kotlin", LocalDate.now(), false),
//            Task("Read a book", LocalDate.now(), false),
//            Task("Workout", LocalDate.now().minusDays(1), true),
//            Task("Call Mom", LocalDate.now().plusDays(1), false)
//        )
//    }
//
//    val tasksForDate = allTasks.filter { it.date == selectedDate }
//
//    val currentMonth = remember { YearMonth.now() }
//    val calendarState = rememberMonthCalendarState(
//        currentMonth = currentMonth,
//        firstVisibleMonth = currentMonth.minusMonths(12),
//        lastVisibleMonth = currentMonth.plusMonths(12)
//    )
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(title = { Text("Your Tasks") })
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = { /* TODO: Add task */ }) {
//                Icon(Icons.Filled.Add, contentDescription = "Add Task")
//            }
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//                .padding(innerPadding)
//                .padding(16.dp)
//        ) {
//
//            // Calendar with marked tasks
//            MonthCalendar(
//                state = calendarState,
//                dayContent = { day ->
//
//                    val hasTasks = allTasks.any { it.date == day.date }
//                    DayContent(day = day, hasTasks = hasTasks, isSelected = day.date == selectedDate) {
//                        selectedDate = day.date
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                "Tasks for ${selectedDate.format(DateTimeFormatter.ofPattern("EEEE, MMM d"))}",
//                style = MaterialTheme.typography.titleMedium,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            if (tasksForDate.isEmpty()) {
//                Text(
//                    "No tasks for this date.",
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//            } else {
//                val totalTasks = tasksForDate.size
//                val completedTasks = tasksForDate.count { it.isDone }
//                val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 24.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    CircularProgressIndicator(
//                        progress = progress,
//                        modifier = Modifier.size(100.dp),
//                        color = MaterialTheme.colorScheme.primary,
//                        strokeWidth = 8.dp,
//                        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        text = "$completedTasks of $totalTasks tasks completed (${(progress * 100).toInt()}%)",
//                        style = MaterialTheme.typography.titleMedium,
//                        textAlign = TextAlign.Center
//                    )
//                }
//
//                tasksForDate.forEach { task ->
//                    TaskItem(
//                        task = task,
//                        onToggleDone = {
//                            val index = allTasks.indexOf(task)
//                            if (index != -1) {
//                                allTasks[index] = task.copy(isDone = !task.isDone)
//                            }
//                        }
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun DayContent(
//    day: CalendarDay,
//    hasTasks: Boolean,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    val background = when {
//        isSelected -> MaterialTheme.colorScheme.primary
//        else -> Color.Transparent
//    }
//
//    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
//
//    Box(
//        modifier = Modifier
//            .size(40.dp)
//            .background(background, shape = MaterialTheme.shapes.small)
//            .padding(4.dp)
//            .then(Modifier),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(4.dp)
//                .clickable { onClick() }
//        ) {
//            Text(
//                text = day.date.dayOfMonth.toString(),
//                color = textColor,
//                style = MaterialTheme.typography.bodyMedium
//            )
//            if (hasTasks) {
//                Spacer(modifier = Modifier.height(2.dp))
//                Box(
//                    modifier = Modifier
//                        .size(6.dp)
//                        .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
//                )
//            }
//        }
//    }
//}