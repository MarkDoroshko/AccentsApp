package com.example.domain.entity

data class UserProgress(
    val currentStreak: Int,
    val bestStreak: Int,
    val lastPlayedEpochDay: Long,
    val totalQuizzes: Int,
    val totalCorrect: Int,
    val totalWrong: Int
) {
    companion object {
        val Empty = UserProgress(
            currentStreak = 0,
            bestStreak = 0,
            lastPlayedEpochDay = 0L,
            totalQuizzes = 0,
            totalCorrect = 0,
            totalWrong = 0
        )
    }
}
