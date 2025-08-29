package com.example.everydaytasks.ui.theme.taskfunc

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.everydaytasks.ui.theme.BGColor
import com.example.everydaytasks.ui.theme.BottomMenuColor
import com.example.everydaytasks.ui.theme.ButtonBGColor
import com.example.everydaytasks.ui.theme.CaptionTextColor
import com.example.everydaytasks.ui.theme.IntervalColor
import com.example.everydaytasks.ui.theme.SelectedItemColor
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
                        horizontal = 12.dp
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
                    .height(35.dp)
            )
            var isOn by remember {
                mutableStateOf(false)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp
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
                        text =
                            if (isOn)
                                "All actions text are shown"
                            else
                                "All actions text are hidden",
                        fontSize = 15.sp,
                        color = CaptionTextColor
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    val offset by animateDpAsState(
                        targetValue =
                            if (isOn) 40.dp
                            else 5.dp,
                        animationSpec = tween(durationMillis = 250),
                        label = ""
                    )

                    val knobSize by animateDpAsState(
                        targetValue =
                            if (isOn) 32.dp
                            else 24.dp,
                        animationSpec = tween(durationMillis = 250),
                        label = ""
                    )

                    val borderWidth by animateDpAsState(
                        targetValue = if (isOn) 0.dp else 4.dp,
                        animationSpec = tween(durationMillis = 250),
                        label = ""
                    )

                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(40.dp)
                            .clip(
                                RoundedCornerShape(20.dp)
                            )
                            .background(
                                color =
                                    if (isOn)
                                        ButtonBGColor
                                    else
                                        BottomMenuColor
                            )
                            .border(
                                width = borderWidth,
                                color =
                                    if (isOn)
                                        Color.Transparent
                                    else
                                        Color.White,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                isOn = !isOn
                            }
                            .padding(4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Box(
                            modifier = Modifier
                                .offset(x = offset)
                                .size(knobSize)
                                .clip(
                                    CircleShape
                                )
                                .background(
                                    color =
                                        if (isOn)
                                            BottomMenuColor
                                        else
                                            Color.White
                                )
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(35.dp)
            )

            val actionsList = listOf(
                "Date",
                "Priority",
                "Reminder",
                "Executor",
                "Tags",
                "Deadline",
                "Location",
            )
            val selectedActions = remember {
                mutableStateListOf<String>()
            }
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 25.dp
                    ),
                text = "Preview",
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
            )
            LazyRow(
                modifier = Modifier
                    .padding(
                        horizontal = 25.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(selectedActions) { label ->
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .clip(
                                RoundedCornerShape(20.dp)
                            )
                            .background(
                                Color(0xFFE0E0E0)
                            )
                            .padding(
                                horizontal = 16.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .height(65.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 1.dp)
                    .background(
                        color = IntervalColor
                    )
            ) {}
            Spacer(
                modifier = Modifier
                    .height(
                        15.dp
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Order of actions in the task",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Show all",
                        fontSize = 20.sp,
                        color = SelectedItemColor
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(
                        25.dp
                    )
            )
            actionsList.forEach { label ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (label in selectedActions) {
                                selectedActions.remove(
                                    element = label
                                )
                            } else {
                                selectedActions.add(label)
                            }
                        }
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 20.sp,
                        color =
                            if (label in selectedActions)
                                Color.Cyan
                            else
                                Color.White
                    )
                }
            }
        }
    }
}