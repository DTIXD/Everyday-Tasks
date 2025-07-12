package com.example.everydaytasks

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.everydaytasks.ui.theme.AddButtonBGColor
import com.example.everydaytasks.ui.theme.AlmostWhite
import com.example.everydaytasks.ui.theme.BGColor
import com.example.everydaytasks.ui.theme.Biruz
import com.example.everydaytasks.ui.theme.BottomMenuColor
import com.example.everydaytasks.ui.theme.DarkerThanAlmostWhite
import com.example.everydaytasks.ui.theme.TextFieldLabelColor
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.time.LocalDate
import androidx.compose.material3.*
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )
        window.navigationBarColor =
            Color(0xFF262626).toArgb()
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightNavigationBars = false

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val isBottomMenuVisible = remember {
                mutableStateOf(false)
            }

            val sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            )
            val scope = rememberCoroutineScope()

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Scaffold(
                    floatingActionButton = {
                        if (isBottomMenuVisible.value) {
                            FloatingActionButton(
                                onClick = {
                                    scope.launch {
                                        sheetState.show()
                                    }
                                },
                                containerColor = AddButtonBGColor,
                                modifier = Modifier
                                    .offset(y = (-8).dp)
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
                        composable<DateScreenDataObject> {
                            DatePage()
                        }
                        composable<SearchScreenDataObject> {
                            SearchPage()
                        }
                        composable<ReviewScreenDataObject> {
                            ReviewPage()
                        }
                    }
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

                    if (sheetState.isVisible) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                scope.launch {
                                    sheetState.hide()
                                }
                            },
                            sheetState = sheetState,
                            containerColor = BGColor,
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(innerPadding)
                                    .padding(top = 25.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 15.dp),
                                    contentAlignment = Alignment.BottomCenter
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(.95f)
                                            .padding(bottom = 25.dp)
                                            .offset(y = (-70).dp)
                                            .background(
                                                color = AlmostWhite,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(15.dp)
                                        ) {
                                            if (addForToday.value) {
                                                TextField(
                                                    value = dayAdding.value,
                                                    onValueChange = {
                                                        dayAdding.value = it
                                                    },
                                                    shape = RoundedCornerShape(7.dp),
                                                    colors = TextFieldDefaults.colors(
                                                        unfocusedContainerColor = DarkerThanAlmostWhite,
                                                        focusedContainerColor = DarkerThanAlmostWhite,
                                                        unfocusedIndicatorColor = Color.Transparent,
                                                        focusedIndicatorColor = Color.Transparent,
                                                    ),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(50.dp),
                                                    label = {
                                                        Text(
                                                            text = "YYYY-DD-MM",
                                                            color = TextFieldLabelColor
                                                        )
                                                    }
                                                )
                                            }
                                            Spacer(
                                                modifier = Modifier
                                                    .height(20.dp)
                                            )
                                            TextField(
                                                value = newTask.value,
                                                onValueChange = {
                                                    newTask.value = it
                                                },
                                                shape = RoundedCornerShape(7.dp),
                                                colors = TextFieldDefaults.colors(
                                                    unfocusedContainerColor = DarkerThanAlmostWhite,
                                                    focusedContainerColor = DarkerThanAlmostWhite,
                                                    unfocusedIndicatorColor = Color.Transparent,
                                                    focusedIndicatorColor = Color.Transparent,
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(50.dp),
                                                label = {
                                                    Text(
                                                        text = "Task",
                                                        color = TextFieldLabelColor
                                                    )
                                                }
                                            )
                                            Spacer(
                                                modifier = Modifier
                                                    .height(17.dp)
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            ) {
                                                Image(
                                                    painterResource(id = R.drawable.addbutton),
                                                    contentDescription = null,
                                                    Modifier
                                                        .padding(5.dp)
                                                        .clickable {
                                                            fs.collection("tasks")
                                                                .document(key).set(
                                                                    ProgressScreenDataObject(
                                                                        newTask = newTask.value,
                                                                        isCompleted = false,
                                                                        key = key,
                                                                        dayAdded = if (addForToday.value) dayAdding.value
                                                                        else today.toString(),
                                                                        daySelected = today.toString(),
                                                                        category = if (addForToday.value) "Today"
                                                                        else "EveryDay",
                                                                        wasAdded = addForToday.value,
                                                                        lastAdded = today.minusDays(
                                                                            1
                                                                        ).toString(),
                                                                        firstAdd = 1
                                                                    )
                                                                )
                                                        },
                                                    contentScale = ContentScale.Crop
                                                )
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Image(
                                                            painterResource(
                                                                id = if (addForToday.value) R.drawable.completed
                                                                else R.drawable.notcompleted
                                                            ),
                                                            contentDescription = null,
                                                            Modifier
                                                                .padding(5.dp)
                                                                .size(30.dp)
                                                                .clickable {
                                                                    addForToday.value =
                                                                        !addForToday.value
                                                                },
                                                            contentScale = ContentScale.Crop
                                                        )
                                                        Text(
                                                            text =
                                                                if (addForToday.value) "Add Date"
                                                                else "Add For Every Day",
                                                            color = Biruz
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
