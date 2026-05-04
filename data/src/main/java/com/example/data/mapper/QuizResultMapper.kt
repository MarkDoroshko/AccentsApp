package com.example.data.mapper

import com.example.data.local.model.QuizResultDbModel
import com.example.domain.entity.Category
import com.example.domain.entity.QuizResult

fun QuizResultDbModel.toEntity(): QuizResult {
    return QuizResult(
        category = runCatching { Category.valueOf(category) }.getOrDefault(Category.ALL),
        correct = correct,
        wrong = wrong,
        total = total,
        bestStreak = bestStreak,
        completedAt = completedAt
    )
}

fun QuizResult.toDbModel(): QuizResultDbModel {
    return QuizResultDbModel(
        category = category.name,
        correct = correct,
        wrong = wrong,
        total = total,
        bestStreak = bestStreak,
        completedAt = completedAt
    )
}
