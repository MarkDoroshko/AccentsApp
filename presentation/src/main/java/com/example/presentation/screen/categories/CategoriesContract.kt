package com.example.presentation.screen.categories

import com.example.domain.entity.Category
import com.example.presentation.model.CategoryUiItem

data class CategoriesState(
    val categories: List<CategoryUiItem> = emptyList()
)

sealed interface CategoriesIntent {
    data class Pick(val category: Category) : CategoriesIntent
    data object Back : CategoriesIntent
}

sealed interface CategoriesEffect {
    data class StartQuiz(val category: Category) : CategoriesEffect
    data object NavigateBack : CategoriesEffect
}
