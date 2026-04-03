package com.example.presentation.screen.greeting

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingScreen(
    modifier: Modifier = Modifier,
    onStart: () -> Unit
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF7B2FF7),
            Color(0xFFE040FB),
            Color(0xFF2CCCFF)
        ),
        start = Offset(0f, 0f),
        end = Offset(400f, 0f)
    )

    val titleText = buildAnnotatedString {
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(stringResource(R.string.greeting_title_prefix) + " ")
        }
        withStyle(
            SpanStyle(
                brush = gradientBrush,
                fontStyle = FontStyle.Italic
            )
        ) {
            append(stringResource(R.string.greeting_title_accent))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 72.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        CompositionLocalProvider(LocalRippleConfiguration provides null) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onStart,
                interactionSource = remember { MutableInteractionSource() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(R.string.start_button),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
