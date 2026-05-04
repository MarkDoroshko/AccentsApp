package com.example.domain.entity

data class QuizResult(
    val category: Category,
    val correct: Int,
    val wrong: Int,
    val total: Int,
    val bestStreak: Int,
    val completedAt: Long
)
