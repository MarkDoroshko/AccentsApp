package com.example.domain.entity

enum class Category(val partOfSpeech: PartOfSpeech?) {
    ALL(null),
    NOUN(PartOfSpeech.NOUN),
    VERB(PartOfSpeech.VERB),
    PARTICIPLE(PartOfSpeech.PARTICIPLE),
    ADJECTIVE(PartOfSpeech.ADJECTIVE),
    ADVERB(PartOfSpeech.ADVERB),
    GERUND(PartOfSpeech.GERUND)
}
