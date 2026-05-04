package com.example.presentation.screen.result

import com.example.domain.entity.Category

data class ResultState(
    val category: Category = Category.ALL,
    val categoryLabel: String = "",
    val correct: Int = 0,
    val wrong: Int = 0,
    val total: Int = 0,
    val bestStreak: Int = 0,
    val isLoading: Boolean = true
) {
    val percent: Int get() = if (total == 0) 0 else (correct * 100) / total
    val tier: String get() = when {
        percent >= 90 -> "Превосходно!"
        percent >= 70 -> "Хороший результат"
        percent >= 50 -> "Неплохо"
        else -> "Надо подтянуть"
    }
}

sealed interface ResultIntent {
    data object PlayAgain : ResultIntent
    data object GoHome : ResultIntent
}

sealed interface ResultEffect {
    data class PlayAgainWithAd(val category: Category) : ResultEffect
    data object NavigateHome : ResultEffect
}
