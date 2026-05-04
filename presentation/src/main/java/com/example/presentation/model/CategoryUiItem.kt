package com.example.presentation.model

import androidx.compose.ui.graphics.Color
import com.example.domain.entity.Category

data class CategoryUiItem(
    val category: Category,
    val label: String,
    val description: String,
    val initials: String,
    val color: Color
)
