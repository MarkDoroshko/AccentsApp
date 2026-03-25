package com.example.presentation.model

import androidx.compose.ui.graphics.Color

data class VariantUiItem(
    val id: Int,
    val variant: String,
    val isCorrect: Boolean,
    val color: Color = Color.Gray
)
