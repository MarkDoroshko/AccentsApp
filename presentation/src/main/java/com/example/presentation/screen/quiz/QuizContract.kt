package com.example.presentation.screen.quiz

import com.example.domain.entity.Category
import com.example.presentation.model.QuizQuestionUiItem

data class QuizState(
    val category: Category = Category.ALL,
    val categoryLabel: String = "",
    val deck: List<QuizQuestionUiItem> = emptyList(),
    val position: Int = 0,
    val pickedOption: Int? = null,
    val correct: Int = 0,
    val wrong: Int = 0,
    val streak: Int = 0,
    val bestStreak: Int = 0,
    val isLoading: Boolean = true
) {
    val currentQuestion: QuizQuestionUiItem? get() = deck.getOrNull(position)
    val total: Int get() = deck.size
}

sealed interface QuizIntent {
    data class Pick(val optionIndex: Int) : QuizIntent
    data object GoHome : QuizIntent
}

sealed interface QuizEffect {
    data object NavigateHome : QuizEffect
    data object NavigateToResult : QuizEffect
}
