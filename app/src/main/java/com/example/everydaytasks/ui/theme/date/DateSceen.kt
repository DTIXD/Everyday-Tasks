package com.example.everydaytasks.ui.theme.date

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.everydaytasks.ui.theme.BGColor
import com.example.everydaytasks.ui.theme.ButtonBGColor
import com.example.everydaytasks.ui.theme.CaptionTextColor
import com.example.everydaytasks.ui.theme.SelectedItemColor
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePage(
    modifier: Modifier = Modifier
) {
    var selectedYearMonth by remember {
        mutableStateOf(
            YearMonth.now()
        )
    }

    val currentDate = LocalDate.now()
    var selectedDate by remember { mutableStateOf(currentDate) }
    var lastCheckedDate by remember { mutableStateOf(currentDate) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val now = LocalDate.now()
                if (now != lastCheckedDate) {
                    selectedDate = now
                    lastCheckedDate = now
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            val nowMillis = System.currentTimeMillis()
            val midnightMillis = LocalDate.now()
                .plusDays(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
            val delayMillis = midnightMillis - nowMillis

            delay(delayMillis)

            selectedDate = LocalDate.now()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = BGColor
            )
            .padding(
                horizontal = 8.dp,
                vertical = 12.dp
            )
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(
                        top = 25.dp,
                        start = 5.dp,
                        end = 5.dp
                    ),
                text = "Upcoming",
                color = Color.White,
                fontSize = 25.sp
            )
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DayOfWeek.entries.forEach {
                    Text(
                        text = it.name.take(3),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = CaptionTextColor
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            val firstDay = selectedYearMonth
                .atDay(1)
            val lastDay = firstDay
                .plusYears(2)
                .minusDays(1)
            val totalDays = java.time.temporal
                .ChronoUnit.DAYS.between(
                    firstDay,
                    lastDay
                ).toInt() + 1
            val startOffset = firstDay
                .dayOfWeek.value % 7

            val allDates = (0 until totalDays).map { offset ->
                firstDay.plusDays(offset.toLong())
            }
            val days = List(
                startOffset
            ) { null } + allDates + List(
                (7 - (allDates.size + startOffset) % 7) % 7
            ) { null }

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                items(days.size) { index ->
                    val day = days[index]
                    val dayDate = day

                    val isFirstDay = day?.dayOfMonth == 1

                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .then(
                                if (isFirstDay) Modifier
                                    .aspectRatio(1f)
                                    .clip(
                                        RoundedCornerShape(4.dp)
                                    )
                                else Modifier
                                    .aspectRatio(1f)
                                    .clip(
                                        RoundedCornerShape(26.dp)
                                    )
                            )
                            .background(
                                when {
                                    dayDate == selectedDate -> SelectedItemColor
                                    else -> BGColor
                                }
                            )
                            .clickable(enabled = day != null) {
                                day?.let {
                                    selectedDate = it
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        days[index]?.let {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                if (it.dayOfMonth == 1) {
                                    Text(
                                        text = it.month
                                            .name.take(3)
                                            .lowercase()
                                            .replaceFirstChar {
                                                c -> c.uppercase()
                                            },
                                        fontSize = 10.sp,
                                        color =
                                            if (dayDate == selectedDate) CaptionTextColor
                                            else SelectedItemColor
                                    )
                                }
                                Text(
                                    text = it.dayOfMonth
                                        .toString(),
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
        val fs = Firebase.firestore
        val list = remember {
            mutableStateOf(
                emptyList<ProgressScreenDataObject>()
            )
        }

        fs.collection("tasks").addSnapshotListener { snapshot, exception ->
            list.value = snapshot?.toObjects(
                ProgressScreenDataObject::class.java
            ) ?: emptyList()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = 100.dp
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            selectedDate?.let { date ->
                val formatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd")
                val day = "Selected date: ${date.format(formatter)}"
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = day,
                        color = CaptionTextColor
                    )
                    Button(
                        onClick = {
                            list.value.forEach { progressScreenDataObject ->
                                fs.collection("tasks")
                                    .document(progressScreenDataObject.key)
                                    .set(
                                        ProgressScreenDataObject(
                                            progressScreenDataObject.newTask,
                                            progressScreenDataObject.isCompleted,
                                            progressScreenDataObject.key,
                                            progressScreenDataObject.dayAdded,
                                            date.format(formatter),
                                            progressScreenDataObject.category,
                                            progressScreenDataObject.wasAdded,
                                            progressScreenDataObject.lastAdded
                                        )
                                    )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonBGColor,
                            contentColor = BGColor
                        )
                    ) {
                        Text(
                            "Select",
                            color = CaptionTextColor
                        )
                    }
                }
            }
        }
    }
}