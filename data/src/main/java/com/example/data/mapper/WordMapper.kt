package com.example.data.mapper

import com.example.data.local.model.VariantDbModel
import com.example.data.local.model.WordWithVariants
import com.example.domain.entity.PartOfSpeech
import com.example.domain.entity.Variant
import com.example.domain.entity.Word

fun WordWithVariants.toEntity(): Word {
    return Word(
        id = word.id,
        word = word.word,
        partOfSpeech = word.partOfSpeech.toPartOfSpeech(),
        variants = variants.map { it.toEntity() }
    )
}

fun VariantDbModel.toEntity(): Variant {
    return Variant(
        id = id,
        variant = variant,
        isCorrect = isCorrect
    )
}

private fun String.toPartOfSpeech(): PartOfSpeech = when (uppercase()) {
    "NOUN" -> PartOfSpeech.NOUN
    "VERB" -> PartOfSpeech.VERB
    "ADJECTIVE" -> PartOfSpeech.ADJECTIVE
    "PARTICIPLE" -> PartOfSpeech.PARTICIPLE
    "ADVERB" -> PartOfSpeech.ADVERB
    "GERUND" -> PartOfSpeech.GERUND
    else -> PartOfSpeech.NOUN
}
