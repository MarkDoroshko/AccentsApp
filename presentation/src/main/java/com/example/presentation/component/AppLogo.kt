package com.example.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.theme.Accent
import com.example.presentation.theme.AppText

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Accent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "а́",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                style = AppText.Display.copy(fontSize = 20.sp)
            )
        }
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Акце́нт",
            style = AppText.Title.copy(fontSize = 18.sp)
        )
    }
}
