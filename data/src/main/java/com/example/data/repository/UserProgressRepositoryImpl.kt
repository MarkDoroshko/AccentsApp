package com.example.data.repository

import com.example.data.local.dao.QuizResultDao
import com.example.data.local.dao.UserProgressDao
import com.example.data.mapper.toDbModel
import com.example.data.mapper.toEntity
import com.example.domain.entity.QuizResult
import com.example.domain.entity.UserProgress
import com.example.domain.repository.UserProgressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserProgressRepositoryImpl @Inject constructor(
    private val userProgressDao: UserProgressDao,
    private val quizResultDao: QuizResultDao
) : UserProgressRepository {
    override fun observeProgress(): Flow<UserProgress> {
        return userProgressDao.observe().map { it?.toEntity() ?: UserProgress.Empty }
    }

    override suspend fun getProgress(): UserProgress {
        return userProgressDao.get()?.toEntity() ?: UserProgress.Empty
    }

    override suspend fun saveProgress(progress: UserProgress) {
        userProgressDao.upsert(progress.toDbModel())
    }

    override suspend fun saveQuizResult(result: QuizResult) {
        quizResultDao.insert(result.toDbModel())
    }

    override suspend fun getRecentResults(limit: Int): List<QuizResult> {
        return quizResultDao.getRecent(limit).map { it.toEntity() }
    }
}
