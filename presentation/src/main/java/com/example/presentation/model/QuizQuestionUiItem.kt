package com.example.presentation.model

data class QuizQuestionUiItem(
    val wordId: Int,
    val word: String,
    val partOfSpeech: String,
    val options: List<String>,
    val correctOptionIndex: Int
)
