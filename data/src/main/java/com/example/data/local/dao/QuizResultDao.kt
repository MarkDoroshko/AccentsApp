package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.local.model.QuizResultDbModel

@Dao
interface QuizResultDao {
    @Insert
    suspend fun insert(result: QuizResultDbModel)

    @Query("SELECT * FROM quiz_results ORDER BY completedAt DESC LIMIT :limit")
    suspend fun getRecent(limit: Int): List<QuizResultDbModel>
}
