package com.example.everydaytasks.ui.theme.progress

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.everydaytasks.R
import com.example.everydaytasks.ui.theme.adding.AddingScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.everydaytasks.ui.theme.Biruz
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProgressPage(
    onNavigationToAddingPage: (AddingScreenDataObject) -> Unit
) {
    val fs = Firebase.firestore
    val list = remember {
        mutableStateOf(
            emptyList<ProgressScreenDataObject>()
        )
    }

    fs.collection("tasks").addSnapshotListener{snapshot, exception ->
        list.value = snapshot?.toObjects(ProgressScreenDataObject::class.java) ?: emptyList()
    }

    val today = remember {
        LocalDate.now()
    }

    Image(
        painterResource(
            id = R.drawable.progressscreenbg
        ),
        contentDescription = null,
        Modifier
            .fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp)
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tasks",
                color = Biruz,
                fontSize = 40.sp
            )
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
                                    lastAdded = today.toString()
                                )
                            )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (
                            progressScreenDataObject.daySelected
                            == progressScreenDataObject.dayAdded
                            && progressScreenDataObject.category
                            == "Today"
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
                                        .padding(5.dp)
                                        .size(30.dp)
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
                                                        progressScreenDataObject.lastAdded
                                                    )
                                                )
                                        },
                                    contentScale = ContentScale.Crop
                                )
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
                        } else if (
                            progressScreenDataObject.daySelected
                            >= progressScreenDataObject.dayAdded
                            && progressScreenDataObject.wasAdded
                            == false
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
                                        today.toString()
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
                                        progressScreenDataObject.daySelected,
                                        progressScreenDataObject.daySelected,
                                        "Today",
                                        true,
                                        progressScreenDataObject.lastAdded
                                    )
                                )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .fillMaxHeight(0.9f)
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    onNavigationToAddingPage(
                        AddingScreenDataObject(
                            "IS303"
                        )
                    )
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    }
}