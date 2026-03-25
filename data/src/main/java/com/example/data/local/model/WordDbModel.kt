package com.example.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val word: String
)
