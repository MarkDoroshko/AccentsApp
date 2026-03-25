package com.example.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "variants_answer")
data class VariantDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val wordId: Int,
    val variant: String,
    val isCorrect: Boolean
)
