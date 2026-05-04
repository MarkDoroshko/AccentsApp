package com.example.domain.repository

import com.example.domain.entity.QuizResult
import com.example.domain.entity.UserProgress
import kotlinx.coroutines.flow.Flow

interface UserProgressRepository {
    fun observeProgress(): Flow<UserProgress>
    suspend fun getProgress(): UserProgress
    suspend fun saveProgress(progress: UserProgress)
    suspend fun saveQuizResult(result: QuizResult)
    suspend fun getRecentResults(limit: Int = 20): List<QuizResult>
}
