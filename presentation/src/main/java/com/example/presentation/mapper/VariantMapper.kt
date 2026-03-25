package com.example.presentation.mapper

import com.example.domain.entity.Variant
import com.example.presentation.model.VariantUiItem

fun Variant.toUiItem(): VariantUiItem {
    return VariantUiItem(
        id = id,
        variant = variant,
        isCorrect = isCorrect
    )
}