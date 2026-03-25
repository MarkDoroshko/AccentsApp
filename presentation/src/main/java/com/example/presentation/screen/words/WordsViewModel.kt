package com.example.presentation.screen.words

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Word
import com.example.domain.usecase.GetWordsUseCase
import com.example.presentation.mapper.toUiItem
import com.example.presentation.model.VariantUiItem
import com.example.presentation.model.WordUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WordsState())
    val state = _state.asStateFlow()

    fun loadWords() {
        runBlocking {
            val words = getWordsUseCase()
            _state.value = _state.value.copy(
                currentWord = words.map { it.toUiItem() }.random(),
                words = words.map { it.toUiItem() }
            )
        }
    }

    fun processIntent(intent: WordsIntent) {
        when (intent) {
            is WordsIntent.SelectVariant -> selectVariant(intent.variant)
        }
    }

    private fun selectVariant(variant: VariantUiItem) {
        _state.value = _state.value.copy(isSelected = true)

        _state.update { previousState ->
//            previousState.currentWord?.variants?.find { it.id == variant.id }
            previousState.copy(
                currentWord = previousState.currentWord?.copy(
                    variants = if (variant.isCorrect) {
                        previousState.currentWord.variants.map {
                            it.copy(color = if (it.isCorrect) Color.Green else it.color)
                        }
                    } else {
                        previousState.currentWord.variants.map {
                            it.copy(color = if (it.isCorrect) Color.Green else Color.Red)
                        }
                    }
                )
            )
        }

        Thread.sleep(1000)

        _state.update { previousState ->
            previousState.copy(
                currentWord = previousState.words.random(),
                words = previousState.words
            )
        }

        _state.value = _state.value.copy(isSelected = false)
    }
}

data class WordsState(
    val currentWord: WordUiItem? = null,
    val words: List<WordUiItem> = emptyList(),
    val isSelected: Boolean = false
)

sealed interface WordsIntent {
    data class SelectVariant(val variant: VariantUiItem) : WordsIntent
}