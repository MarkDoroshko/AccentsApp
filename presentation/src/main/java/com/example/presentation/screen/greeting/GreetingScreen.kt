package com.example.presentation.screen.greeting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GreetingScreen(
    modifier: Modifier = Modifier,
    onStart: () -> Unit
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Example title",
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onStart
        ) {
            Text(
                text = "Начать",
                fontSize = 16.sp
            )
        }
    }
}