package com.example.everydaytasks.ui.theme.progress

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ProgressScreenDataObject @RequiresApi(Build.VERSION_CODES.O) constructor(
    val newTask: String = "",
    var isCompleted: Boolean = false,
    val key: String = "",
    val dayAdded: String = "",
    val daySelected: String = "",
    val category: String = "",
    val wasAdded: Boolean = false,
    val lastAdded: String = "",
    val firstAdd: Int = 1,
    val time: String = "",
    val priority: Int = 0,
    val tag: String = "",
    val deadlineDay: String = "",
    val deadlineTime: String = ""
)