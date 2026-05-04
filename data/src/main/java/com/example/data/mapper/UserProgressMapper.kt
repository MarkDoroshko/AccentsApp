package com.example.data.mapper

import com.example.data.local.model.UserProgressDbModel
import com.example.domain.entity.UserProgress

fun UserProgressDbModel.toEntity(): UserProgress {
    return UserProgress(
        currentStreak = currentStreak,
        bestStreak = bestStreak,
        lastPlayedEpochDay = lastPlayedEpochDay,
        totalQuizzes = totalQuizzes,
        totalCorrect = totalCorrect,
        totalWrong = totalWrong
    )
}

fun UserProgress.toDbModel(): UserProgressDbModel {
    return UserProgressDbModel(
        id = UserProgressDbModel.SINGLE_ROW_ID,
        currentStreak = currentStreak,
        bestStreak = bestStreak,
        lastPlayedEpochDay = lastPlayedEpochDay,
        totalQuizzes = totalQuizzes,
        totalCorrect = totalCorrect,
        totalWrong = totalWrong
    )
}
