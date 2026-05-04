package com.example.presentation.screen.result

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.entity.Category
import com.example.presentation.ads.AdBanner
import com.example.presentation.ads.InterstitialAdEntryPoint
import com.example.presentation.component.DuoButton
import com.example.presentation.theme.Accent
import com.example.presentation.theme.AppText
import com.example.presentation.theme.Bg
import com.example.presentation.theme.Border
import com.example.presentation.theme.Err
import com.example.presentation.theme.Ink
import com.example.presentation.theme.InkMute
import com.example.presentation.theme.Ok
import com.example.presentation.theme.Surface
import com.example.presentation.theme.Yellow
import dagger.hilt.android.EntryPointAccessors

@Composable
fun ResultScreen(
    onPlayAgain: (Category) -> Unit,
    onHome: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity
    val adManager = remember(context) {
        EntryPointAccessors.fromApplication(
            context.applicationContext,
            InterstitialAdEntryPoint::class.java
        ).interstitialAdManager()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ResultEffect.PlayAgainWithAd -> {
                    if (activity != null) {
                        adManager.show(activity) { onPlayAgain(effect.category) }
                    } else {
                        onPlayAgain(effect.category)
                    }
                }
                ResultEffect.NavigateHome -> onHome()
            }
        }
    }

    ResultContent(
        state = state,
        onPlayAgain = { viewModel.processIntent(ResultIntent.PlayAgain) },
        onHome = { viewModel.processIntent(ResultIntent.GoHome) }
    )
}

@Composable
private fun ResultContent(
    state: ResultState,
    onPlayAgain: () -> Unit,
    onHome: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(Accent.copy(alpha = 0.15f), Bg),
                    radius = 1000f
                )
            )
    ) {
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Box(
                    Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Surface)
                        .clickable(onClick = onHome),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Close, contentDescription = null, tint = Ink)
                }
            }

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically)
            ) {
                Box(
                    Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Accent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.EmojiEvents,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }
                Text(state.tier, style = AppText.Title.copy(fontSize = 26.sp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${state.correct}",
                        color = Accent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 64.sp
                    )
                    Text(
                        text = "/${state.total}",
                        color = InkMute,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                }
                Text(
                    text = "${state.categoryLabel} · ${state.percent}% правильно",
                    style = AppText.Body,
                    color = InkMute
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatChip(state.correct, "верно", Ok)
                    StatChip(state.wrong, "ошибок", Err)
                    StatChip(state.bestStreak, "серия", Yellow)
                }
            }

            DuoButton(text = "Ещё раз", onClick = onPlayAgain)
            Spacer(Modifier.height(10.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = onHome),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "На главную",
                    style = AppText.Button.copy(fontSize = 15.sp),
                    color = Ink
                )
            }
        }
        AdBanner(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun StatChip(value: Int, label: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Border)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 2.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Surface)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = "$value",
                color = color,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp
            )
            Text(text = label, color = InkMute, fontSize = 12.sp)
        }
    }
}
