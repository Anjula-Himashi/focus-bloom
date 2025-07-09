package com.example.focusbloom

import AddScreen
import AvatarScreen
import HomeScreen
import ProfileScreen
import Task
import TimerScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.focusbloom.ui.components.BottomNavBar
import com.example.focusbloom.ui.components.BottomNavItem
import com.example.focusbloom.ui.components.TaskItem
import com.example.focusbloom.ui.theme.FocusBloomTheme
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

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        }
    ) { innerPadding ->
        // Your screen content depending on selectedItem
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                is BottomNavItem.Home -> HomeScreen()
                is BottomNavItem.Timer -> TimerScreen()
                is BottomNavItem.Add -> AddScreen()
                is BottomNavItem.Avatar -> AvatarScreen()
                is BottomNavItem.Profile -> ProfileScreen()
            }
        }
    }
}



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
            task = Task("Buy groceries",LocalDate.now(), false),
            onToggleDone = {}
        )
        TaskItem(
            task = Task("Read a book",LocalDate.now(), true),
            onToggleDone = {}
        )
    }
}