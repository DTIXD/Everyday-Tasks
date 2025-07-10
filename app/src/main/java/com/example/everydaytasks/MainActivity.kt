package com.example.everydaytasks

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.everydaytasks.ui.theme.AddButtonBGColor
import com.example.everydaytasks.ui.theme.BGColor
import com.example.everydaytasks.ui.theme.BottomMenuColor
import com.example.everydaytasks.ui.theme.adding.AddingPage
import com.example.everydaytasks.ui.theme.adding.AddingScreenDataObject
import com.example.everydaytasks.ui.theme.bottommenu.BottomMenu
import com.example.everydaytasks.ui.theme.date.DatePage
import com.example.everydaytasks.ui.theme.date.DateScreenDataObject
import com.example.everydaytasks.ui.theme.login.LoginPage
import com.example.everydaytasks.ui.theme.login.LoginScreenObject
import com.example.everydaytasks.ui.theme.progress.ProgressPage
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject
import com.example.everydaytasks.ui.theme.review.ReviewPage
import com.example.everydaytasks.ui.theme.review.ReviewScreenDataObject
import com.example.everydaytasks.ui.theme.search.SearchPage
import com.example.everydaytasks.ui.theme.search.SearchScreenDataObject

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val isBottomMenuVisible = remember {
                mutableStateOf(false)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Scaffold(
                    floatingActionButton = {
                        if (isBottomMenuVisible.value) {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(
                                        AddingScreenDataObject(
                                            "IS303"
                                        )
                                    )
                                },
                                containerColor = AddButtonBGColor,
                                modifier = Modifier
                                    .offset(y = (-16).dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    containerColor = BGColor,
                    bottomBar = {
                        if (isBottomMenuVisible.value) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(BottomMenuColor)
                                    .padding(vertical = 10.dp)
                            ) {
                                BottomMenu(
                                    onTodayClick = {
                                        navController.navigate(ProgressScreenDataObject())
                                    },
                                    onUpcomingClick = {
                                        navController.navigate(DateScreenDataObject())
                                    },
                                    onSearchClick = {
                                        navController.navigate(SearchScreenDataObject)
                                    },
                                    onReviewClick = {
                                        navController.navigate(ReviewScreenDataObject)
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = LoginScreenObject
                    ) {
                        composable<LoginScreenObject> {
                            LoginPage { navData ->
                                navController.navigate(navData)
                                isBottomMenuVisible.value = true
                            }
                        }
                        composable<ProgressScreenDataObject> {
                            ProgressPage()
                        }
                        composable<AddingScreenDataObject> {
                            AddingPage(
                                onNavigationToProgressPage = { navData ->
                                    navController.navigate(navData)
                                }
                            )
                        }
                        composable<DateScreenDataObject> {
                            DatePage(
                                onNavigationToProgressPage = { navData ->
                                    navController.navigate(navData)
                                }
                            )
                        }
                        composable<SearchScreenDataObject> {
                            SearchPage()
                        }
                        composable<ReviewScreenDataObject> {
                            ReviewPage()
                        }
                    }
                }
            }
        }
    }
}