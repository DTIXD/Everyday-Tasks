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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.everydaytasks.ui.theme.BGColor
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePage(
    onNavigationToProgressPage: (ProgressScreenDataObject) -> Unit
) {
    var input by remember {
        mutableStateOf("")
    }
    var selectedYearMonth by remember {
        mutableStateOf(
            YearMonth.now()
        )
    }
    var selectedDate by remember {
        mutableStateOf<LocalDate?>(
            LocalDate.now()
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = BGColor
            )
    ) {
        null
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 15.dp)
    ) {
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

        val firstDayOfMonth = selectedYearMonth
            .atDay(1)
        val totalDays = selectedYearMonth
            .lengthOfMonth()
        val startOffset = firstDayOfMonth
            .dayOfWeek.value % 7

        val days = List(startOffset) { null } +
                (1..totalDays).map { it }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp)
        ) {
            items(days.size) { index ->
                val day = days[index]
                val dayDate = day?.let {
                    LocalDate.of(
                        selectedYearMonth.year,
                        selectedYearMonth.monthValue, it
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .aspectRatio(1f)
                        .background(
                            when {
                                day == null -> Color.Transparent
                                dayDate == selectedDate -> Color(0xFFADD8E6)
                                else -> Color.LightGray
                            }
                        )
                        .clickable(enabled = day != null) {
                            day?.let {
                                selectedDate = LocalDate.of(
                                    selectedYearMonth.year,
                                    selectedYearMonth.monthValue,
                                    it
                                )
                            }
                        }
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    days[index]?.let {
                        Text(text = it.toString())
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

    fs.collection("tasks").addSnapshotListener{snapshot, exception ->
        list.value = snapshot?.toObjects(ProgressScreenDataObject::class.java) ?: emptyList()
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
                        onNavigationToProgressPage(
                            ProgressScreenDataObject(
                                daySelected = day
                            )
                        )
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