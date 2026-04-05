package com.example.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.screen.greeting.GreetingScreen
import com.example.presentation.screen.words.WordsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (currentRoute == Screen.Words.route) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
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