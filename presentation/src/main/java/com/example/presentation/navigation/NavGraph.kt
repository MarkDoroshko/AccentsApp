package com.example.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.screen.greeting.GreetingScreen
import com.example.presentation.screen.words.WordsScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Greeting.route
        ) {
            composable(Screen.Greeting.route) {
                GreetingScreen(
                    onStart = { navController.navigate(Screen.Words.route) }
                )
            }

            composable(Screen.Words.route) {
                WordsScreen()
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Greeting : Screen("greeting")

    data object Words : Screen("words")
}