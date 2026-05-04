package com.example.presentation.screen.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Category
import com.example.presentation.mapper.toUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(
        CategoriesState(categories = Category.entries.map { it.toUiItem() })
    )
    val state = _state.asStateFlow()

    private val _effect = Channel<CategoriesEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun processIntent(intent: CategoriesIntent) {
        when (intent) {
            is CategoriesIntent.Pick -> viewModelScope.launch {
                _effect.send(CategoriesEffect.StartQuiz(intent.category))
            }
            CategoriesIntent.Back -> viewModelScope.launch {
                _effect.send(CategoriesEffect.NavigateBack)
            }
        }
    }
}
