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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.everydaytasks.ui.theme.BGColor
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
    var input by remember {
        mutableStateOf("")
    }
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
            .padding(horizontal = 8.dp, vertical = 12.dp)

    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(
                        top = 5.dp,
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
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    val parts = it.split("/")
                    if (parts.size == 2) {
                        val month = parts[0]
                            .toIntOrNull()
                        val year = parts[1]
                            .toIntOrNull()
                        if (
                            month != null
                            && year != null
                            && month in 1..12
                        ) {
                            selectedYearMonth = YearMonth.of(
                                year,
                                month
                            )
                        }
                    }
                },
                label = {
                    Text("Enter Month/Year (e.g. 06/2025)")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
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
                        fontWeight = FontWeight.Bold
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
                    Box(
                        modifier = Modifier
                            .padding(15.dp)
                            .aspectRatio(1f)
                            .clip(
                                RoundedCornerShape(26.dp)
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
                            Text(
                                text = it.dayOfMonth.toString(),
                                color = Color.White
                            )
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
                    Text(text = day)
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
                            containerColor = Color.White,
                            contentColor = BGColor
                        )
                    ) {
                        Text(
                            "Select",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}