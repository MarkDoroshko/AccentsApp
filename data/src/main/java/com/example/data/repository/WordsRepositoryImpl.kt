package com.example.data.repository

import com.example.data.local.dao.WordDao
import com.example.data.mapper.toEntity
import com.example.domain.entity.Word
import com.example.domain.repository.WordsRepository
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val wordDao: WordDao
) : WordsRepository {
    override suspend fun getWords(): List<Word> {
        return wordDao.getAllWordsWithVariant().map { it.toEntity() }
    }
}