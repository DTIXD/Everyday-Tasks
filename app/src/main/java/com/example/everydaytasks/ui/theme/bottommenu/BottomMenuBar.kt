package com.example.everydaytasks.ui.theme.bottommenu

import com.example.everydaytasks.R

sealed class BottomMenuBar(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object Today: BottomMenuBar(
        route = "",
        title = "Today",
        iconId = R.drawable.ic_today
    )
    object Month: BottomMenuBar(
        route = "",
        title = "Upcoming",
        iconId = R.drawable.ic_month
    )
    object Search: BottomMenuBar(
        route = "",
        title = "Search",
        iconId = R.drawable.ic_search
    )
    object Review: BottomMenuBar(
        route = "",
        title = "Review",
        iconId = R.drawable.ic_reviev
    )
}