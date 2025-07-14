package com.example.focusbloom.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import com.example.focusbloom.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(
    title: String,
    moneyEarned: Double,
    onAddTaskClicked: () -> Unit,
    onNavigate: (String) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onNavigate = onNavigate,
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    actions = {

                            Row(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(horizontal = 12.dp)
                                    .clickable { /* action if needed */ },
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "$${String.format("%.2f", moneyEarned)}",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.width(2.dp))


                                Image(
                                    painter = painterResource(R.drawable.coins3),
                                    contentDescription = "Coins",
                                    modifier = Modifier.size(48.dp)
                                )

                            }

                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF1B5E20),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )


            },
            floatingActionButton = {
                FloatingActionButton(onClick = onAddTaskClicked) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Task")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                content()
            }
        }
    }
}