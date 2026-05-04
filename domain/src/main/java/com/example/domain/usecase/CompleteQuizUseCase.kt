package com.example.domain.usecase

import com.example.domain.entity.QuizResult
import com.example.domain.repository.UserProgressRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.max

class CompleteQuizUseCase @Inject constructor(
    private val userProgressRepository: UserProgressRepository
) {
    suspend operator fun invoke(result: QuizResult) {
        val previous = userProgressRepository.getProgress()
        val today = LocalDate.now().toEpochDay()

        val newStreak = when {
            previous.lastPlayedEpochDay == today -> previous.currentStreak.coerceAtLeast(1)
            previous.lastPlayedEpochDay == today - 1L -> previous.currentStreak + 1
            else -> 1
        }

        val updated = previous.copy(
            currentStreak = newStreak,
            bestStreak = max(previous.bestStreak, max(newStreak, result.bestStreak)),
            lastPlayedEpochDay = today,
            totalQuizzes = previous.totalQuizzes + 1,
            totalCorrect = previous.totalCorrect + result.correct,
            totalWrong = previous.totalWrong + result.wrong
        )

        userProgressRepository.saveProgress(updated)
        userProgressRepository.saveQuizResult(result)
    }
}
