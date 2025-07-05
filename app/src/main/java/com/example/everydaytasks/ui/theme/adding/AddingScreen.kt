package com.example.everydaytasks.ui.theme.adding

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.everydaytasks.R
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.everydaytasks.ui.theme.AlmostWhite
import com.example.everydaytasks.ui.theme.Biruz
import com.example.everydaytasks.ui.theme.DarkerThanAlmostWhite
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddingPage(
    onNavigationToProgressPage: (ProgressScreenDataObject) -> Unit
) {
    val newTask = remember {
        mutableStateOf("")
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
    val key = fs.collection("tasks")
        .document().id
    val today = remember {
        LocalDate.now()
    }

    val addForToday = remember {
        mutableStateOf(true)
    }

    Image(
        painterResource(id = R.drawable.addingscreenbg),
        contentDescription = null,
        Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(list.value) { progressScreenDataObject ->
                    val isExtended = remember {
                        mutableStateOf(false)
                    }

                    if (
                        progressScreenDataObject.category
                        == "EveryDay"
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = progressScreenDataObject.newTask,
                                    modifier = Modifier
                                        .padding(
                                            top = 10.dp,
                                            end = 40.dp
                                        )
                                        .clickable {
                                            isExtended.value = !isExtended.value
                                        },
                                    fontSize = 20.sp,
                                    maxLines =
                                        if (isExtended.value) 100
                                        else 1
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
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .padding(bottom = 25.dp)
                    .offset(y = (-70).dp)
                    .background(
                        color = AlmostWhite,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Text(
                        text = "ADD TASK",
                        color = Biruz
                    )
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )
                    TextField(
                        value = newTask.value,
                        onValueChange = {
                            newTask.value = it
                        },
                        shape = RoundedCornerShape(7.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = DarkerThanAlmostWhite,
                            focusedContainerColor = DarkerThanAlmostWhite,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(17.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            painterResource(id = R.drawable.addbutton),
                            contentDescription = null,
                            Modifier
                                .padding(5.dp)
                                .clickable {
                                    onNavigationToProgressPage(
                                        ProgressScreenDataObject(
                                            newTask = newTask.value,
                                            isCompleted = false,
                                            key = key,
                                            dayAdded = today.toString(),
                                            daySelected = today.toString(),
                                            category = if (addForToday.value) "Today"
                                            else "EveryDay",
                                            wasAdded = addForToday.value,
                                            lastAdded = today.toString()
                                        )
                                    )
                                    fs.collection("tasks")
                                        .document(key).set(
                                            ProgressScreenDataObject(
                                                newTask = newTask.value,
                                                isCompleted = false,
                                                key = key,
                                                dayAdded = today.toString(),
                                                daySelected = today.toString(),
                                                category = if (addForToday.value) "Today"
                                                else "EveryDay",
                                                wasAdded = addForToday.value,
                                                lastAdded = today.toString()
                                            )
                                        )
                                },
                            contentScale = ContentScale.Crop
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painterResource(
                                        id =
                                            if (addForToday.value) R.drawable.completed
                                            else R.drawable.notcompleted
                                    ),
                                    contentDescription = null,
                                    Modifier
                                        .padding(5.dp)
                                        .size(30.dp)
                                        .clickable {
                                            addForToday.value = !addForToday.value
                                        },
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = "Add Just For Today",
                                    color = Biruz
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}