package com.example.focusbloom.ui.theme.model

import java.time.LocalDate

data class Task(
    val title: String,
    val date: LocalDate,
    val isDone: Boolean,
    val type: String = ""
)