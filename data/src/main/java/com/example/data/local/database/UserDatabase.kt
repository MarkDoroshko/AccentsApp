package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.QuizResultDao
import com.example.data.local.dao.UserProgressDao
import com.example.data.local.model.QuizResultDbModel
import com.example.data.local.model.UserProgressDbModel

@Database(
    entities = [UserProgressDbModel::class, QuizResultDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userProgressDao(): UserProgressDao
    abstract fun quizResultDao(): QuizResultDao
}
