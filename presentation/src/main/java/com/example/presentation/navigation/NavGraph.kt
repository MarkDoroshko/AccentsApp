package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.screen.greeting.GreetingScreen
import com.example.presentation.screen.words.WordsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Greeting.route
    ) {
        composable(Screen.Greeting.route) {
            GreetingScreen(
                onStart = { navController.navigate(Screen.Words.route) }
            )
        }

        composable(Screen.Words.route) {
            WordsScreen(
                onFinished = {}
            )
        }
    }
}

sealed class Screen(val route: String) {
    data object Greeting : Screen("greeting")

    data object Words : Screen("words")
}