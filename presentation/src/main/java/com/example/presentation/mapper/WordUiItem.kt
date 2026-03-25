package com.example.presentation.mapper

import com.example.domain.entity.Word
import com.example.presentation.model.WordUiItem

fun Word.toUiItem(): WordUiItem {
    return WordUiItem(
        id = id,
        word = word,
        variants = variants.map { it.toUiItem() }
    )
}