package com.example.presentation.screen.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.component.AppLogo
import com.example.presentation.component.DuoButton
import com.example.presentation.component.StreakBadge
import com.example.presentation.theme.Accent
import com.example.presentation.theme.AppText
import com.example.presentation.theme.Bg
import com.example.presentation.theme.InkMute

@Composable
fun StartScreen(
    onStart: () -> Unit,
    viewModel: StartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                StartEffect.NavigateToCategories -> onStart()
            }
        }
    }

    StartContent(
        state = state,
        onStart = { viewModel.processIntent(StartIntent.Start) }
    )
}

@Composable
private fun StartContent(
    state: StartState,
    onStart: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(Accent.copy(alpha = 0.13f), Bg),
                    radius = 900f
                )
            )
            .padding(horizontal = 28.dp, vertical = 40.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                AppLogo()
                Spacer(Modifier.weight(1f))
                if (state.streak > 0) {
                    StreakBadge(state.streak)
                }
            }

            Column(
                Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier.size(200.dp).clip(CircleShape).background(Accent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "о́",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 96.sp
                    )
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Привет!\nПоставим ударе́ние?",
                    style = AppText.Display,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Тренажёр орфоэпии для ЕГЭ. 5 минут в день — и задание №4 не страшно.",
                    style = AppText.Body,
                    color = InkMute,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 280.dp)
                )
            }

            DuoButton(
                text = "НАЧАТЬ",
                onClick = onStart,
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward
            )
            Spacer(Modifier.height(8.dp))
            val totalText = if (state.totalWords > 0) "${state.totalWords} слов" else "Все слова"
            Text(
                text = "$totalText · 7 категорий · офлайн",
                style = AppText.Body.copy(fontSize = 12.sp),
                color = InkMute,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
