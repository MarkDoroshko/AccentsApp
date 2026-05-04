package com.example.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressDbModel(
    @PrimaryKey val id: Int = SINGLE_ROW_ID,
    val currentStreak: Int,
    val bestStreak: Int,
    val lastPlayedEpochDay: Long,
    val totalQuizzes: Int,
    val totalCorrect: Int,
    val totalWrong: Int
) {
    companion object {
        const val SINGLE_ROW_ID = 1
    }
}
