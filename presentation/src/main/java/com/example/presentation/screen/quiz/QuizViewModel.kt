package com.example.presentation.screen.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Category
import com.example.domain.entity.QuizResult
import com.example.domain.usecase.CompleteQuizUseCase
import com.example.domain.usecase.GetQuizDeckUseCase
import com.example.presentation.mapper.toQuizQuestion
import com.example.presentation.mapper.toUiItem
import com.example.presentation.screen.quiz.QuizNavArgs.CATEGORY_ARG
import com.example.presentation.sound.SoundEffects
import com.example.presentation.sound.SoundType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizDeckUseCase: GetQuizDeckUseCase,
    private val completeQuizUseCase: CompleteQuizUseCase,
    private val soundEffects: SoundEffects,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val category: Category = runCatching {
        Category.valueOf(savedStateHandle.get<String>(CATEGORY_ARG) ?: Category.ALL.name)
    }.getOrDefault(Category.ALL)

    private val _state = MutableStateFlow(
        QuizState(
            category = category,
            categoryLabel = category.toUiItem().label
        )
    )
    val state = _state.asStateFlow()

    private val _effect = Channel<QuizEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        loadDeck()
    }

    private fun loadDeck() {
        viewModelScope.launch {
            val deck = getQuizDeckUseCase(category).map { it.toQuizQuestion() }
            _state.update {
                it.copy(deck = deck, isLoading = false, position = 0, pickedOption = null,
                    correct = 0, wrong = 0, streak = 0, bestStreak = 0)
            }
        }
    }

    fun processIntent(intent: QuizIntent) {
        when (intent) {
            is QuizIntent.Pick -> pickOption(intent.optionIndex)
            QuizIntent.GoHome -> viewModelScope.launch { _effect.send(QuizEffect.NavigateHome) }
        }
    }

    private fun pickOption(optionIndex: Int) {
        val current = _state.value
        if (current.pickedOption != null || current.currentQuestion == null) return

        val question = current.currentQuestion!!
        val isCorrect = optionIndex == question.correctOptionIndex
        val newStreak = if (isCorrect) current.streak + 1 else 0

        soundEffects.play(if (isCorrect) SoundType.OK else SoundType.ERR)

        _state.update {
            it.copy(
                pickedOption = optionIndex,
                correct = it.correct + if (isCorrect) 1 else 0,
                wrong = it.wrong + if (isCorrect) 0 else 1,
                streak = newStreak,
                bestStreak = max(it.bestStreak, newStreak)
            )
        }

        viewModelScope.launch {
            delay(1000)
            advance()
        }
    }

    private fun advance() {
        val current = _state.value
        val nextPosition = current.position + 1
        if (nextPosition >= current.deck.size) {
            finishQuiz()
        } else {
            _state.update { it.copy(position = nextPosition, pickedOption = null) }
        }
    }

    private fun finishQuiz() {
        val current = _state.value
        viewModelScope.launch {
            val result = QuizResult(
                category = current.category,
                correct = current.correct,
                wrong = current.wrong,
                total = current.total,
                bestStreak = current.bestStreak,
                completedAt = System.currentTimeMillis()
            )
            completeQuizUseCase(result)
            _effect.send(QuizEffect.NavigateToResult)
        }
    }
}

object QuizNavArgs {
    const val CATEGORY_ARG = "category"
}
