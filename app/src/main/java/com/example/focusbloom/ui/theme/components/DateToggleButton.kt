package com.example.focusbloom.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DateToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    shape: RoundedCornerShape,
    modifier: Modifier = Modifier
) {
    val selectedBackground = Color(0xFF388E3C)
    val unselectedBackground = Color.Transparent
    val selectedTextColor = Color.White
    val unselectedTextColor = MaterialTheme.colorScheme.onSurface

    Surface(
        shape = shape,
        color = if (selected) selectedBackground else unselectedBackground,
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier.height(48.dp)
    ) {
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                contentColor = if (selected) selectedTextColor else unselectedTextColor
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text)
        }
    }
}