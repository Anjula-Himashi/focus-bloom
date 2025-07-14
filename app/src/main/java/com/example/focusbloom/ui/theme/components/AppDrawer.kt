package com.example.focusbloom.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    onNavigate: (String) -> Unit,
    onClose: () -> Unit
) {
    ModalDrawerSheet {
        Text(
            text = "Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Divider()
        NavigationDrawerItem(
            label = { Text("Home") },
            selected = false,
            onClick = {
                onNavigate("home")
                onClose()
            }
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            onClick = {
                onNavigate("settings")
                onClose()
            }
        )
    }
}