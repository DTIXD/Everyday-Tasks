package com.example.everydaytasks.ui.theme.progress

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.everydaytasks.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import com.example.everydaytasks.ui.theme.BGColor
import com.example.everydaytasks.ui.theme.CaptionTextColor
import com.example.everydaytasks.ui.theme.IntervalColor
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProgressPage(
    modifier: Modifier = Modifier
) {
    val fs = Firebase.firestore
    val list = remember {
        mutableStateOf(
            emptyList<ProgressScreenDataObject>()
        )
    }

    fs.collection("tasks").addSnapshotListener{snapshot, exception ->
        list.value = snapshot?.toObjects(
            ProgressScreenDataObject::class.java
        ) ?: emptyList()
    }

    val today = remember {
        LocalDate.now()
    }

    Column(
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
                text = "Today",
                color = Color.White,
                fontSize = 25.sp,
            )
            Text(
                modifier = Modifier
                    .padding(
                        top = 2.dp,
                        bottom = 5.dp,
                        start = 5.dp,
                        end = 5.dp
                    ),
                text =
                    if (
                        list.value.count {
                            it.category == "Today" &&
                            it.dayAdded == today.toString()
                        }
                        == 0
                    ) "You've done everything today"
                    else "${
                        list.value.count {
                            it.category == "Today" && 
                            it.dayAdded == today.toString()
                        } 
                    } task(s)",
                color = CaptionTextColor,
                fontSize = 15.sp
            )
        }
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(list.value) { progressScreenDataObject ->
                val isCompleted = remember {
                    mutableStateOf(
                        progressScreenDataObject.isCompleted
                    )
                }
                val isExtended = remember {
                    mutableStateOf(false)
                }

                if (
                    today.toString()
                    > progressScreenDataObject.lastAdded
                    && progressScreenDataObject.category
                    == "EveryDay"
                    && progressScreenDataObject.wasAdded
                    == true
                ) {
                    fs.collection("tasks")
                        .document(progressScreenDataObject.key)
                        .set(
                            ProgressScreenDataObject(
                                progressScreenDataObject.newTask,
                                progressScreenDataObject.isCompleted,
                                progressScreenDataObject.key,
                                progressScreenDataObject.dayAdded,
                                progressScreenDataObject.daySelected,
                                progressScreenDataObject.category,
                                false,
                                progressScreenDataObject.lastAdded,
                                progressScreenDataObject.firstAdd
                            )
                        )
                }
                if (
                    progressScreenDataObject.daySelected
                    == progressScreenDataObject.dayAdded
                    && progressScreenDataObject.category
                    == "Today"
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    color = IntervalColor
                                )
                        ) {}
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                            ) {
                                Image(
                                    painterResource(
                                        id =
                                            if (isCompleted.value) R.drawable.completed
                                            else R.drawable.notcompleted
                                    ),
                                    contentDescription = null,
                                    Modifier
                                        .size(35.dp)
                                        .clickable {
                                            isCompleted.value = !isCompleted.value
                                            fs.collection("tasks")
                                                .document(progressScreenDataObject.key)
                                                .set(
                                                    ProgressScreenDataObject(
                                                        progressScreenDataObject.newTask,
                                                        isCompleted.value,
                                                        progressScreenDataObject.key,
                                                        progressScreenDataObject.dayAdded,
                                                        progressScreenDataObject.daySelected,
                                                        progressScreenDataObject.category,
                                                        progressScreenDataObject.wasAdded,
                                                        progressScreenDataObject.lastAdded,
                                                        progressScreenDataObject.firstAdd
                                                    )
                                                )
                                        },
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = progressScreenDataObject.newTask,
                                    modifier = Modifier
                                        .padding(
                                            end = 40.dp,
                                            start = 3.dp
                                        )
                                        .clickable {
                                            isExtended.value = !isExtended.value
                                        },
                                    fontSize = 25.sp,
                                    maxLines =
                                        if (isExtended.value) 100
                                        else 1,
                                    color = Color.White
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    onClick = {
                                        fs.collection("tasks")
                                            .document(progressScreenDataObject.key)
                                            .delete()
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                } else if (
                    progressScreenDataObject.lastAdded
                    < today.toString()
                    && progressScreenDataObject.wasAdded
                    == false
                    && progressScreenDataObject.category
                    == "EveryDay"
                ) {
                    fs.collection("tasks")
                        .document(progressScreenDataObject.key).set(
                            ProgressScreenDataObject(
                                progressScreenDataObject.newTask,
                                progressScreenDataObject.isCompleted,
                                progressScreenDataObject.key,
                                progressScreenDataObject.dayAdded,
                                progressScreenDataObject.daySelected,
                                progressScreenDataObject.category,
                                true,
                                today.toString(),
                                progressScreenDataObject.firstAdd + 1
                            )
                        )
                    val key = fs.collection("tasks")
                        .document().id
                    fs.collection("tasks")
                        .document(key).set(
                            ProgressScreenDataObject(
                                progressScreenDataObject.newTask,
                                progressScreenDataObject.isCompleted,
                                key,
                                LocalDate.now().plusDays(
                                    if (progressScreenDataObject.firstAdd == 1) 0
                                    else 1
                                ).toString(),
                                progressScreenDataObject.daySelected,
                                "Today",
                                true,
                                progressScreenDataObject.lastAdded,
                                progressScreenDataObject.firstAdd
                            )
                        )

                }
            }
        }
    }
}