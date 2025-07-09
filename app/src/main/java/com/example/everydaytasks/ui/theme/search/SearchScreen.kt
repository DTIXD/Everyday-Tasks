package com.example.everydaytasks.ui.theme.search

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
fun SearchPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BGColor)
            .padding(5.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(5.dp),
            text = "Search",
            color = Color.White,
            fontSize = 25.sp
        )
    }
}