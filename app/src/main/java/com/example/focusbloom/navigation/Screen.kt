package com.example.focusbloom.navigation

sealed class Screen(val route: String) {
    object SignIn : Screen("sign_in")
    object Main : Screen("main")
}