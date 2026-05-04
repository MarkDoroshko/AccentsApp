package com.example.presentation.screen.start

data class StartState(
    val streak: Int = 0,
    val totalWords: Int = 0
)

sealed interface StartIntent {
    data object Start : StartIntent
}

sealed interface StartEffect {
    data object NavigateToCategories : StartEffect
}
