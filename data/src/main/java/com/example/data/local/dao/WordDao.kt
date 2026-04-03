package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.model.WordWithVariants

@Dao
interface WordDao {
    @Transaction
    @Query("SELECT * FROM words WHERE id = :wordId")
    suspend fun getWordWithVariants(wordId: Int): WordWithVariants

    @Transaction
    @Query("SELECT * FROM words")
    suspend fun getAllWordsWithVariant(): List<WordWithVariants>
}