package com.example.domain.usecase

import com.example.domain.entity.Category
import com.example.domain.entity.Word
import com.example.domain.repository.WordsRepository
import javax.inject.Inject

class GetQuizDeckUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    suspend operator fun invoke(category: Category, size: Int = DECK_SIZE): List<Word> {
        val pool = wordsRepository.getWordsByCategory(category)
        return pool.shuffled().take(minOf(size, pool.size))
    }

    companion object {
        const val DECK_SIZE = 10
    }
}
