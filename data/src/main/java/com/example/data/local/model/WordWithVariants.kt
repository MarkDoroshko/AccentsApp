package com.example.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithVariants(
    @Embedded
    val word: WordDbModel,

    @Relation(
        parentColumn = "id",
        entityColumn = "wordId"
    )
    val variants: List<VariantDbModel>
)
