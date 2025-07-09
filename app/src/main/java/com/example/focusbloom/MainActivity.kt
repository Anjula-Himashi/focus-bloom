package com.example.focusbloom

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.focusbloom.ui.screens.TaskListScreen
import com.example.focusbloom.ui.theme.FocusBloomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                is BottomNavItem.Timer -> Text("Timer Screen")
                is BottomNavItem.Add -> Text("Add Screen")
                is BottomNavItem.Avatar -> Text("Avatar Screen")
                is BottomNavItem.Profile -> Text("Profile Screen")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    FocusBloomTheme {
        MainScreen()
    }
}