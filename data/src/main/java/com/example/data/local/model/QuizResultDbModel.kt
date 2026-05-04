package com.example.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResultDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: String,
    val correct: Int,
    val wrong: Int,
    val total: Int,
    val bestStreak: Int,
    val completedAt: Long
)
