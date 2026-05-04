package com.example.domain.usecase

import com.example.domain.entity.UserProgress
import com.example.domain.repository.UserProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserProgressUseCase @Inject constructor(
    private val userProgressRepository: UserProgressRepository
) {
    operator fun invoke(): Flow<UserProgress> = userProgressRepository.observeProgress()
}
