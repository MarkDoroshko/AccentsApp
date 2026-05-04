package com.example.presentation.screen.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Category
import com.example.domain.usecase.GetWordsUseCase
import com.example.domain.usecase.ObserveUserProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    observeUserProgressUseCase: ObserveUserProgressUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(StartState())
    val state = _state.asStateFlow()

    private val _effect = Channel<StartEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        observeUserProgressUseCase()
            .onEach { progress -> _state.update { it.copy(streak = progress.currentStreak) } }
            .launchIn(viewModelScope)

        viewModelScope.launch {
            val total = getWordsUseCase(Category.ALL).size
            _state.update { it.copy(totalWords = total) }
        }
    }

    fun processIntent(intent: StartIntent) {
        when (intent) {
            StartIntent.Start -> viewModelScope.launch { _effect.send(StartEffect.NavigateToCategories) }
        }
    }
}
