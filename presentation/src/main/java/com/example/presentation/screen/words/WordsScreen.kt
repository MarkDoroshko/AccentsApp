package com.example.presentation.screen.words

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.model.VariantState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsScreen(
    modifier: Modifier = Modifier,
    viewModel: WordsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) { viewModel.loadWords() }

    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.currentWord?.let { word ->
            Text(
                text = word.word,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                word.variants.shuffled().forEach { variant ->
                    val containerColor = when (variant.state) {
                        VariantState.Default -> MaterialTheme.colorScheme.tertiary
                        VariantState.Correct -> MaterialTheme.colorScheme.primary
                        VariantState.Wrong -> MaterialTheme.colorScheme.secondary
                    }
                    val contentColor = when (variant.state) {
                        VariantState.Default -> MaterialTheme.colorScheme.onTertiary
                        VariantState.Correct -> MaterialTheme.colorScheme.onPrimary
                        VariantState.Wrong -> MaterialTheme.colorScheme.onSecondary
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.processIntent(WordsIntent.SelectVariant(variant)) },
                        enabled = !state.isSelected,
                        interactionSource = remember { MutableInteractionSource() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            contentColor = contentColor,
                            disabledContainerColor = containerColor,
                            disabledContentColor = contentColor
                        )
                    ) {
                        Text(
                            text = variant.variant,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
