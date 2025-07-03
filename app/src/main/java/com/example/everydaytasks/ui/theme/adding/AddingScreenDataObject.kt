package com.example.everydaytasks.ui.theme.adding

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AddingScreenDataObject(
    val str: String = ""
)