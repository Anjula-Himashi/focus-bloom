package com.example.focusbloom.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.focusbloom.R
import com.example.focusbloom.ui.theme.components.MyScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarScreen(
    onAddTaskClicked: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {
    MyScreen(
        title = "Choose Avatar",
        moneyEarned = 123.45,
        onAddTaskClicked = onAddTaskClicked,
        onNavigate = onNavigate
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Rectangle image box with aspect ratio
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f) // 70% width
                    .fillMaxHeight(0.9f) // 70% width

                    .aspectRatio(3f / 7f) // or any ratio you like
                    .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar4), // your avatar image
                    contentDescription = "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "This is your avatar",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}