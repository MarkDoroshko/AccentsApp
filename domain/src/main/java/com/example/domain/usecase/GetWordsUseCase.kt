package com.example.domain.usecase

import com.example.domain.entity.Word
import com.example.domain.repository.WordsRepository
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    suspend operator fun invoke(): List<Word> {
        return wordsRepository.getWords()
    }
}