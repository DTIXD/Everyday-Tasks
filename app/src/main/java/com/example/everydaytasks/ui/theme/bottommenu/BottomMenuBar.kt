package com.example.everydaytasks.ui.theme.bottommenu

import com.example.everydaytasks.R

sealed class BottomMenuBar(
    val title: String,
    val iconId: Int
) {
    object Today: BottomMenuBar(
        title = "Today",
        iconId = R.drawable.ic_today
    )
    object Month: BottomMenuBar(
        title = "Upcoming",
        iconId = R.drawable.ic_month
    )
    object Search: BottomMenuBar(
        title = "Search",
        iconId = R.drawable.ic_search
    )
    object Review: BottomMenuBar(
        title = "Review",
        iconId = R.drawable.ic_reviev
    )
}