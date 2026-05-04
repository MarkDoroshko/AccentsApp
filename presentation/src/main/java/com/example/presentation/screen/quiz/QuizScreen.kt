package com.example.presentation.screen.quiz

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.ads.AdBanner
import com.example.presentation.model.QuizQuestionUiItem
import com.example.presentation.theme.Accent
import com.example.presentation.theme.AppText
import com.example.presentation.theme.Bg
import com.example.presentation.theme.Border
import com.example.presentation.theme.Err
import com.example.presentation.theme.ErrSoft
import com.example.presentation.theme.Ink
import com.example.presentation.theme.InkMute
import com.example.presentation.theme.Ok
import com.example.presentation.theme.OkSoft
import com.example.presentation.theme.Surface
import com.example.presentation.theme.Yellow

@Composable
fun QuizScreen(
    onHome: () -> Unit,
    onFinish: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                QuizEffect.NavigateHome -> onHome()
                QuizEffect.NavigateToResult -> onFinish()
            }
        }
    }

    QuizContent(
        state = state,
        onPick = { viewModel.processIntent(QuizIntent.Pick(it)) },
        onHome = { viewModel.processIntent(QuizIntent.GoHome) }
    )
}

@Composable
private fun QuizContent(
    state: QuizState,
    onPick: (Int) -> Unit,
    onHome: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            QuizTopBar(
                progress = if (state.total == 0) 0f
                else (state.position + if (state.pickedOption != null) 1 else 0).toFloat() / state.total,
                streak = state.streak,
                onHome = onHome
            )
            Spacer(Modifier.height(22.dp))

            val question = state.currentQuestion
            if (question != null) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(22.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ГДЕ УДАРЕНИЕ?",
                        style = AppText.Caption,
                        color = InkMute
                    )

                    WordCard(question = question, categoryLabel = state.categoryLabel)

                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        question.options.forEachIndexed { index, option ->
                            OptionRow(
                                text = option,
                                isPicked = state.pickedOption == index,
                                isCorrect = index == question.correctOptionIndex,
                                showResult = state.pickedOption != null,
                                enabled = state.pickedOption == null,
                                onClick = { onPick(index) }
                            )
                        }
                    }

                    FeedbackText(
                        showResult = state.pickedOption != null,
                        isCorrect = state.pickedOption == question.correctOptionIndex,
                        correctText = question.options.getOrNull(question.correctOptionIndex).orEmpty()
                    )
                }
            }
        }
        AdBanner(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun QuizTopBar(progress: Float, streak: Int, onHome: () -> Unit) {
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "progress")
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Surface)
                .clickable(onClick = onHome),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Home, contentDescription = null, tint = Ink)
        }
        Spacer(Modifier.width(12.dp))
        Box(
            Modifier
                .weight(1f)
                .height(12.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Border)
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Accent)
            )
        }
        Spacer(Modifier.width(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.LocalFireDepartment,
                contentDescription = null,
                tint = Yellow,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = "$streak", color = Yellow, style = AppText.Caption.copy(fontSize = 14.sp))
        }
    }
}

@Composable
private fun WordCard(question: QuizQuestionUiItem, categoryLabel: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Surface)
                .padding(horizontal = 22.dp, vertical = 38.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question.word,
                style = AppText.Word,
                color = Ink,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = categoryLabel.lowercase(),
                style = AppText.Body.copy(fontSize = 13.sp),
                color = InkMute
            )
        }
    }
}

@Composable
private fun OptionRow(
    text: String,
    isPicked: Boolean,
    isCorrect: Boolean,
    showResult: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val (bg, fg, border) = when {
        showResult && isCorrect -> Triple(OkSoft, Ok, Ok)
        showResult && isPicked && !isCorrect -> Triple(ErrSoft, Err, Err)
        else -> Triple(Surface, Ink, Border)
    }

    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(border)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(bg)
                .border(2.dp, border, RoundedCornerShape(18.dp))
                .clickable(enabled = enabled, onClick = onClick)
                .padding(horizontal = 18.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = AppText.Option,
                color = fg,
                modifier = Modifier.weight(1f)
            )
            if (showResult && isCorrect) {
                ResultIcon(bg = Ok, icon = Icons.Filled.Check)
            } else if (showResult && isPicked) {
                ResultIcon(bg = Err, icon = Icons.Filled.Close)
            }
        }
    }
}

@Composable
private fun ResultIcon(bg: Color, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Box(
        Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun FeedbackText(showResult: Boolean, isCorrect: Boolean, correctText: String) {
    Box(modifier = Modifier.fillMaxWidth().height(28.dp), contentAlignment = Alignment.Center) {
        if (showResult) {
            Text(
                text = if (isCorrect) "Верно!" else "Правильно: $correctText",
                color = if (isCorrect) Ok else Err,
                style = AppText.Caption.copy(fontSize = 14.sp),
                textAlign = TextAlign.Center
            )
        }
    }
}
