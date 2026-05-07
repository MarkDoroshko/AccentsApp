package com.example.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.domain.entity.Category
import com.example.presentation.screen.categories.CategoriesScreen
import com.example.presentation.screen.quiz.QuizNavArgs
import com.example.presentation.screen.quiz.QuizScreen
import com.example.presentation.screen.result.ResultScreen
import com.example.presentation.screen.splash.SplashScreen
import com.example.presentation.screen.start.StartScreen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Start : Screen("start")
    data object Categories : Screen("categories")
    data object Quiz : Screen("quiz/{${QuizNavArgs.CATEGORY_ARG}}") {
        fun build(category: Category) = "quiz/${category.name}"
    }
    data object Result : Screen("result")
}

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Splash.route
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    onContinue = {
                        navController.navigate(Screen.Start.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Start.route) {
                StartScreen(
                    onStart = { navController.navigate(Screen.Categories.route) }
                )
            }

            composable(Screen.Categories.route) {
                CategoriesScreen(
                    onBack = { navController.popBackStack() },
                    onPick = { category ->
                        navController.navigate(Screen.Quiz.build(category))
                    }
                )
            }

            composable(
                route = Screen.Quiz.route,
                arguments = listOf(
                    navArgument(QuizNavArgs.CATEGORY_ARG) { type = NavType.StringType }
                )
            ) {
                QuizScreen(
                    onHome = {
                        navController.popBackStack(Screen.Start.route, inclusive = false)
                    },
                    onFinish = {
                        navController.navigate(Screen.Result.route) {
                            popUpTo(Screen.Categories.route)
                        }
                    }
                )
            }

            composable(Screen.Result.route) {
                ResultScreen(
                    onPlayAgain = { category ->
                        navController.navigate(Screen.Quiz.build(category)) {
                            popUpTo(Screen.Categories.route)
                        }
                    },
                    onHome = {
                        navController.popBackStack(Screen.Start.route, inclusive = false)
                    }
                )
            }
        }
    }
}
