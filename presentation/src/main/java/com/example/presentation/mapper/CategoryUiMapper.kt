package com.example.presentation.mapper

import com.example.domain.entity.Category
import com.example.presentation.model.CategoryUiItem
import com.example.presentation.theme.Accent
import com.example.presentation.theme.AdjectiveColor
import com.example.presentation.theme.AdverbColor
import com.example.presentation.theme.GerundColor
import com.example.presentation.theme.NounColor
import com.example.presentation.theme.ParticipleColor
import com.example.presentation.theme.VerbColor

fun Category.toUiItem(): CategoryUiItem = when (this) {
    Category.ALL -> CategoryUiItem(
        category = this,
        label = "Все слова",
        description = "Случайные слова из минимума",
        initials = "А",
        color = Accent
    )
    Category.NOUN -> CategoryUiItem(
        category = this,
        label = "Существительные",
        description = "тОрты, договОр, свЁкла",
        initials = "Сщ",
        color = NounColor
    )
    Category.VERB -> CategoryUiItem(
        category = this,
        label = "Глаголы",
        description = "звонИт, началА, понялА",
        initials = "Гл",
        color = VerbColor
    )
    Category.PARTICIPLE -> CategoryUiItem(
        category = this,
        label = "Причастия",
        description = "начАвший, налитА",
        initials = "Прч",
        color = ParticipleColor
    )
    Category.ADJECTIVE -> CategoryUiItem(
        category = this,
        label = "Прилагательные",
        description = "красИвее, кУхонный",
        initials = "Прл",
        color = AdjectiveColor
    )
    Category.ADVERB -> CategoryUiItem(
        category = this,
        label = "Наречия",
        description = "донЕльзя, исстАри",
        initials = "Нр",
        color = AdverbColor
    )
    Category.GERUND -> CategoryUiItem(
        category = this,
        label = "Деепричастия",
        description = "начАв, понЯв",
        initials = "Дп",
        color = GerundColor
    )
}
