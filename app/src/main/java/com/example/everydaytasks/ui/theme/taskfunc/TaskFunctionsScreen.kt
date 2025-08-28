package com.example.everydaytasks.ui.theme.taskfunc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.everydaytasks.ui.theme.BGColor
import com.example.everydaytasks.ui.theme.CaptionTextColor
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
                color = BGColor
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 10.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(
                            top = 25.dp,
                            bottom = 5.dp,
                            start = 5.dp,
                            end = 5.dp
                        )
                        .clickable {
                            onNavigationToProgressPage(
                                ProgressScreenDataObject()
                            )
                        },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Add",
                    tint = Color.White
                )
                Spacer(
                    modifier = Modifier
                        .width(30.dp)
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 25.dp,
                            bottom = 5.dp,
                            start = 5.dp,
                            end = 5.dp
                        ),
                    text = "Fast Adding",
                    fontSize = 25.sp,
                    color = Color.White
                )
            }
            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 10.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Show names of actions",
                        fontSize = 25.sp,
                        color = Color.White
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                    )
                    Text(
                        text = "All actions text are shown",
                        fontSize = 15.sp,
                        color = CaptionTextColor
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {

                }
            }
        }
    }
}