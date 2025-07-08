package com.example.everydaytasks

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.everydaytasks.ui.theme.adding.AddingPage
import com.example.everydaytasks.ui.theme.adding.AddingScreenDataObject
import com.example.everydaytasks.ui.theme.bottommenu.BottomMenu
import com.example.everydaytasks.ui.theme.date.DatePage
import com.example.everydaytasks.ui.theme.date.DateScreenDataObject
import com.example.everydaytasks.ui.theme.login.LoginPage
import com.example.everydaytasks.ui.theme.login.LoginScreenObject
import com.example.everydaytasks.ui.theme.progress.ProgressPage
import com.example.everydaytasks.ui.theme.progress.ProgressScreenDataObject

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
                        .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {

                Scaffold(
                    bottomBar = {
                        if (isBottomMenuVisible.value) {
                            BottomMenu(
                                onTodayClick = {
                                    navController.navigate(ProgressScreenDataObject())
                                },
                                onUpcomingClick = {
                                    navController.navigate(DateScreenDataObject())
                                }
                            )
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = LoginScreenObject
                    ) {
                        composable<LoginScreenObject> {
                            LoginPage{navData ->
                                navController.navigate(navData)
                                isBottomMenuVisible.value = true
                            }
                        }
                        composable<ProgressScreenDataObject> {navEntry ->
                            val navData = navEntry.toRoute<ProgressScreenDataObject>()
                            ProgressPage(
                                onSelectButtonClick = {navData ->
                                    navController.navigate(
                                        navData
                                    )
                                }
                            ){navData ->
                                navController.navigate(navData)
                            }
                        }
                        composable<AddingScreenDataObject> {navEntry ->
                            val navData = navEntry.toRoute<AddingScreenDataObject>()
                            AddingPage{navData ->
                                navController.navigate(navData)
                            }
                        }
                        composable<DateScreenDataObject> {
                            DatePage{navData ->
                                navController.navigate(navData)
                            }
                        }
                    }
                }
            }
        }
    }
}