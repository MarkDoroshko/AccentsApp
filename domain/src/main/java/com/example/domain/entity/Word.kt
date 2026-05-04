package com.example.domain.entity

data class Word(
    val id: Int,
    val word: String,
    val partOfSpeech: PartOfSpeech,
    val variants: List<Variant>
)
