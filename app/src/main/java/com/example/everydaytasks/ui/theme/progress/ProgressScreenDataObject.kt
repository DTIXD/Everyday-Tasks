package com.example.everydaytasks.ui.theme.progress

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ProgressScreenDataObject(
    val newTask: String = "",
    var isCompleted: Boolean = false,
    val key: String = "",
    val dayAdded: String = "",
    val daySelected: String = "",
    val category: String = "",
    val wasAdded: Boolean = false,
    val lastAdded: String = "",
    val firstAdd: Int = 1
)