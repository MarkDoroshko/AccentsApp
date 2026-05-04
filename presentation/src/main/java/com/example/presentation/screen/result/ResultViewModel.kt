package com.example.presentation.screen.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserProgressRepository
import com.example.presentation.mapper.toUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val userProgressRepository: UserProgressRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ResultState())
    val state = _state.asStateFlow()

    private val _effect = Channel<ResultEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        loadResult()
    }

    private fun loadResult() {
        viewModelScope.launch {
            val recent = userProgressRepository.getRecentResults(limit = 1).firstOrNull()
            if (recent != null) {
                _state.value = ResultState(
                    category = recent.category,
                    categoryLabel = recent.category.toUiItem().label,
                    correct = recent.correct,
                    wrong = recent.wrong,
                    total = recent.total,
                    bestStreak = recent.bestStreak,
                    isLoading = false
                )
            } else {
                _state.value = ResultState(isLoading = false)
            }
        }
    }

    fun processIntent(intent: ResultIntent) {
        when (intent) {
            ResultIntent.PlayAgain -> viewModelScope.launch {
                _effect.send(ResultEffect.PlayAgainWithAd(_state.value.category))
            }
            ResultIntent.GoHome -> viewModelScope.launch {
                _effect.send(ResultEffect.NavigateHome)
            }
        }
    }
}
