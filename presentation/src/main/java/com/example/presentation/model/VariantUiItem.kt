package com.example.presentation.model

data class VariantUiItem(
    val id: Int,
    val variant: String,
    val isCorrect: Boolean,
    val state: VariantState = VariantState.Default
)

enum class VariantState {
    Default,
    Correct,
    Wrong
}
