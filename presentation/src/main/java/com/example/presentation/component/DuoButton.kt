package com.example.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.presentation.theme.Accent
import com.example.presentation.theme.AppText

@Composable
fun DuoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    bg: Color = Accent,
    fg: Color = Color.White,
    shadowColor: Color = bg.copy(alpha = 0.55f),
    shadow: Dp = 6.dp,
    radius: Dp = 18.dp,
    height: Dp = 60.dp,
    style: TextStyle = AppText.Button,
    enabled: Boolean = true,
    trailingIcon: ImageVector? = null
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(height + shadow)
            .clip(RoundedCornerShape(radius))
            .background(shadowColor)
            .clickable(enabled = enabled, onClick = onClick)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(radius))
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = text, style = style, color = fg)
                if (trailingIcon != null) {
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = fg,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
