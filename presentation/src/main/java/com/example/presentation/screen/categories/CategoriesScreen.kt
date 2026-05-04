package com.example.presentation.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.entity.Category
import com.example.presentation.ads.AdBanner
import com.example.presentation.model.CategoryUiItem
import com.example.presentation.theme.AppText
import com.example.presentation.theme.Bg
import com.example.presentation.theme.Border
import com.example.presentation.theme.Ink
import com.example.presentation.theme.InkMute
import com.example.presentation.theme.Surface

@Composable
fun CategoriesScreen(
    onBack: () -> Unit,
    onPick: (Category) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CategoriesEffect.StartQuiz -> onPick(effect.category)
                CategoriesEffect.NavigateBack -> onBack()
            }
        }
    }

    CategoriesContent(
        state = state,
        onBack = { viewModel.processIntent(CategoriesIntent.Back) },
        onPick = { viewModel.processIntent(CategoriesIntent.Pick(it)) }
    )
}

@Composable
private fun CategoriesContent(
    state: CategoriesState,
    onBack: () -> Unit,
    onPick: (Category) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Bg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Surface)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Ink
                )
            }
            Spacer(Modifier.width(10.dp))
            Text("Выбери раздел", style = AppText.Title)
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.categories, key = { it.category.name }) { item ->
                CategoryCard(item = item, onClick = { onPick(item.category) })
            }
            item { Spacer(Modifier.height(8.dp)) }
        }

        AdBanner(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun CategoryCard(item: CategoryUiItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Border)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Surface)
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(item.color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.initials,
                    color = item.color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(item.label, style = AppText.Subtitle, color = Ink)
                Text(
                    item.description,
                    style = AppText.Body.copy(fontSize = 13.sp),
                    color = InkMute
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = InkMute,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
