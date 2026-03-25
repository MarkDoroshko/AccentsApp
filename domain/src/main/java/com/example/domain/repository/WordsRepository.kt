package com.example.domain.repository

import com.example.domain.entity.Word

interface WordsRepository {
    suspend fun getWords(): List<Word>
}