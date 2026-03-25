package com.example.presentation.screen.words

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun WordsScreen(
    modifier: Modifier = Modifier,
    viewModel: WordsViewModel = hiltViewModel(),
    onFinished: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.loadWords() }

    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.currentWord != null) {
            state.currentWord!!.variants.forEach { variant ->
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(variant.color),
                    onClick = { viewModel.processIntent(WordsIntent.SelectVariant(variant)) },
                    enabled = state.isSelected
                ) {
                    Text(
                        modifier = Modifier,
                        text = variant.variant,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}