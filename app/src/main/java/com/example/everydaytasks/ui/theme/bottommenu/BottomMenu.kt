package com.example.everydaytasks.ui.theme.bottommenu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import com.example.everydaytasks.ui.theme.BottomMenuColor
import com.example.everydaytasks.ui.theme.SelectedItemBGColor
import com.example.everydaytasks.ui.theme.SelectedItemColor
import com.example.everydaytasks.ui.theme.UnSelectedItemColor

@Composable
fun BottomMenu(
    onTodayClick: () -> Unit = {},
    onUpcomingClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onReviewClick: () -> Unit = {}
) {
    val items = listOf(
        BottomMenuBar.Today,
        BottomMenuBar.Month,
        BottomMenuBar.Search,
        BottomMenuBar.Review
    )

    val selectedItem = remember {
        mutableStateOf("Today")
    }

    NavigationBar(
        containerColor = BottomMenuColor
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = (selectedItem.value == item.title),
                onClick = {
                    when (item.title) {
                        "Today" -> onTodayClick()
                        "Upcoming" -> onUpcomingClick()
                        "Search" -> onSearchClick()
                        "Review" -> onReviewClick()
                    }
                    selectedItem.value = item.title
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = item.iconId
                        ),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = item.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SelectedItemColor,
                    unselectedIconColor = UnSelectedItemColor,
                    selectedTextColor = SelectedItemColor,
                    unselectedTextColor = UnSelectedItemColor,
                    indicatorColor = SelectedItemBGColor
                )
            )
        }
    }
}