package com.example.focusbloom.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

sealed class BottomNavItem(val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Filled.Home)
    object Timer : BottomNavItem("Timer", Icons.Filled.Schedule)
//    object Add : BottomNavItem("Add", Icons.Filled.NoteAdd)
    object Avatar : BottomNavItem("Avatar", Icons.Filled.AccountBox)
    object Profile : BottomNavItem("Profile", Icons.Filled.ManageAccounts)


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
        NavigationBar(
            containerColor = Color(0xFFE8F5F9) // background of nav bar
        ) {
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
                    alwaysShowLabel = false,
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = if (isSelected) {
                                Modifier
                                    .offset(y = (-4).dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            } else Modifier
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(if (isSelected) 32.dp else 24.dp),
                                tint = if (isSelected)
                                    MaterialTheme.colorScheme.primary
                                else
                                    LocalContentColor.current
                            )
                            Text(
                                item.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected)
                                    MaterialTheme.colorScheme.primary
                                else
                                    LocalContentColor.current
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )

            }
        }
    }
}