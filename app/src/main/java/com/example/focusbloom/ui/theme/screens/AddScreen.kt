@file:OptIn(ExperimentalMaterial3Api::class)

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import java.time.LocalDate

/**
 * A screen to add a new Task.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddScreen(
    onTaskSaved: (Task) -> Unit,
    onCancel: () -> Unit
) {
    var taskName by remember { mutableStateOf("") }
    var taskType by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentSize(Alignment.TopCenter)
    ) {
        Text(
            text = "Add New Task",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Task Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = taskType,
            onValueChange = { taskType = it },
            label = { Text("Task Type / Category") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )

        Spacer(modifier = Modifier.height(8.dp))

        DatePicker(
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    if (taskName.isNotBlank()) {
                        onTaskSaved(Task(taskName, selectedDate, false))
                    }
                }
            ) {
                Text("Save Task")
            }
        }
    }
}

/**
 * Simple date picker replacement: just buttons to adjust date.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Date: ${selectedDate.toString()}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Button(onClick = { onDateSelected(selectedDate.minusDays(1)) }) {
                Text("-1 day")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onDateSelected(selectedDate.plusDays(1)) }) {
                Text("+1 day")
            }
        }
    }
}