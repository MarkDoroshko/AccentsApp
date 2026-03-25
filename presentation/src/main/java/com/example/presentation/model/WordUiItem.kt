package com.example.presentation.model

data class WordUiItem(
    val id: Int,
    val word: String,
    val variants: List<VariantUiItem>
)
