package com.example.focusbloom

import AddScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.focusbloom.navigation.BottomNavBar
import com.example.focusbloom.navigation.BottomNavItem
import com.example.focusbloom.ui.components.TaskItem
import com.example.focusbloom.ui.screens.HomeScreen
import com.example.focusbloom.ui.theme.model.Task
import com.example.focusbloom.ui.theme.screens.AvatarScreen
import com.example.focusbloom.ui.theme.screens.ProfileScreen
import com.example.focusbloom.ui.theme.screens.TimerScreen
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Force light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContent {
            FocusBloomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }
    var showAddScreen by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedItem,
                onItemSelected = {
                    selectedItem = it
                    showAddScreen = false // reset when switching tabs
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when {
                showAddScreen -> AddScreen(
                    onTaskSaved = {
                        // You can add logic here to save the task
                        showAddScreen = false
                    },
                    onCancel = {
                        showAddScreen = false
                    }
                )
                selectedItem is BottomNavItem.Home -> HomeScreen(
                    onAddTaskClicked = { showAddScreen = true }
                )
                selectedItem is BottomNavItem.Timer -> TimerScreen(
                    taskId = "Study Kotlin",
                    onBack = { /* do nothing */ }
                )

                selectedItem is BottomNavItem.Avatar -> AvatarScreen()
                selectedItem is BottomNavItem.Profile -> ProfileScreen("Anjula Himashi",200,2,250.5)
            }
        }
    }
}


/**
 * Custom Theme
 */
@Composable
fun FocusBloomTheme(content: @Composable () -> Unit) {
    val LightColors = lightColorScheme(
        primary = Color(0xFF388E3C),         // Deep green primary
        surface = Color(0xFFE8F5E9),         // Light green surfaces
        background = Color(0xFFF1FDF1),      // Very light green background
        onSurface = Color.Black              // Text on surface
    )

    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}

/**
 * Previews
 */
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    FocusBloomTheme {
        MainScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewTaskItem() {
    Column {
        TaskItem(
            task = Task("Buy groceries", LocalDate.now(), false),
            onToggleDone = {}
        )
        TaskItem(
            task = Task("Read a book", LocalDate.now(), true),
            onToggleDone = {}
        )
    }
}