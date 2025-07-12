package com.example.everydaytasks.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

@Composable
fun SavedCode() {
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

    val dayAdding = remember {
        mutableStateOf("")
    }
    val addForToday = remember {
        mutableStateOf(true)
    }
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
}