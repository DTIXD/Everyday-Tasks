package com.example.everydaytasks.ui.theme.taskfunc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 15.dp
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Add",
                    tint = Color.White
                )
                Text(
                    text = "Task Actions",
                    fontSize = 25.sp,
                    color = Color.White
                )
            }
        }
    }
}