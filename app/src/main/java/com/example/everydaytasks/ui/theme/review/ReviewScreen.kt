package com.example.everydaytasks.ui.theme.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.everydaytasks.ui.theme.BGColor

@Composable
fun ReviewPage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = BGColor)
            .padding(
                horizontal = 8.dp
            )
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = 25.dp,
                    bottom = 5.dp,
                    start = 5.dp,
                    end = 5.dp
                ),
            text = "Review",
            color = Color.White,
            fontSize = 25.sp
        )
    }
}