package com.example.focusbloom.ui.components

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class BottomNavItem(val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Filled.Dashboard)
    object Timer : BottomNavItem("Timer", Icons.Filled.AccessTime)
    object Add : BottomNavItem("Add", Icons.Filled.Add) // If you keep Add elsewhere
    object Avatar : BottomNavItem("Avatar", Icons.Filled.AccountCircle)
    object Profile : BottomNavItem("Profile", Icons.Filled.Settings)
}


@Composable
fun BottomNavBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    Surface(
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

    ) {
        NavigationBar (containerColor = _root_ide_package_.androidx.compose.ui.graphics.Color(0xFFE8F5F9)){
            val items = listOf(
                BottomNavItem.Home,
                BottomNavItem.Timer,
                BottomNavItem.Avatar,
                BottomNavItem.Profile
            )

            items.forEach { item ->
                val isSelected = item == selectedItem

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemSelected(item) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier
                                .size(if (isSelected)  32.dp else 24.dp) // Larger when selected
                                .offset(y = if (isSelected) (-4).dp else 0.dp) // Move up when selected
                        )
                    },
                    label = { Text(item.label) }
                )
            }
        }
    }
}