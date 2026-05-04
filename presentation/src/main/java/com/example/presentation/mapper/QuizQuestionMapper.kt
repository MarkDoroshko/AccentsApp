package com.example.presentation.mapper

import com.example.domain.entity.Word
import com.example.presentation.model.QuizQuestionUiItem

fun Word.toQuizQuestion(): QuizQuestionUiItem {
    val shuffledVariants = variants.shuffled()
    val correctIndex = shuffledVariants.indexOfFirst { it.isCorrect }.coerceAtLeast(0)
    return QuizQuestionUiItem(
        wordId = id,
        word = word,
        partOfSpeech = partOfSpeech.name,
        options = shuffledVariants.map { it.variant },
        correctOptionIndex = correctIndex
    )
}
