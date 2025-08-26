package com.example.everydaytasks.ui.theme.taskfunc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.everydaytasks.ui.theme.BottomMenuColor
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject

@Composable
fun TaskFunctionsPage(
    onNavigationToProgressPage: (ProgressScreenDataObject) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = BottomMenuColor
            )
    ) {
        Text(
            text = "Hello",
            fontSize = 100.sp
        )
    }
}